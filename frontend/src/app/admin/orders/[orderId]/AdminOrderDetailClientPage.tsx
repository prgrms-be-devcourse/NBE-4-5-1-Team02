"use client";

import Link from "next/link";
import { formatDate } from "@/utils/utility";
import { useState, useEffect } from "react";

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

export default function AdminOrderDetailClientPage({
  orderDetail,
}: {
  orderDetail: OrderDetailResponse;
}) {
  const { data } = orderDetail;

  const [formData, setFormData] = useState({
    address: data.deliveryAddress,
    zipCode: data.zipCode.toString(),
    totalAmount: data.totalAmount.toLocaleString(),
    buyer: { email: data.user.email || "" },
  });

  
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>,
    field: keyof typeof formData
  ) => {
    if (field === "totalAmount") {
      const value = e.target.value.replace(/[^\d]/g, ''); // 숫자만 남기기
      setFormData({
        ...formData,
        totalAmount: value ? Number(value) : 0,
      });
    } else if (field === "buyer") {
      setFormData({
        ...formData,
        buyer: { ...formData.buyer, email: e.target.value },
      });
    } else {
      setFormData({
        ...formData,
        [field]: e.target.value,
      });
    }
  };

  const handleBlur = () => {
    // totalAmount가 숫자이면 포맷 적용
    if (formData.totalAmount) {
      setFormData({
        ...formData,
        totalAmount: formData.totalAmount.toLocaleString(),
      });
    }
  };

  const handleSubmit = async () => {
    if (!confirm("수정하시겠습니까?")) {
      return;
    }
    // zipCode를 다시 숫자로 변환
    const requestBody = {
      zipcode: Number(formData.zipCode), // 숫자로 변환
      address: formData.address,
      totalAmount: Number(formData.totalAmount.replace(/,/g, '')), // 포맷 제거 후 숫자로 변환
      buyer: {
        email: formData.buyer.email,
      },
    };

    console.log(requestBody);

    try {
      const response = await fetch(
        `http://localhost:8080/admin/orders/${data.orderUuid}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestBody),
        }
      );

      if (response.ok) {
        const result = await response.json();
        alert("주문 정보가 수정되었습니다.");
        window.location.reload();
      } else {
        alert("수정에 실패했습니다.");
      }
    } catch (error) {
      console.error("Error updating order:", error);
      alert("서버와의 연결에 문제가 발생했습니다.");
    }
  };

  return (
    <div className="container mx-auto p-6">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">주문 상세 정보</h1>
        <div>
          <Link
            href="/admin/orders"
            className="px-4 py-2 bg-gray-200 rounded-md hover:bg-gray-300 transition-colors"
          >
            목록으로 돌아가기
          </Link>
        </div>
      </div>

      <div className="bg-white rounded-lg shadow-md p-6">
        <form className="grid grid-cols-2 gap-4">
          <div className="space-y-4">
            <h2 className="text-xl font-semibold">주문 정보</h2>
            <p>
              <span className="font-medium">주문 ID:</span>{" "}
              <input type="text" value={data.orderUuid || ""} readOnly />
            </p>
            <p>
              <span className="font-medium">주문 일시:</span>{" "}
              <input
                type="text"
                value={formatDate(data.createDate) || ""}
                readOnly
              />
            </p>
            <p>
              <span className="font-medium">배송 상태:</span>{" "}
              <input
                type="text"
                value={data.deliveryStatus || ""}
                readOnly
              />
            </p>
            <p>
              <span className="font-medium">총 주문금액:</span>{" "}
              <input
                type="text"
                value={formData.totalAmount}
                onChange={(e) => handleChange(e, "totalAmount")}
                onBlur={handleBlur} // 포커스를 잃을 때 포맷 적용
              />
              원
            </p>
          </div>

          <div className="space-y-4">
            <h2 className="text-xl font-semibold">사용자 정보</h2>
            <p>
              <span className="font-medium">사용자 ID:</span>{" "}
              <input type="text" value={data.user.id || ""} readOnly />
            </p>
            <p>
              <span className="font-medium">이메일:</span>{" "}
              <input
                type="text"
                value={formData.buyer.email || ""}
                readOnly
              />
            </p>
            <p>
              <span className="font-medium">배송 주소:</span>{" "}
              <input
                type="text"
                value={formData.address || ""}
                onChange={(e) => handleChange(e, "address")}
              />
            </p>
            <p>
              <span className="font-medium">우편번호:</span>{" "}
              <input
                type="text"
                value={formData.zipCode || ""}
                onChange={(e) => handleChange(e, "zipCode")}
              />
            </p>
          </div>
        </form>
        <div className="mt-4 flex justify-between">
          <div></div>
          <button
            onClick={handleSubmit}
            className="px-4 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600"
          >
            수정
          </button>
        </div>
      </div>
    </div>
  );
}