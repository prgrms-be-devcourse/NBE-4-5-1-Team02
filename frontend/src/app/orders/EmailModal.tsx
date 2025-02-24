"use client";

import { Dialog, DialogContent, DialogHeader, DialogTitle } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";

interface EmailModalProps {
  isOpen: boolean;
  onSubmit: (email: string) => void; // 이메일을 전달하는 콜백 함수수
}

// 컴포넌트
// TODO : async를 붙여야 하나? 
export default function EmailModal({ isOpen, onSubmit }: EmailModalProps) { // EmailModal 컴포넌트에게 어떤 Props를 전달할지 정의 (이외의 것이 있으면 무시)
  if (!isOpen) return null;

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => { // e는 이벤트 객체, `폼 제출 시` React가 전달하는 이벤트 / 이벤트가 발생한 요소가 <form> 요소임을 명시
    e.preventDefault(); // 페이지 리로드 방지지
    const formData = new FormData(e.currentTarget); // FormData 객체에서 폼 데이터를 추출 :: 해당 폼 내의 모든 입력 필드의 데이터를 자동으로 수집집
    const email = formData.get('email') as string; // 폼 데이터에서 email 필드의 값을 수집집 :: 타입 단언 (as string) = 문자열임을 컴파일러에게 보장한다는 의미미

    // 이메일 형식 검증
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(email)) {
        alert('잘못된 이메일 형식입니다. 다시 입력해주세요.');
        return; // 잘못된 형식일 경우 제출하지 않음
    }

    onSubmit(email); // 추출된 이메일을 onSubmit 함수에 전달
  };

  return (
    <Dialog open={isOpen}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>이메일 입력</DialogTitle>
        </DialogHeader>
        <form onSubmit={handleSubmit} className="space-y-4">
          <Input
            type="email"
            name="email"
            placeholder="이메일을 입력하세요"
            required
            className="w-full"
          />
          <Button type="submit" className="w-full">
            확인
          </Button>
        </form>
      </DialogContent>
    </Dialog>
  );
} 