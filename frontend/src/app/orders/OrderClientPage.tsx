"use client";

import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";

export default function OrdersClientPage({
  rsData,
}: {
  rsData: components["schemas"]["RsDataOrderListResponse"];
}) {
  const orderListResponse = rsData.data;
  const currentPage = orderListResponse?.page || 0;
  const totalPages = orderListResponse?.totalPages || 0;

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">주문 목록</h1>
        <Link 
          href="/"
          className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300 transition-colors"
        >
          메인으로 돌아가기
        </Link>
      </div>
      
      <div className="overflow-x-auto">
        <table className="min-w-full bg-white border border-gray-200">
          <thead>
            <tr className="bg-gray-100">
              <th className="px-6 py-3 text-left">주문번호</th>
              <th className="px-6 py-3 text-left">결제일</th>
              <th className="px-6 py-3 text-left">배송상태</th>
              <th className="px-6 py-3 text-left">주소</th>
              <th className="px-6 py-3 text-left">이메일</th>
              <th className="px-6 py-3 text-right">총 주문금액</th>
            </tr>
          </thead>
          <tbody>
            {orderListResponse?.content?.length ? (
              orderListResponse.content.map((order) => (
                <tr key={order.orderId} className="border-b">
                  <td className="px-6 py-4">{order.orderId}</td>
                  <td className="px-6 py-4">{order.orderDate}</td>
                  <td className="px-6 py-4">{order.deliveryStatus}</td>
                  <td className="px-6 py-4">{order.address}</td>
                  <td className="px-6 py-4">{order.buyerEmail}</td>
                  <td className="px-6 py-4 text-right">{order.totalPrice?.toLocaleString()}원</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={6} className="px-6 py-4 text-center">
                  주문 내역이 없습니다.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
      
      <div className="flex justify-center mt-4 gap-2">
        {totalPages > 0 && (
          <div className="flex items-center gap-2">
            <button
              onClick={() => window.location.href = `?page=${currentPage - 1}`}
              disabled={currentPage <= 0}
              className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
            >
              이전
            </button>
            {Array.from({ length: totalPages }, (_, i) => (
              <a
                key={i}
                href={`?page=${i}`}
                className={`px-3 py-1 rounded ${
                  currentPage === i 
                    ? 'bg-blue-500 text-white' 
                    : 'bg-gray-200 hover:bg-gray-300'
                }`}
              >
                {i + 1}
              </a>
            ))}
            <button
              onClick={() => window.location.href = `?page=${currentPage + 1}`}
              disabled={currentPage >= totalPages - 1}
              className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
            >
              다음
            </button>
          </div>
        )}
      </div>
    </div>
  );
} 