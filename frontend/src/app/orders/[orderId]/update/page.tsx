// /app/orders/[orderId]/update/page.tsx
"use client";

import React from "react";
import { useParams } from "next/navigation";
import AvailableProducts from "@/components/OrderUpdate/AvailableProducts";
import OrderDetails from "@/components/OrderUpdate/OrderDetails";
import useOrder from "@/hooks/useOrders";

export default function OrderUpdatePage() {
  const params = useParams();
  const orderId = params.orderId as string;

  const {
    order,
    setOrder,
    availableProducts,
    loading,
    addProduct,
    removeProduct,
    submitOrder,
  } = useOrder(orderId);

  if (loading) return <div>로딩 중...</div>;

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">주문 수정</h1>
      <div className="flex gap-8">
        <AvailableProducts
          products={availableProducts}
          onAddProduct={addProduct}
        />
        <OrderDetails
          order={order}
          setOrder={setOrder}
          onRemoveProduct={removeProduct}
          onSubmit={submitOrder}
        />
      </div>
    </div>
  );
}
