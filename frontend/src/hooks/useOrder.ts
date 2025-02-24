// /hooks/useOrder.ts
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import {
  fetchOrderData,
  fetchOrderProductsData,
  fetchAllProducts,
  updateOrderData,
} from "@/services/orderService";

export interface OrderItem {
  productId: string;
  name: string;
  quantity: number;
}

export interface Order {
  orderId: string;
  buyerEmail: string;
  address: string;
  zipcode: string;
  deliveryStatus: string;
  orderDate: string;
  items: OrderItem[];
  totalPrice: number;
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
    totalPrice: 0,
  });
  const [availableProducts, setAvailableProducts] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const orderData = await fetchOrderData(orderId);
        setOrder({ ...orderData, items: [] });
        const orderProducts = await fetchOrderProductsData(orderId);
        setOrder((prev) => ({ ...prev, items: orderProducts }));
        const products = await fetchAllProducts();
        setAvailableProducts(products);
      } catch (error: any) {
        console.error(error);
        alert("데이터 로딩에 실패했습니다.");
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [orderId]);

  const addProduct = (product: any) => {
    const existingItem = order.items.find(
      (item) => item.productId === product.productUuid
    );
    if (existingItem) {
      setOrder({
        ...order,
        items: order.items.map((item) =>
          item.productId === product.productUuid
            ? { ...item, quantity: item.quantity + 1 }
            : item
        ),
      });
    } else {
      const newItem = {
        productId: product.productUuid,
        name: product.productName,
        quantity: 1,
      };
      setOrder({ ...order, items: [...order.items, newItem] });
    }
  };

  const removeProduct = (productId: string) => {
    const updatedItems = order.items.filter(
      (item) => item.productId !== productId
    );
    setOrder({ ...order, items: updatedItems });
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

  return {
    order,
    setOrder,
    availableProducts,
    loading,
    addProduct,
    removeProduct,
    submitOrder,
  };
}
