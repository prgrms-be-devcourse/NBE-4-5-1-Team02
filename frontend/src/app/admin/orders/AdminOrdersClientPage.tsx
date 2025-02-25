"use client";

import { useState } from "react";
import { components } from "@/lib/backend/apiV1/schema";
import Link from "next/link";
import { useRouter } from "next/navigation";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

export default function AdminOrdersClientPage({
  rsData,
  onOrderDelete,
  pageSize,
  onPageSizeChange,
}: {
  rsData: components["schemas"]["RsDataOrderListResponse"];
  onOrderDelete: (orderId: string) => Promise<void>;
  pageSize: number;
  onPageSizeChange: (size: number) => void;
}) {
  const router = useRouter();
  const orderListResponse = rsData.data;
  const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
  const [selectedOrderId, setSelectedOrderId] = useState<string>("");
  const [isDeleting, setIsDeleting] = useState(false);
  const currentPage = orderListResponse?.page || 1;
  const totalPages = orderListResponse?.totalPages || 0;

  const handlePageChange = (page: number) => {
    router.push(`/admin/orders?page=${page}`);
  };

  const handleDeleteClick = (e: React.MouseEvent, orderId: string) => {
    e.stopPropagation();
    setSelectedOrderId(orderId);
    setIsDeleteModalOpen(true);
  };

  const handleDeleteConfirm = async () => {
    try {
      setIsDeleting(true);
      await onOrderDelete(selectedOrderId);
      setIsDeleteModalOpen(false);
    } catch (error) {
      console.error('Delete Error:', error);
      alert('주문 삭제 중 오류가 발생했습니다.');
    } finally {
      setIsDeleting(false);
    }
  };
  
  return (
    <div className="container mx-auto p-4">
      <Card>
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle>관리자 주문 목록</CardTitle>
          <div className="flex items-center gap-4">
            <div className="flex items-center gap-2">
              <span className="text-sm">페이지당 항목 수:</span>
              <Select
                value={pageSize.toString()}
                onValueChange={(value) => onPageSizeChange(Number(value))}
              >
                <SelectTrigger className="w-[100px]">
                  <SelectValue placeholder="선택" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="1">1개</SelectItem>
                  <SelectItem value="5">5개</SelectItem>
                  <SelectItem value="10">10개</SelectItem>
                  <SelectItem value="15">15개</SelectItem>
                  <SelectItem value="20">20개</SelectItem>
                </SelectContent>
              </Select>
            </div>
            <Button variant="outline" asChild>
              <Link href="/">메인으로 돌아가기</Link>
            </Button>
          </div>
        </CardHeader>
        <CardContent>
          <div className="rounded-md border">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>주문번호</TableHead>
                  <TableHead>결제일</TableHead>
                  <TableHead>배송상태</TableHead>
                  <TableHead>이메일</TableHead>
                  <TableHead>주소</TableHead>
                  <TableHead className="text-right">총 주문금액</TableHead>
                  <TableHead className="text-center">작업</TableHead>
                </TableRow>
              </TableHeader>
              <TableBody>
                {orderListResponse?.content?.length ? (
                  orderListResponse.content.map((order) => (
                    <TableRow
                      key={order.orderId}
                      className="cursor-pointer hover:bg-muted/50"
                      onClick={() => router.push(`/admin/orders/${order.orderId}`)}
                    >
                      <TableCell>{order.orderId}</TableCell>
                      <TableCell>{order.orderDate}</TableCell>
                      <TableCell>{order.deliveryStatus}</TableCell>
                      <TableCell>{order.buyerEmail}</TableCell>
                      <TableCell>{order.address}</TableCell>
                      <TableCell className="text-right">
                        {order.totalPrice?.toLocaleString()}원
                      </TableCell>
                      <TableCell className="text-center">
                        <Button
                          variant="destructive"
                          size="sm"
                          onClick={(e) => handleDeleteClick(e, order.orderId)}
                          disabled={isDeleting}
                        >
                          삭제
                        </Button>
                      </TableCell>
                    </TableRow>
                  ))
                ) : (
                  <TableRow>
                    <TableCell colSpan={7} className="text-center">
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
                {currentPage > 1 && (
                  <PaginationItem>
                    <PaginationPrevious
                      onClick={() => handlePageChange(currentPage - 1)}
                    />
                  </PaginationItem>
                )}
                {Array.from({ length: totalPages }, (_, i) => (
                  <PaginationItem key={i}>
                    <PaginationLink
                      onClick={() => handlePageChange(i + 1)}
                      isActive={currentPage === i + 1}
                    >
                      {i + 1}
                    </PaginationLink>
                  </PaginationItem>
                ))}
                {currentPage < totalPages && (
                  <PaginationItem>
                    <PaginationNext
                      onClick={() => handlePageChange(currentPage + 1)}
                    />
                  </PaginationItem>
                )}
              </PaginationContent>
            </Pagination>
          )}
        </CardContent>
      </Card>

      <Dialog open={isDeleteModalOpen} onOpenChange={setIsDeleteModalOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>주문 삭제 확인</DialogTitle>
            <DialogDescription>
              주문번호 {selectedOrderId}를 삭제하시겠습니까?
            </DialogDescription>
          </DialogHeader>
          <DialogFooter>
            <Button
              variant="outline"
              onClick={() => setIsDeleteModalOpen(false)}
            >
              취소
            </Button>
            <Button
              variant="destructive"
              onClick={handleDeleteConfirm}
              disabled={isDeleting}
            >
              삭제
            </Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}