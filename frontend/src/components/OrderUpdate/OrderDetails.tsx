// /components/OrderUpdate/OrderDetails.tsx
import React from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";

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

interface OrderDetailsProps {
  order: Order;
  setOrder: React.Dispatch<React.SetStateAction<Order>>;
  onRemoveProduct: (productId: string) => void;
  onSubmit: () => void;
}

export default function OrderDetails({
  order,
  setOrder,
  onRemoveProduct,
  onSubmit,
}: OrderDetailsProps) {
  return (
    <div className="w-80 border p-4">
      <h2 className="text-lg font-semibold mb-2">주문 내용</h2>
      <p className="mb-2">
        <span className="font-medium">주문번호:</span> {order.orderId}
      </p>
      <p>
        <span className="font-medium">구매자:</span> {order.buyerEmail}
      </p>
      <p>
        <span className="font-medium">주문일자:</span> {order.orderDate}
      </p>
      <p>
        <span className="font-medium">배송상태:</span> {order.deliveryStatus}
      </p>
      <div className="mt-4">
        <h3 className="font-semibold mb-1">주문 상품</h3>
        {order.items.length > 0 ? (
          order.items.map((item, index) => (
            <div
              key={index}
              className="flex justify-between items-center mb-2 bg-gray-100 p-2 rounded"
            >
              <span>
                {item.name} (수량: {item.quantity})
              </span>
              <Button
                onClick={() => onRemoveProduct(item.productId)}
                variant="destructive"
                className="bg-black text-white px-3 py-1 rounded"
              >
                제거
              </Button>
            </div>
          ))
        ) : (
          <p>주문 상품이 없습니다.</p>
        )}
      </div>
      <div className="mt-4">
        <label className="block font-semibold mb-1">배송 주소</label>
        <Input
          value={order.address}
          onChange={(e) => setOrder({ ...order, address: e.target.value })}
          placeholder="예) 서울시 어쩌구"
        />
      </div>
      <div className="mt-4">
        <label className="block font-semibold mb-1">우편번호</label>
        <Input
          value={order.zipcode}
          onChange={(e) => setOrder({ ...order, zipcode: e.target.value })}
          placeholder="우편번호 입력"
        />
      </div>
      <p className="mt-4 font-semibold">총 금액: {order.totalPrice}원</p>
      <div className="mt-4 flex gap-4">
        <Button
          onClick={onSubmit}
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
          결제하기
        </Button>
        <Button
          onClick={() => window.history.back()}
          className="bg-gray-300 text-black px-4 py-2 rounded"
        >
          돌아가기
        </Button>
      </div>
    </div>
  );
}
