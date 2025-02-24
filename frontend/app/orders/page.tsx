import { getOrders } from "@/lib/api"; // API 호출 함수 import
import OrdersClientPage from "./OrderClientPage";
import { Badge } from "@/components/ui/badge"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip"
import { format } from "date-fns"
import { ko } from "date-fns/locale"

export default async function OrdersPage({
  searchParams,
}: {
  searchParams: { page?: string };
}) {
  const page = Number(searchParams.page) || 0;
  
  // API로부터 주문 데이터 가져오기
  const orders = await getOrders(page);

  return <OrdersClientPage rsData={orders} />;
}

// 주문 상태에 따른 Badge 컴포넌트
function OrderStatusBadge({ status }: { status: string }) {
  const getStatusStyle = (status: string) => {
    switch (status) {
      case "PENDING":
        return { label: "대기중", variant: "warning" }
      case "PROCESSING":
        return { label: "처리중", variant: "default" }
      case "COMPLETED":
        return { label: "완료", variant: "success" }
      case "CANCELLED":
        return { label: "취소됨", variant: "destructive" }
      default:
        return { label: status, variant: "secondary" }
    }
  }

  const { label, variant } = getStatusStyle(status)
  return <Badge variant={variant as any}>{label}</Badge>
} 