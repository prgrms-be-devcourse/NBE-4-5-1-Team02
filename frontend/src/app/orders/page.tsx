"use client";

import { useState, useEffect } from "react";
import OrderClientPage from "./OrderClientPage";
import EmailModal from "./EmailModal";

// 컴포넌트
export default function Page() {
  const [email, setEmail] = useState<string | null>(null); // 이메일 상태 (초기값 null)
  const [rsData, setRsData] = useState<any>(null); // API 호출을 받아온 주문 데이터 (초기값 null)
  const [isModalOpen, setIsModalOpen] = useState(false); // 모달 상태 (초기값 false)

  useEffect(() => {
    // 컴포넌트 마운트 시 localStorage에서 이메일 확인
    const savedEmail = localStorage.getItem('userEmail');
    if (savedEmail) {
      setEmail(savedEmail);
      fetchOrders(savedEmail);
    } else {
      setIsModalOpen(true);
    }
  }, []);

  const fetchOrders = async (emailToFetch: string) => {
    try {
      const response = await fetch(
        `/api/orders?email=${emailToFetch}&page=0&size=10`, // /api를 경로에 포함시키면, 이 URL이 일반적인 페이지(예: HTML 렌더링)를 반환하는 것이 아니라 데이터(JSON 등)를 반환하는 API 엔드포인트임을 쉽게 구분 가능
        {
          headers: {
            'Content-Type': 'application/json', // 요청 헤더에 컨텐츠 타입 지정 (안전성 증가)
          }
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(`API 오류: ${errorData.message || '주문 목록을 불러오는데 실패했습니다.'}`);
      }

      const data = await response.json();
      setRsData(data);
    } catch (error) {
      console.error('Fetch Error:', error);
      alert('데이터를 불러오는데 실패했습니다.');
    }
  };

  const handleEmailSubmit = async (submittedEmail: string) => {
    // localStorage에 이메일 저장
    localStorage.setItem('userEmail', submittedEmail);
    setEmail(submittedEmail);
    await fetchOrders(submittedEmail);
    setIsModalOpen(false);
  };

  return (
    <>
      <EmailModal isOpen={isModalOpen} onSubmit={handleEmailSubmit} />
      <OrderClientPage rsData={rsData || { data: { content: [] } }} /> {/* rsData가 null 또는 undefined라면, 기본값으로 전달 */}
    </>
  );
}