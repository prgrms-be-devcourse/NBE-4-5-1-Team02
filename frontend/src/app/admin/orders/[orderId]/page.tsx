"use client";
import React, { useEffect, useState } from 'react';

interface OrderData {
    message: string,
    orderUuid: string,
    user: {
        id: string,
        email: string,
        createdDate: string,
        modifiedDate: string
    },
    createDate: string,
    modifiedDate: string,
    totalAmount: number,
    deliveryAddress: string,
    zipCode: number,
    deliveryStatus: string,
    code: number
}

export default function Page() {
    const [orderData, setOrderData] = useState<OrderData | null>(null);

    // formData에 user 객체를 포함하여 상태를 설정
    const [formData, setFormData] = useState({
        orderUuid: "",
        user: { email: "", id: "", createdDate: "", modifiedDate: "" },  // user 객체 추가
        createDate: "",
        modifiedDate: "",
        deliveryAddress: "",
        zipCode: "",
        deliveryStatus: "",
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                // API 호출
                const response = await fetch("http://localhost:8080/admin/orders/order-11111-22222-33331");

                if (!response.ok) {
                    throw new Error("에러");
                }

                const rsData = await response.json();
                setOrderData(rsData.data);  // 데이터를 상태로 저장

                // formData에 user 객체 포함하여 값 설정
                setFormData({
                    orderUuid: rsData.data.orderUuid,
                    user: rsData.data.user,  // user 객체 할당
                    createDate: rsData.data.createDate,
                    modifiedDate: rsData.data.modifiedDate,
                    deliveryAddress: rsData.data.deliveryAddress,
                    zipCode: rsData.data.zipCode,
                    deliveryStatus: rsData.data.deliveryStatus,
                });
            } catch (err) {
                console.log(err);
            }
        };

        fetchData();  // 컴포넌트가 마운트될 때 데이터 호출
    }, []);

    const reqUpdateUrl = "http://localhost:8080/admin/orders/" + formData.orderUuid;

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        console.log("폼제출");

        try {

            const requestBody = {
                zipcode: formData.zipCode,
                address: formData.deliveryAddress,
                buyer: {
                    email: formData.user.email
                }
            };
            const response = await fetch(reqUpdateUrl, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json", //요청 헤더 contentType설정
                },
                body: JSON.stringify(requestBody),
            });
            
            if (response.ok) {
                const result = await response.json();
                console.log("서버응답", result);
            } else {
                console.log("서버 요청 실패");
            }
        } catch (error) {
            console.error("요청 중 오류 발생", error);
        }
    }

    const handleChange = (
        e: React.ChangeEvent<HTMLInputElement>,
        field: keyof typeof formData
    ) => {
        setFormData({
            ...formData,
            [field]: e.target.value,
        });
    };
    console.log(orderData);

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <button type="submit">수정</button>
                <button>취소</button>
                <label>order no.</label>
                <input type="text" style={{ color: 'black' }} value={formData.orderUuid || ""} readOnly />
                <label>이메일</label>
                <input type="text" style={{ color: 'black' }} value={formData.user.email || ""} readOnly /> {/* user.email 사용 */}
                <label>결제일</label>
                <input type="text" style={{ color: 'black' }} value={formData.createDate || ""} readOnly />
                <label>수정일</label>
                <input type="text" style={{ color: 'black' }} value={formData.modifiedDate || ""} readOnly />
                <label>주소</label>
                <input type="text" style={{ color: 'black' }} value={formData.deliveryAddress || ""} onChange={(e) => handleChange(e, "deliveryAddress")} />
                <label>우편번호</label>
                <input type="text" style={{ color: 'black' }} value={formData.zipCode || ""} onChange={(e) => handleChange(e, "zipCode")} />
                <label>배송상태</label>
                <input type="text" style={{ color: 'black' }} value={formData.deliveryStatus || ""} readOnly />
            </form>
        </div>
    );
}