"use client";

import { useParams } from "next/navigation";
import React from "react";
import OrderProductList from "@/components/OrderUpdate/OrderProductList";
import OrderDetails from "@/components/OrderUpdate/OrderDetails";
import useOrder from "@/hooks/useOrders";

export default function OrderUpdatePage() {
  const params = useParams();
  const orderId = params.orderId as string;

  const {
    order,
    setOrder,
    loading,
    addProduct,
    removeProduct,
    submitOrder,
    increaseQuantity,
    decreaseQuantity,
  } = useOrder(orderId);

  if (loading) return <div>로딩 중...</div>;

  // + 버튼
  const handleIncrease = (productId: string) => {
    increaseQuantity(productId);
  };

  // - 버튼
  const handleDecrease = (productId: string) => {
    decreaseQuantity(productId);
  };

  const handleSubmit = () => {
    submitOrder();
  };

  return (
    <div className="p-4 max-w-6xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">주문 수정</h1>
      <div className="flex gap-8">
        <OrderProductList
          orderId={orderId}
          onAddProduct={addProduct}
          onDeleteProduct={(p) => removeProduct(p.productUuid)}
        />
        <OrderDetails
          order={order}
          setOrder={setOrder}
          onRemoveProduct={removeProduct}
          onIncreaseQuantity={handleIncrease}
          onDecreaseQuantity={handleDecrease}
          onSubmit={handleSubmit}
        />
      </div>
    </div>
  );
}
