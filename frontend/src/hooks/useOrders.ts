"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import {
  fetchOrderData,
  fetchOrderProductsData,
  fetchAllProducts,
  updateOrderData,
  OrderItem,
  Order,
} from "@/services/orderService";

export interface PaginationDataProductDto {
  data: any[];
  page: number;
  totalPages: number;
}

export default function useOrder(orderId: string) {
  const [order, setOrder] = useState<Order>({
    orderId: "",
    buyerEmail: "",
    address: "",
    zipcode: "",
    deliveryStatus: "",
    orderDate: "",
    items: [],
    totalAmount: 0,
  });

  // availableProducts 배열 -> PaginationDataProductDto로 변경
  const [availableProducts, setAvailableProducts] =
    useState<PaginationDataProductDto>({
      data: [],
      page: 0,
      totalPages: 1,
    });

  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const orderData = await fetchOrderData(orderId);
        const orderProducts = await fetchOrderProductsData(orderId);

        const totalAmount = orderProducts.reduce(
          (sum: number, item: OrderItem) => sum + item.price * item.quantity,
          0
        );

        setOrder({
          ...orderData,
          items: orderProducts,
          totalAmount,
        });

        // availableProducts에 저장된 상품 페이지네이션으로
        const products = await fetchAllProducts();
        setAvailableProducts({
          data: products,
          page: 0,
          totalPages: 1,
        });
      } catch (error: any) {
        console.error(error);
        alert("데이터 로딩에 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [orderId]);

  const updateTotalPrice = (updatedItems: OrderItem[]) => {
    const newTotalAmount = updatedItems.reduce(
      (sum: number, item: OrderItem) => sum + item.price * item.quantity,
      0
    );
    setOrder((prev) => ({
      ...prev,
      items: updatedItems,
      totalAmount: newTotalAmount,
    }));
  };

  const addProduct = (product: any) => {
    const existingItem = order.items.find(
      (item) => item.productId === product.productUuid
    );
    if (existingItem) {
      const updatedItems = order.items.map((item) =>
        item.productId === product.productUuid
          ? { ...item, quantity: item.quantity + 1 }
          : item
      );
      updateTotalPrice(updatedItems);
    } else {
      const newItem = {
        productId: product.productUuid,
        name: product.productName,
        quantity: 1,
        price: product.productPrice,
      };
      updateTotalPrice([...order.items, newItem]);
    }
  };

  const removeProduct = (productId: string) => {
    const updatedItems = order.items.filter(
      (item) => item.productId !== productId
    );
    updateTotalPrice(updatedItems);
  };

  const submitOrder = async () => {
    try {
      await updateOrderData(order);
      alert("주문 수정이 완료되었습니다!");
      router.push(`/orders/${order.orderId}`);
    } catch (error: any) {
      alert(error.message);
    }
  };

  function increaseQuantity(productId: string) {
    setOrder((prev) => {
      const updatedItems = prev.items.map((item) =>
        item.productId === productId
          ? { ...item, quantity: item.quantity + 1 }
          : item
      );
      return { ...prev, items: updatedItems };
    });
  }

  function decreaseQuantity(productId: string) {
    setOrder((prev) => {
      const updatedItems = prev.items
        .map((item) =>
          item.productId === productId && item.quantity > 0
            ? { ...item, quantity: item.quantity - 1 }
            : item
        )
        .filter((item) => item.quantity > 0);
      return { ...prev, items: updatedItems };
    });
  }

  return {
    order,
    setOrder,
    availableProducts,
    loading,
    addProduct,
    removeProduct,
    submitOrder,
    increaseQuantity,
    decreaseQuantity,
  };
}
