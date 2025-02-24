"use client";

import { components } from "@/lib/backend/apiV1/schema";
import React, { useEffect, useState } from "react";
import Image from "next/image";
import {
  Table,
  TableHeader,
  TableRow,
  TableHead,
  TableBody,
  TableCell,
} from "@/components/ui/table";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";
import { Button } from "@/components/ui/button";
import { useSearchParams } from "next/navigation";

type TableProductData = {
  id?: string;
  imageUrl?: string;
  name?: string;
  description?: string;
  category?: string;
  price?: number;
};

type PaginationDatatProductDto =
  components["schemas"]["PaginationDataProductDto"];

function toProductDto(tableProductData: TableProductData) {
  return {
    productUuid: tableProductData.id,
    productName: tableProductData.name,
    productPrice: tableProductData.price,
    category: tableProductData.category,
    imageUrl: tableProductData.imageUrl,
    productDescription: tableProductData.description,
  };
}

export default function ProductList({
  products,
  productsMap,
  setProductsMap,
}: {
  products: PaginationDatatProductDto;
  productsMap: Map<
    string,
    { product: components["schemas"]["ProductDto"]; quantity: number }
  >;
  setProductsMap: React.Dispatch<
    React.SetStateAction<
      Map<
        string,
        { product: components["schemas"]["ProductDto"]; quantity: number }
      >
    >
  >;
}) {
  const [pageNums, setPageNums] = useState<number[]>([]);
  const searchParams = useSearchParams();

  const createPageUrl = (pageNum: number) => {
    const params = new URLSearchParams(searchParams.toString());
    params.set("page", pageNum.toString());
    return `/?${params.toString()}`;
  };

  useEffect(() => {
    const totalPages = products.totalPages || 0;
    const currentPage = products.page || 0;
    const pages = [];
    const maxPages = 5;

    let startPage = Math.max(
      0,
      Math.min(currentPage - Math.floor(maxPages / 2), totalPages - maxPages)
    );
    let endPage = Math.min(startPage + maxPages, totalPages);

    if (endPage - startPage < maxPages) {
      startPage = Math.max(0, endPage - maxPages);
    }

    for (let i = startPage; i < endPage; i++) {
      pages.push(i);
    }

    setPageNums(pages);
  }, [products.totalPages, products.page]);

  // 상품 추가
  const addProduct = (item: TableProductData) => {
    // 값을 바꿀 맵 선언
    const changedProductMap = new Map<
      string,
      {
        product: components["schemas"]["ProductDto"];
        quantity: number;
      }
    >(productsMap);
    // 만약 상품이 이 맵맵에 존재한다면
    if (changedProductMap.has(item.id!)) {
      // 상품 수량을 1 늘린다
      changedProductMap.set(item.id!, {
        product: toProductDto(item),
        quantity: changedProductMap.get(item.id!)!.quantity + 1,
      });
      // 상품이 이 맵에 존재하지 않는다면
    } else {
      // 상품 수량을 1로 해 맵에 새로 추가한다
      changedProductMap.set(item.id!, {
        product: toProductDto(item),
        quantity: 1,
      });
    }
    console.log(changedProductMap);
    setProductsMap(changedProductMap);
  };
  const deleteProduct = (item: TableProductData) => {
    // 값을 바꿀 맵 선언
    const changedProductMap = new Map<
      string,
      {
        product: components["schemas"]["ProductDto"];
        quantity: number;
      }
    >(productsMap);
    // 만약 상품이 존재하고 수량이 1보다 크면
    if (changedProductMap.has(item.id!)) {
      if (changedProductMap.get(item.id!)!.quantity! > 1) {
        // 상품 수량을 1 줄인다
        changedProductMap.set(item.id!, {
          product: toProductDto(item),
          quantity: changedProductMap.get(item.id!)!.quantity - 1,
        });
        // 상품이 존재하고 수량이 1개라면
      } else if (changedProductMap.get(item.id!)?.quantity == 1) {
        // 상품을 맵에서 제거한다.
        changedProductMap.delete(item.id!);
      }
    }
    setProductsMap(changedProductMap);
  };

  const data = products.data!.map((item) => {
    return {
      id: item.productUuid,
      imageUrl: item.imageUrl,
      name: item.productName,
      description: item.productDescription,
      price: item.productPrice,
      category: item.category,
    };
  });

  return (
    <div className="flex-1 flex flex-col">
      <div className="overflow-x-auto">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead className="w-24">
                <span className="text-lg font-semibold">이미지</span>
              </TableHead>
              <TableHead className="w-2/3">
                <span className="text-lg font-semibold">상품 이름 및 설명</span>
              </TableHead>
              <TableHead className="w-32">
                <span className="text-lg font-semibold">가격</span>
              </TableHead>
              <TableHead className="w-40"></TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {data.map((item) => (
              <TableRow key={item.id}>
                <TableCell className="p-2">
                  <Image
                    src={"/image.png"}
                    alt={item.name!}
                    width={50}
                    height={50}
                    className="rounded object-cover"
                  />
                </TableCell>
                <TableCell>
                  <div className="space-y-1">
                    <span className="text-lg font-medium block">
                      {item.name}
                    </span>
                    <span className="text-sm text-gray-600">
                      {item.description}
                    </span>
                  </div>
                </TableCell>
                <TableCell>
                  <span className="text-lg">
                    {item.price?.toLocaleString()}원
                  </span>
                </TableCell>
                <TableCell>
                  <div className="flex gap-2">
                    <Button
                      type="button"
                      size="sm"
                      onClick={(e) => {
                        e.preventDefault();
                        addProduct(item);
                      }}
                    >
                      추가
                    </Button>
                    <Button
                      type="button"
                      size="sm"
                      variant="destructive"
                      onClick={(e) => {
                        e.preventDefault();
                        deleteProduct(item);
                      }}
                    >
                      삭제
                    </Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
      <div className="mt-4">
        <Pagination>
          <PaginationContent>
            {products.page! > 0 ? (
              <PaginationItem>
                <PaginationPrevious
                  href={createPageUrl(Number(products.page!) - 1)}
                />
              </PaginationItem>
            ) : null}
            {products.page === 0 ? null : (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}
            {pageNums.map((value, idx) => {
              return (
                <PaginationItem key={idx}>
                  <PaginationLink
                    isActive={value === products.page}
                    href={createPageUrl(value)}
                  >
                    {value + 1}
                  </PaginationLink>
                </PaginationItem>
              );
            })}
            {products.page === products.totalPages! - 1 ? null : (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}
            <PaginationItem>
              <PaginationNext
                href={createPageUrl(Number(products.page!) + 1)}
                disabled={products.page === products.totalPages! - 1}
              />
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>
    </div>
  );
}
