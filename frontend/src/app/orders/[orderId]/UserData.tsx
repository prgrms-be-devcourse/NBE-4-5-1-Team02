import { components } from "@/lib/backend/apiV1/schema";
import { OrderWithoutItemDto } from "./ClientPage";

const deliveryStatus = new Map([
  ["PENDING", "배송 전"],
  ["SHIPPED", "배송 중"],
  ["DELIVERED", "배송 됨"],
  ["CANCELED", "취소됨"],
]);

export default function UserData({ order }: { order?: OrderWithoutItemDto }) {
  return (
    <div>
      <div className="my-10">
        <h3 className="text-3xl font-bold">{order?.orderUuid}</h3>
      </div>

      <div>
        <div className="my-3">
          <span className="text-xl">구매자 : {order?.user!.email}</span>
        </div>
        <div className="my-3">
          <span className="text-xl">주문 일자 : {order?.createDate}</span>
        </div>
        <div className="my-3">
          <span className="text-xl">총 결제 금액 : {order?.totalAmount}</span>
        </div>
        <div>
          <span className="text-xl">
            배송 상태 :{" "}
            {order &&
            order!.deliveryStatus &&
            deliveryStatus.has(order!.deliveryStatus)
              ? deliveryStatus.get(order!.deliveryStatus!)
              : "배송 중"}
          </span>
        </div>
      </div>
    </div>
  );
}
