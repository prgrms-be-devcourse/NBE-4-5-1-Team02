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
    /* // 컴포넌트 마운트 시 localStorage에서 이메일 확인
    const savedEmail = localStorage.getItem('userEmail'); */

    // 컴포넌트 마운트 시 SessionStorage에서 이메일 확인
    const savedEmail = sessionStorage.getItem('userEmail');
    if (savedEmail) {
      setEmail(savedEmail);
      fetchOrders(savedEmail);
    } else {
      setIsModalOpen(true);
    }
  }, []);

  useEffect(() => {
    console.log("Modal open state changed:", isModalOpen);
  }, [isModalOpen]);

  const fetchOrders = async (emailToFetch: string) => {
    try {
      const response = await fetch(
        `/api/orders?email=${emailToFetch}&page=0&size=10`,
        {
          headers: {
            'Content-Type': 'application/json',
          }
        }
      );

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || '주문 목록을 불러오는데 실패했습니다.');
      }

      const data = await response.json();
      setRsData(data);
      return true; // 성공 시 true 반환
    } catch (error) {
      console.error('Fetch Error:', error);
      alert('데이터를 불러오는데 실패했습니다.');
      return false; // 실패 시 false 반환
    }
  };

  const handleEmailSubmit = async (submittedEmail: string) => {
    // 이메일 형식 검증
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(submittedEmail)) {
      alert('잘못된 이메일 형식입니다. 다시 입력해주세요.');
      return; // 모달 유지
    }

    try {
      const success = await fetchOrders(submittedEmail);
      if (success) {
        sessionStorage.setItem('userEmail', submittedEmail);
        setEmail(submittedEmail);
        setIsModalOpen(false); // API 호출이 성공한 경우에만 모달 닫기
      }
    } catch (error) {
      console.error('Fetch Error:', error);
      alert('데이터를 불러오지 못했습니다.');
      // 실패 시 모달 유지
    }
  };

  return (
    <>
      <EmailModal isOpen={isModalOpen} onSubmit={handleEmailSubmit} />
      <OrderClientPage rsData={rsData || { data: { content: [] } }} /> {/* rsData가 null 또는 undefined라면, 기본값으로 전달 */}
    </>
  );
}

/* 
  [localStorage 란]
  - 브라우저에 데이터를 저장하는 방법
  - 브라우저 쿠키와 비슷한 역할
  - 쿠키와 달리 서버에 전송되지 않음
  - 브라우저 내에서만 데이터를 저장
  - 데이터 타입 제한 없음
  - 어디에서든 접근 및 사용 가능

  [localStorage 사용법법]
  // 데이터 저장
  localStorage.setItem('key', 'value');

  // 데이터 조회
  const value = localStorage.getItem('key');

  // 특정 데이터 삭제
  localStorage.removeItem('key');

  // 모든 데이터 삭제
  localStorage.clear();
*/