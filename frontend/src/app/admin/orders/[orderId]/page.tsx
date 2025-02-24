import AdminOrderDetailClientPage from "./AdminOrderDetailClientPage";

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

export default async function OrderDetailPage({ params }: { params: { orderId: string } }) {
  const {orderId} =  await params;
  const response = await fetch(`http://localhost:8080/admin/orders/${orderId}`, {
    headers: {
      'Content-Type': 'application/json',
    },
  });
  
  if (!response.ok) {
    throw new Error('주문 정보를 불러오는데 실패했습니다.');
  }

  const orderDetail: OrderDetailResponse = await response.json();
  
  return <AdminOrderDetailClientPage orderDetail={orderDetail} />;
}
