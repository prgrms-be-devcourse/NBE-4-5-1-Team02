"use client";

import Link from "next/link";
import { formatDate } from "@/utils/utility";

interface OrderDetailResponse {
  message: string;
  data: {
    orderUuid: string;
    user: {
      id: string;
      email: string;
      createdDate: string;
      modifiedDate: string;
    };
    createDate: string;
    modifiedDate: string;
    totalAmount: number;
    deliveryAddress: string;
    zipCode: number;
    deliveryStatus: "PENDING" | "SHIPPED" | "DELIVERED" | "CANCELLED";
  };
  code: number;
}

interface DeleteConfirmModalProps {
  isOpen: boolean;
  orderId: string;
  onConfirm: () => void;
  onCancel: () => void;
}

export default function AdminOrderDetailClientPage({ 
  orderDetail 
}: { 
  orderDetail: OrderDetailResponse 
}) {
  const { data } = orderDetail;

  return (
    <div className="container mx-auto p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">주문 상세 정보</h1>
        <Link
          href="/admin/orders"
          className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300 transition-colors"
        >
          목록으로 돌아가기
        </Link>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6">
        <div className="grid grid-cols-2 gap-4">
          <div className="space-y-4">
            <h2 className="text-xl font-semibold">주문 정보</h2>
            <p><span className="font-medium">주문 ID:</span> {data.orderUuid}</p>
            <p><span className="font-medium">주문 일시:</span> {formatDate(data.createDate)}</p>
            <p><span className="font-medium">배송 상태:</span> {data.deliveryStatus}</p>
            <p><span className="font-medium">총 주문금액:</span> {data.totalAmount.toLocaleString()}원</p>
          </div>

          <div className="space-y-4">
            <h2 className="text-xl font-semibold">사용자 정보</h2>
            <p><span className="font-medium">사용자 ID:</span> {data.user.id}</p>
            <p><span className="font-medium">이메일:</span> {data.user.email}</p>
            <p><span className="font-medium">배송 주소:</span> {data.deliveryAddress}</p>
            <p><span className="font-medium">우편번호:</span> {data.zipCode}</p>
          </div>
        </div>
      </div>
    </div>
  );
} 