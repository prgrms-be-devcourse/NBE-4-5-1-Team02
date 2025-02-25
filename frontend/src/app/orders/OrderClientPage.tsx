"use client";

import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useRouter } from "next/navigation";

export default function OrdersClientPage({
  rsData,
}: {
  rsData: components["schemas"]["RsDataOrderListResponse"];
}) {
  const router = useRouter();
  const orderListResponse = rsData.data;
  const currentPage = orderListResponse?.page || 0;
  const totalPages = orderListResponse?.totalPages || 0;

  const handleRowClick = (orderId: string) => {
    router.push(`/orders/${orderId}`);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50">
      <div className="w-full max-w-7xl px-4">
        <Card>
          <CardHeader className="flex flex-row items-center justify-between">
            <CardTitle>주문 목록</CardTitle>
            <Button variant="outline" asChild>
              <Link href="/">메인으로 돌아가기</Link>
            </Button>
          </CardHeader>
          <CardContent>
            <div className="rounded-md border">
              <Table>
                <TableHeader>
                  <TableRow>
                    <TableHead>주문번호</TableHead>
                    <TableHead>결제일</TableHead>
                    <TableHead>배송상태</TableHead>
                    <TableHead>주소</TableHead>
                    <TableHead>이메일</TableHead>
                    <TableHead className="text-right">총 주문금액</TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {orderListResponse?.content?.length ? (
                    orderListResponse.content.map((order) => (
                      <TableRow 
                        key={order.orderId}
                        className="cursor-pointer hover:bg-gray-100"
                        onClick={() => handleRowClick(order.orderId)}
                      >
                        <TableCell>{order.orderId}</TableCell>
                        <TableCell>{order.orderDate}</TableCell>
                        <TableCell>{order.deliveryStatus}</TableCell>
                        <TableCell>{order.address}</TableCell>
                        <TableCell>{order.buyerEmail}</TableCell>
                        <TableCell className="text-right">
                          {order.totalPrice?.toLocaleString()}원
                        </TableCell>
                      </TableRow>
                    ))
                  ) : (
                    <TableRow>
                      <TableCell colSpan={6} className="text-center">
                        주문 내역이 없습니다.
                      </TableCell>
                    </TableRow>
                  )}
                </TableBody>
              </Table>
            </div>

            {totalPages > 0 && (
              <Pagination className="mt-4">
                <PaginationContent>
                  {currentPage > 0 && (
                    <PaginationItem>
                      <PaginationPrevious
                        href={`?page=${currentPage - 1}`}
                      />
                    </PaginationItem>
                  )}
                  {Array.from({ length: totalPages }, (_, i) => (
                    <PaginationItem key={i}>
                      <PaginationLink
                        href={`?page=${i}`}
                        isActive={currentPage === i}
                      >
                        {i + 1}
                      </PaginationLink>
                    </PaginationItem>
                  ))}
                  {currentPage < totalPages - 1 && (
                    <PaginationItem>
                      <PaginationNext
                        href={`?page=${currentPage + 1}`}
                      />
                    </PaginationItem>
                  )}
                </PaginationContent>
              </Pagination>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
}