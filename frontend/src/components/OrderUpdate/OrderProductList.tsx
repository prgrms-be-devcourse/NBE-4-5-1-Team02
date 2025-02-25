"use client";

import React, { useState, useEffect } from "react";
import { useSearchParams, useRouter } from "next/navigation";
import { Button } from "@/components/ui/button";
import {
  Pagination,
  PaginationContent,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";

interface Product {
  productUuid: string;
  productName: string;
  productPrice: number;
  imageUrl?: string;
}

interface PaginationData<T> {
  data: T[];
  page: number;
  totalPages: number;
}

interface PaginatedProductListProps {
  orderId: string;
  onAddProduct: (product: Product) => void;
  onDeleteProduct: (product: Product) => void;
}

export default function OrderProductList({
  orderId,
  onAddProduct,
  onDeleteProduct,
}: PaginatedProductListProps) {
  const [products, setProducts] = useState<PaginationData<Product>>({
    data: [],
    page: 0,
    totalPages: 0,
  });
  const [loading, setLoading] = useState(false);

  const searchParams = useSearchParams();
  const router = useRouter();

  // 이미지 경로
  const BASE_IMAGE_PATH = process.env.NEXT_PUBLIC_IMAGE_BASE_PATH || "";

  // size 기본값 10
  const pageParam = searchParams.get("page") || "0";
  const sizeParam = searchParams.get("size") || "10";
  const page = parseInt(pageParam, 10);
  const [pageSize, setPageSize] = useState<number>(parseInt(sizeParam, 10));

  useEffect(() => {
    fetchProducts(page, pageSize);
  }, [page, pageSize]);

  async function fetchProducts(pageNum: number, size: number) {
    try {
      setLoading(true);
      const response = await fetch(
        `/api/products?page=${pageNum}&size=${size}`
      );
      if (!response.ok) {
        throw new Error("상품 목록 로딩 실패");
      }
      const result = await response.json();
      setProducts(result.data);
    } catch (error) {
      console.error(error);
      alert("상품 목록을 불러올 수 없습니다.");
    } finally {
      setLoading(false);
    }
  }

  const handlePageChange = (targetPage: number) => {
    router.push(`?page=${targetPage}&size=${pageSize}`);
  };

  const handlePageSizeChange = (newSize: number) => {
    setPageSize(newSize);
    router.push(`?page=0&size=${newSize}`);
  };

  const handleGoBack = () => {
    router.push(`/orders/${orderId}`);
  };

  if (loading) return <div>로딩 중...</div>;

  return (
    <div className="flex-1 flex flex-col">
      <div className="flex items-center justify-between mb-4">
        <div className="flex items-center gap-4">
          <h2 className="text-2xl font-bold">전체 제품</h2>
          <span className="text-sm">페이지당 항목 수</span>
          <div className="w-28">
            <Select
              value={pageSize.toString()}
              onValueChange={(value) => handlePageSizeChange(Number(value))}
            >
              <SelectTrigger className="w-full">
                <SelectValue placeholder="개수" />
              </SelectTrigger>
              <SelectContent>
                <SelectItem value="5">5개</SelectItem>
                <SelectItem value="10">10개</SelectItem>
                <SelectItem value="15">15개</SelectItem>
                <SelectItem value="20">20개</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        <Button variant="outline" onClick={handleGoBack}>
          돌아가기
        </Button>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-4 lg:grid-cols-5 gap-4">
        {products.data.map((item) => {
          // "/img"시작시 -> 제거 : url 변경
          const resolvedImageUrl = item.imageUrl
            ? item.imageUrl.startsWith("/img")
              ? item.imageUrl.replace("/img", "")
              : item.imageUrl
            : `${BASE_IMAGE_PATH}/${item.productName.replace(/\s+/g, "")}.png`;

          return (
            <div
              key={item.productUuid}
              className="border rounded p-3 flex flex-col items-center"
            >
              <img
                src={resolvedImageUrl}
                alt={item.productName}
                className="w-full h-24 object-cover mb-2"
              />
              <p className="font-medium">{item.productName}</p>
              <p className="text-sm">{item.productPrice.toLocaleString()}원</p>
              <div className="flex gap-2 mt-3">
                <Button size="sm" onClick={() => onAddProduct(item)}>
                  추가
                </Button>
                <Button
                  variant="destructive"
                  size="sm"
                  onClick={() => onDeleteProduct(item)}
                >
                  삭제
                </Button>
              </div>
            </div>
          );
        })}
      </div>

      {/* 페이지네이션 */}
      {products.totalPages > 1 && (
        <Pagination className="mt-4">
          <PaginationContent>
            {page > 0 && (
              <PaginationItem>
                <PaginationPrevious
                  onClick={() => handlePageChange(page - 1)}
                />
              </PaginationItem>
            )}
            {Array.from({ length: products.totalPages }, (_, i) => (
              <PaginationItem key={i}>
                <PaginationLink
                  onClick={() => handlePageChange(i)}
                  isActive={page === i}
                >
                  {i + 1}
                </PaginationLink>
              </PaginationItem>
            ))}
            {page < products.totalPages - 1 && (
              <PaginationItem>
                <PaginationNext onClick={() => handlePageChange(page + 1)} />
              </PaginationItem>
            )}
          </PaginationContent>
        </Pagination>
      )}
    </div>
  );
}
