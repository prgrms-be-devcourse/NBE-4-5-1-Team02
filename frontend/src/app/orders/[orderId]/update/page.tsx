// app/orders/[orderId]/update/page.tsx
"use client";
export const dynamic = "force-dynamic";

import React, { useEffect, useState } from "react";
import { useParams } from "next/navigation";

interface OrderData {
  orderId: string;
  buyerEmail: string;
  address: string;
  zipcode: string;
  deliveryStatus: string;
  orderDate: string;
  items: any[];
  totalPrice?: number;
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
  });
  const [loading, setLoading] = useState(true); // 로딩중

  useEffect(() => {
    const fetchOrder = async () => {
      // 주문 데이터 가져오기 - 비동기
      try {
        // 테스트용 이메일 작성 -> 로그인 정보에서 받아오기
        const email = "email1@email.com";
        const res = await fetch(
          `http://localhost:8080/orders/${orderId}?email=${email}`
        );
        if (!res.ok) {
          throw new Error("Failed to fetch order data");
        }
        const json = await res.json();
        const data = json.data;
        setOrder({
          orderId: data.orderUuid,
          buyerEmail: data.buyerEmail,
          address: data.address,
          zipcode: data.zipcode,
          deliveryStatus: data.deliveryStatus,
          orderDate: data.orderDate,
          items: data.items || [],
          totalPrice: data.totalPrice,
        });
      } catch (error: any) {
        console.error(error);
        alert(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchOrder(); // GET 요청 : http://localhost:8080/orders/${orderId}?email=${email}
  }, [orderId]);

  if (loading) return <div>로딩 중...</div>;

  return (
    <div>
      <h1>주문 수정 페이지</h1>
      <p>주문번호: {order.orderId}</p>
      {/* 이후 UI 추가 예정 */}
    </div>
  );
}
