import { components } from "@/lib/backend/apiV1/schema";
import { OrderWithoutItemDto } from "./ClientPage";
import { Separator } from "@/components/ui/separator"
import { Badge } from "@/components/ui/badge"
import { Card, CardHeader, CardContent } from "@/components/ui/card"

const deliveryStatus = new Map([
  ["PENDING", "배송 전"],
  ["SHIPPED", "배송 중"],
  ["DELIVERED", "배송 됨"],
  ["CANCELED", "취소됨"],
]);

export default function UserData({ order }: { order?: OrderWithoutItemDto }) {
  return (
    <Card className="w-full">
      <CardHeader>
        <h3 className="text-2xl font-bold">주문 정보</h3>
        <Separator className="my-2" />
      </CardHeader>
      <CardContent>
        <div className="space-y-4">
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">주문 번호</span>
            <span className="font-medium">{order?.orderUuid}</span>
          </div>
          <Separator />
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">구매자</span>
            <span className="font-medium">{order?.user!.email}</span>
          </div>
          <Separator />
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">주문 일자</span>
            <span className="font-medium">{order?.createDate}</span>
          </div>
          <Separator />
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">총 결제 금액</span>
            <span className="font-medium">{order?.totalAmount.toLocaleString()}원</span>
          </div>
          <Separator />
          <div className="flex items-center justify-between">
            <span className="text-sm text-muted-foreground">배송 상태</span>
            <Badge variant="outline">
              {order?.deliveryStatus && deliveryStatus.has(order.deliveryStatus)
                ? deliveryStatus.get(order.deliveryStatus)
                : "배송 중"}
            </Badge>
          </div>
        </div>
      </CardContent>
    </Card>
  );
}
