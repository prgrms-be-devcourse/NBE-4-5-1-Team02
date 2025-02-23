// app/orders/[orderId]/update/page.tsx
"use client";
import React, { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

interface OrderData {
  orderId: string;
  buyerEmail: string;
  address: string;
  zipcode: string;
  deliveryStatus: string;
  orderDate: string;
  items: OrderItem[];
  totalPrice: number;
}
interface OrderItem {
  productId: string;
  name: string;
  quantity: number;
}
interface Product {
  productUuid: string;
  productName: string;
  category: string;
  productPrice: number;
  productDescription: string;
  imageUrl: string;
}

export default function OrderUpdatePage() {
  const params = useParams();
  const router = useRouter();
  const orderId = params.orderId as string;

  const [order, setOrder] = useState<OrderData>({
    orderId: "",
    buyerEmail: "",
    address: "",
    zipcode: "",
    deliveryStatus: "",
    orderDate: "",
    items: [],
    totalPrice: 0,
  });
  const [availableProducts, setAvailableProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // 주문 기본 정보 호출
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
        setOrder((prev) => ({
          ...prev,
          orderId: data.orderUuid,
          buyerEmail: data.user.email,
          address: data.deliveryAddress,
          zipcode: String(data.zipCode),
          deliveryStatus: data.deliveryStatus,
          orderDate: data.createDate,
          totalPrice: data.totalPrice,
        }));
      } catch (error: any) {
        console.error(error);
        alert("해당 주문을 찾을 수 없습니다.");
      }
    };

    // 주문 상품 목록
    const fetchOrderProducts = async () => {
      try {
        const email = "email1@email.com";
        const res = await fetch(
          `http://localhost:8080/orders/${orderId}/products?email=${email}`
        );
        if (!res.ok) {
          throw new Error("주문 상품 목록을 불러오지 못했습니다.");
        }
        const json = await res.json();
        const rawItems: OrderItem[] = json.data.data.map((p: any) => ({
          productId: p.productUuid,
          name: p.productName,
          quantity: p.quantity || 1,
        }));
        // 수량 확인
        const groupedItems = rawItems.reduce(
          (acc: { [key: string]: OrderItem }, item) => {
            if (acc[item.productId]) {
              acc[item.productId].quantity += item.quantity;
            } else {
              acc[item.productId] = { ...item };
            }
            return acc;
          },
          {}
        );
        setOrder((prev) => ({ ...prev, items: Object.values(groupedItems) }));
      } catch (error: any) {
        console.error(error);
        alert("주문 상품 목록을 불러오지 못했습니다.");
      }
    };

    // 전체 상품 리스트
    const fetchProducts = async () => {
      try {
        const res = await fetch(
          `http://localhost:8080/products?keyword-type=title&keyword=`
        );
        if (!res.ok) {
          throw new Error("전체 제품 목록을 불러오지 못했습니다.");
        }
        const json = await res.json();
        setAvailableProducts(json.data.data);
      } catch (error: any) {
        console.error(error);
        alert("전체 제품 목록을 불러오지 못했습니다.");
      }
    };

    Promise.all([fetchOrder(), fetchOrderProducts(), fetchProducts()]).then(
      () => setLoading(false)
    );
  }, [orderId]);

  if (loading) return <div>로딩 중...</div>;

  return (
    <div className="p-4 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold mb-4">주문 수정 페이지</h1>
      <div className="flex gap-8">
        {/* 전체 상품 리스트 */}
        <div className="flex-1 border p-4">
          <h2 className="text-xl font-semibold mb-2">전체 제품</h2>
          {availableProducts.length > 0 ? (
            availableProducts.map((product) => (
              <div
                key={product.productUuid}
                className="flex justify-between items-center mb-2 p-2 bg-gray-50 rounded"
              >
                <div>
                  <p className="font-medium">{product.productName}</p>
                  <p className="text-sm text-gray-600">
                    {product.category} - {product.productPrice}원
                  </p>
                </div>
                <Button disabled>추가</Button>
              </div>
            ))
          ) : (
            <p>제품이 없습니다.</p>
          )}
        </div>
        {/* 주문 내용 */}
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
            <span className="font-medium">배송상태:</span>{" "}
            {order.deliveryStatus}
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
                  <Button disabled>제거</Button>
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
        </div>
      </div>
    </div>
  );
}
