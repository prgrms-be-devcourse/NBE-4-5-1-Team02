"use client";

import { useState } from "react";
import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";
import { useRouter } from "next/navigation";
import DeleteConfirmModal from "@/components/DeleteConfirmModal";

export default function AdminOrdersClientPage({
  rsData,
  onOrderDelete,
  pageSize,
  onPageSizeChange,
}: {
  rsData: components["schemas"]["RsDataOrderListResponse"];
  onOrderDelete: (orderId: string) => Promise<void>;
  pageSize: number;
  onPageSizeChange: (size: number) => void;
}) {
  const router = useRouter();
  const orderListResponse = rsData.data;
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [selectedOrderId, setSelectedOrderId] = useState<string>("");
  const [isDeleting, setIsDeleting] = useState(false);
  const currentPage = orderListResponse?.page || 1;
  const totalPages = orderListResponse?.totalPages || 0;

  const handlePageChange = (page: number) => {
    router.push(`/admin/orders?page=${page}`);
  };

  const handleDeleteClick = (e: React.MouseEvent, orderId: string) => {
    e.stopPropagation(); // 행 클릭 이벤트 전파 방지
    setSelectedOrderId(orderId);
    setIsDeleteModalOpen(true);
  };

  const handleDeleteConfirm = async () => {
    try {
      setIsDeleting(true);
      await onOrderDelete(selectedOrderId);
      setIsDeleteModalOpen(false);
    } catch (error) {
      console.error('Delete Error:', error);
      alert('주문 삭제 중 오류가 발생했습니다.');
    } finally {
      setIsDeleting(false);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <div className="flex justify-between items-center mb-6">
        <div className="flex items-center gap-4">
          <h1 className="text-2xl font-bold">관리자 주문 목록</h1>
          <div className="flex items-center">
            <label htmlFor="pageSize" className="mr-2">페이지당 항목 수:</label>
            <select
              id="pageSize"
              value={pageSize}
              onChange={(e) => onPageSizeChange(Number(e.target.value))}
              className="border rounded p-1"
            >
              <option value="5">5개</option>
              <option value="10">10개</option>
              <option value="15">15개</option>
              <option value="20">20개</option>
            </select>
          </div>
        </div>
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
              <th className="px-6 py-3 text-left">이메일</th>
              <th className="px-6 py-3 text-left">주소</th>
              <th className="px-6 py-3 text-right">총 주문금액</th>
              <th className="px-6 py-3"></th>
            </tr>
          </thead>
          <tbody>
            {orderListResponse?.content?.length ? (
              orderListResponse.content.map((order) => (
                <tr
                  key={order.orderId}
                  className="border-b hover:bg-gray-50 cursor-pointer group"
                  onClick={() => router.push(`/admin/orders/${order.orderId}`)}
                >
                  <td className="px-6 py-4">{order.orderId}</td>
                  <td className="px-6 py-4">{order.orderDate}</td>
                  <td className="px-6 py-4">{order.deliveryStatus}</td>
                  <td className="px-6 py-4">{order.buyerEmail}</td>
                  <td className="px-6 py-4">{order.address}</td>
                  <td className="px-6 py-4 text-right">{order.totalPrice?.toLocaleString()}원</td>
                  <td className="px-6 py-4 opacity-0 group-hover:opacity-100 transition-opacity">
                    <button
                      onClick={(e) => handleDeleteClick(e, order.orderId)}
                      disabled={isDeleting}
                      className="px-3 py-1 bg-red-500 text-white rounded hover:bg-red-600 disabled:opacity-50"
                    >
                      삭제
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={7} className="px-6 py-4 text-center">
                  주문 내역이 없습니다.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

    <div className="flex justify-center mt-4 gap-2">
        {totalPages > 0 && (
          <div className="flex items-center gap-2 mt-4 justify-center">
            <button
              onClick={() => handlePageChange(currentPage - 1)}
              disabled={currentPage <= 1}
              className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
            >
              이전
            </button>
            {Array.from({ length: totalPages }, (_, i) => (
              <button
                key={i}
                onClick={() => handlePageChange(i + 1)}
                className={`px-3 py-1 rounded ${
                  currentPage === i + 1 
                    ? 'bg-blue-500 text-white' 
                    : 'bg-gray-200 hover:bg-gray-300'
                }`}
              >
                {i + 1}
              </button>
            ))}
            <button
              onClick={() => handlePageChange(currentPage + 1)}
              disabled={currentPage >= totalPages}
              className="px-3 py-1 bg-gray-200 rounded disabled:opacity-50"
            >
              다음
            </button>
          </div>
        )}
      </div>

      <DeleteConfirmModal
        isOpen={isDeleteModalOpen}
        orderId={selectedOrderId}
        onConfirm={handleDeleteConfirm}
        onCancel={() => setIsDeleteModalOpen(false)}
      />
    </div>
  );
}