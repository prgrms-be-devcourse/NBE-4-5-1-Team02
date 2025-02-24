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
import { Button, buttonVariants } from "@/components/ui/button";
import { useSearchParams } from 'next/navigation';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import Link from "next/link";
import { formatDate } from "@/utils/utility";

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
    params.set('page', pageNum.toString());
    return `/?${params.toString()}`;
  };

  useEffect(() => {
    const totalPages = products.totalPages || 0;
    const currentPage = products.page || 0;
    const pages = [];
    const maxPages = 5;
    
    let startPage = Math.max(0, Math.min(currentPage - Math.floor(maxPages / 2), totalPages - maxPages));
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
      <div className="grid grid-cols-2 gap-3 md:grid-cols-4 lg:grid-cols-5">
        {data.map((item) => (
          <Card key={item.id} className="flex flex-col">
            <CardHeader className="p-3">
              <div className="w-full h-24 relative">
                <Image
                  src={'/image.png'}
                  alt={item.name!}
                  fill
                  className="rounded-t object-cover"
                />
              </div>
            </CardHeader>
            <CardContent className="p-3 pt-1">
              <CardTitle className="text-base mb-1">{item.name}</CardTitle>
              <CardDescription className="text-xs h-6 overflow-hidden">
                {item.description}
              </CardDescription>
              <p className="text-sm font-semibold mt-2">
                {item.price?.toLocaleString()}원
              </p>
            </CardContent>
            <CardFooter className="flex gap-1 justify-end p-3 pt-0">
              <Button
                size="sm"
                className="h-7 text-xs"
                onClick={(e) => {
                  e.preventDefault();
                  addProduct(item);
                }}
              >
                추가
              </Button>
              <Button
                size="sm"
                className="h-7 text-xs"
                variant="destructive"
                onClick={(e) => {
                  e.preventDefault();
                  deleteProduct(item);
                }}
              >
                삭제
              </Button>
            </CardFooter>
          </Card>
        ))}
      </div>
      <div className="mt-4">
        <Pagination>
          <PaginationContent>
            {products.page !== 0 && (
              <PaginationItem>
                <PaginationPrevious 
                  href={`/?page=${products.page! - 1}`}
                />
              </PaginationItem>
            )}
            {products.page! > 1 && (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}
            {pageNums.map((value, idx) => {
              return (
                <PaginationItem key={idx}>
                  <PaginationLink 
                    href={createPageUrl(value)}
                    isActive={value === products.page}
                  >
                    {value + 1}
                  </PaginationLink>
                </PaginationItem>
              );
            })}
            {products.page! < products.totalPages! - 2 && (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}
            {products.page !== products.totalPages! - 1 && (
              <PaginationItem>
                <PaginationNext 
                  href={`/?page=${Number(products.page!) + 1}`}
                />
              </PaginationItem>
            )}
          </PaginationContent>
        </Pagination>
      </div>
    </div>
  );
}
