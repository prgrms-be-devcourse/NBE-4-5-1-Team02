"use client";

import React from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Order } from "@/services/orderService";

interface OrderDetailsProps {
  order: Order;
  setOrder: React.Dispatch<React.SetStateAction<Order>>;
  onRemoveProduct: (productId: string) => void;
  onIncreaseQuantity: (productId: string) => void;
  onDecreaseQuantity: (productId: string) => void;
  onSubmit: () => void;
}

export default function OrderDetails({
  order,
  setOrder,
  onRemoveProduct,
  onIncreaseQuantity,
  onDecreaseQuantity,
  onSubmit,
}: OrderDetailsProps) {
  const handleAddressChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOrder({ ...order, address: e.target.value });
  };

  const handleZipcodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOrder({ ...order, zipcode: e.target.value });
  };

  const totalPrice = order.items.reduce(
    (sum, item) => sum + item.price * item.quantity,
    0
  );

  return (
    <div className="w-80 border p-4">
      <h2 className="text-lg font-semibold mb-2">주문 내용</h2>
      {/* 주문 정보 */}
      <p className="mb-2">
        <span className="font-medium">주문번호:</span> {order.orderId}
      </p>
      <p className="mb-2">
        <span className="font-medium">구매자:</span> {order.buyerEmail}
      </p>
      <p className="mb-2">
        <span className="font-medium">주문일자:</span> {order.orderDate}
      </p>

      {/* 주문 상품 */}
      <div className="mt-4">
        <h3 className="font-semibold mb-1">주문 상품</h3>
        {order.items && order.items.length > 0 ? (
          order.items.map((item) => (
            <div
              key={item.productId}
              className="flex justify-between items-center mb-2 bg-gray-100 p-2 rounded"
            >
              <div>
                <p className="font-medium">{item.name}</p>
                <p className="text-sm text-gray-600">
                  {(item.price * item.quantity).toLocaleString()}원
                </p>
              </div>
              <div className="flex items-center gap-2">
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => onIncreaseQuantity(item.productId)}
                >
                  +
                </Button>
                <span className="w-6 text-center">{item.quantity}</span>
                <Button
                  variant="outline"
                  size="sm"
                  onClick={() => onDecreaseQuantity(item.productId)}
                >
                  -
                </Button>
              </div>
            </div>
          ))
        ) : (
          <p>주문 상품이 없습니다.</p>
        )}
      </div>

      <div className="mt-4">
        <label className="block font-semibold mb-1">배송 주소</label>
        <Input value={order.address} onChange={handleAddressChange} />
      </div>
      <div className="mt-4">
        <label className="block font-semibold mb-1">우편번호</label>
        <Input value={order.zipcode} onChange={handleZipcodeChange} />
      </div>

      <hr className="my-3" />
      <div className="flex justify-between items-center">
        <span className="font-semibold">총 가격</span>
        <span className="text-lg font-bold">
          {totalPrice.toLocaleString()}원
        </span>
      </div>

      <Button className="mt-4 w-full" onClick={onSubmit}>
        수정하기
      </Button>
    </div>
  );
}
