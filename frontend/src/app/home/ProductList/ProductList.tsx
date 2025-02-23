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

  useEffect(() => {
    let startPage = products.totalPages! - 5;
    let cnt = 0;
    const pages = [];
    while (cnt < 5) {
      if (startPage >= products.totalPages!) {
        break;
      }
      if (startPage < 0) {
        startPage++;
        continue;
      } else {
        pages.push(startPage);
        startPage++;
        cnt++;
      }
    }
    setPageNums(pages);
  }, []);

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
    <div className="w-[100%] flex flex-col justify-center flex-grow my-3">
      <div className="w-[100%] self-center h-[100%]">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>
                <span className="text-2xl font-semibold">이미지</span>
              </TableHead>
              <TableHead>
                <span className="text-2xl font-semibold">
                  상품 이름 및 설명
                </span>
              </TableHead>
              <TableHead>
                <span className="text-2xl font-semibold">가격</span>
              </TableHead>
              <TableHead className="w-[5%]"></TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {data.map((item) => (
              <TableRow key={item.id}>
                <TableCell className="w-fit p-0">
                  <Image
                    src={'/image.png'}
                    alt={item.name!}
                    width={50}
                    height={50}
                    className="rounded"
                  />
                </TableCell>
                <TableCell className="w-[50%]">
                  <div>
                    <span className="text-xl">{item.name}</span>
                    <br />
                    <span className="text-l">{item.description}</span>
                  </div>
                </TableCell>
                <TableCell className="w-fit">
                  <span className="text-l">{item.price}</span>
                </TableCell>
                <TableCell>
                  <div className="flex w-fit justify-end">
                    <Button
                      type="button"
                      onClick={(e) => {
                        e.preventDefault();
                        addProduct(item);
                      }}
                    >
                      추가
                    </Button>
                    <Button
                      type="button"
                      className="bg-red-500"
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
      <Pagination>
        <PaginationContent>
          {products.page! > 0 ? (
            <PaginationItem>
              <PaginationPrevious
                href={`?page=${String(Number(products.page!) - 1)}`}
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
                  href={`?page=${String(value)}`}
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
          {products.page! < products.totalPages! - 1 ? (
            <PaginationItem>
              <PaginationNext
                href={`?page=${String(products.page! + 1)}`}
              ></PaginationNext>
            </PaginationItem>
          ) : null}
        </PaginationContent>
      </Pagination>
    </div>
  );

  //   <div>
  //     <ul>
  //       {products!.data!.map((item, idx) => (
  //         <li key={idx}>
  //           <div>image:{item.imageUrl}</div>
  //           <div>
  //             <div>productName:{item.productName}</div>
  //             <div>productDescription:{item.productDescription}</div>
  //             <div>price:{item.productPrice}</div>
  //             <input
  //               type="button"
  //               value="추가"
  //               onClick={(e) => {
  //                 e.preventDefault();
  //                 // 값을 바꿀 맵 선언
  //                 const changedProductMap = new Map<
  //                   string,
  //                   {
  //                     product: components["schemas"]["ProductDto"];
  //                     quantity: number;
  //                   }
  //                 >(productsMap);
  //                 // 만약 상품이 이 맵맵에 존재한다면
  //                 if (changedProductMap.has(item.productUuid!)) {
  //                   // 상품 수량을 1 늘린다
  //                   changedProductMap.set(item.productUuid!, {
  //                     product: item,
  //                     quantity:
  //                       changedProductMap.get(item.productUuid!)!.quantity + 1,
  //                   });
  //                   // 상품이 이 맵에 존재하지 않는다면
  //                 } else {
  //                   // 상품 수량을 1로 해 맵에 새로 추가한다
  //                   changedProductMap.set(item.productUuid!, {
  //                     product: item,
  //                     quantity: 1,
  //                   });
  //                 }
  //                 setProductsMap(changedProductMap);
  //                 console.log("changed");
  //               }}
  //             />
  //           </div>
  //         </li>
  //       ))}
  //     </ul>
  //   </div>
  // );
}
