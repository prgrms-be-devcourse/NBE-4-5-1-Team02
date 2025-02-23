"use client";

import { useState, useEffect } from "react";
import AdminOrdersClientPage from "./AdminOrdersClientPage";
import { useSearchParams } from "next/navigation";

export default function Page() {
  // sessionStorage에서 페이지 크기를 가져오거나 기본값 사용
  const [pageSize, setPageSize] = useState<number>(() => {
    if (typeof window !== 'undefined') {
      const savedPageSize = sessionStorage.getItem('adminOrdersPageSize');
      return savedPageSize ? Number(savedPageSize) : 10;
    }
    return 10;
  });
  
  const [rsData, setRsData] = useState<any>(null);
  const searchParams = useSearchParams();
  const page = Number(searchParams.get("page")) || 1;

  useEffect(() => {
    // 페이지 크기가 변경될 때마다 sessionStorage에 저장
    sessionStorage.setItem('adminOrdersPageSize', pageSize.toString());
    fetchOrders(page);
  }, [page, pageSize]);

  const fetchOrders = async (page: number) => {
    try {
      const response = await fetch(
        `/api/admin/orders?page=${page}&size=${pageSize}`,
        {
          headers: {
            'Content-Type': 'application/json',
          }
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        // 데이터가 없는 경우 (빈 배열)는 정상적인 응답으로 처리
        if (response.status === 200 && errorData.data?.content?.length === 0) {
          setRsData({ data: { content: [] } });
          return;
        }
        throw new Error(`API 오류: ${errorData.message || '주문 목록을 불러오는데 실패했습니다.'}`);
      }

      const data = await response.json();
      setRsData(data);
    } catch (error) {
      console.error('Fetch Error:', error);
      // 에러 발생 시 빈 데이터로 초기화하여 UI 렌더링
      setRsData({ data: { content: [] } });
      alert('데이터를 불러오는데 실패했습니다.');
    }
  };

  const handlePageSizeChange = (newSize: number) => {
    setPageSize(newSize);
    // 페이지 크기 변경 시 첫 페이지로 이동
    if (typeof window !== 'undefined') {
      window.history.pushState({}, '', `?page=1`);
    }
  };

  const deleteOrder = async (orderId: string) => {
    try {
      const response = await fetch(`/api/admin/orders/${orderId}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`API 오류: ${errorData.message || '주문 삭제에 실패했습니다.'}`);
      }

      // 삭제 성공 후 목록 새로고침
      await fetchOrders(page);
      alert('주문이 성공적으로 삭제되었습니다.');
    } catch (error) {
      console.error('Delete Error:', error);
      throw error;
    }
  };

  return (
    <div className="mt-8">
      <AdminOrdersClientPage 
        rsData={rsData || { data: { content: [] } }} 
        onOrderDelete={deleteOrder}
        pageSize={pageSize}
        onPageSizeChange={handlePageSizeChange}
      />
    </div>
  );
}
