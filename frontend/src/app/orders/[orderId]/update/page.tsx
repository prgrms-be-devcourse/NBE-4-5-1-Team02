// app/orders/[orderId]/update/page.tsx
"use client";
import React, { useEffect, useState } from "react";
import { useParams } from "next/navigation";
import { Input } from "@/components/ui/input";

interface OrderData {
  orderId: string;
  buyerEmail: string;
  address: string;
  zipcode: string;
  deliveryStatus: string;
  orderDate: string;
  items: any[];
  totalPrice: number;
}

export default function OrderUpdatePage() {
  const params = useParams(); // orderId값 가져오기
  const orderId = params.orderId as string;

  const [order, setOrder] = useState<OrderData>({
    // 초기값
    orderId: "",
    buyerEmail: "",
    address: "",
    zipcode: "",
    deliveryStatus: "",
    orderDate: "",
    items: [],
    totalPrice: 0,
  });

  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrder = async () => {
      try {
        const email = "email1@email.com";
        const res = await fetch(
          `http://localhost:8080/orders/${orderId}?email=${email}`
        );
        if (!res.ok) {
          throw new Error("해당 주문을 찾을 수 없습니다.");
        }
        const json = await res.json();
        const data = json.data;
        setOrder({
          orderId: data.orderUuid,
          buyerEmail: data.user.email,
          address: data.deliveryAddress,
          zipcode: String(data.zipCode),
          deliveryStatus: data.deliveryStatus,
          orderDate: data.createDate,
          items: data.items || [],
          totalPrice: data.totalPrice,
        });
      } catch (error: any) {
        console.error(error);
        alert("해당 주문을 찾을 수 없습니다.");
      } finally {
        setLoading(false);
      }
    };

    fetchOrder();
  }, [orderId]);

  if (loading) return <div>로딩 중...</div>;

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">주문 수정 페이지</h1>
      <div className="mb-4">
        <p>
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
      </div>
      {/* 배송주소 수정 */}
      <div className="mt-4">
        <h2 className="text-lg font-semibold mb-1">배송 주소</h2>
        <Input
          value={order.address}
          onChange={(e) => setOrder({ ...order, address: e.target.value })}
          placeholder="예) 서울시 어쩌구"
        />
      </div>

      {/* 우편번호 수정 */}
      <div className="mt-4">
        <label className="block font-semibold mb-1">우편번호</label>
        <Input
          value={order.zipcode}
          onChange={(e) => setOrder({ ...order, zipcode: e.target.value })}
          placeholder="우편번호 입력"
        />
      </div>
    </div>
  );
}
