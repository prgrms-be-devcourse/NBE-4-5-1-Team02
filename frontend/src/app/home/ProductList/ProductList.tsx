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

type TableProductData = {
  id?: string;
  imageUrl?: string;
  name?: string;
  description?: string;
  price?: number;
};

type PaginationDatatProductDto =
  components["schemas"]["PaginationDataProductDto"];

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
    if (changedProductMap.has(item.productUuid!)) {
      // 상품 수량을 1 늘린다
      changedProductMap.set(item.productUuid!, {
        product: item,
        quantity: changedProductMap.get(item.productUuid!)!.quantity + 1,
      });
      // 상품이 이 맵에 존재하지 않는다면
    } else {
      // 상품 수량을 1로 해 맵에 새로 추가한다
      changedProductMap.set(item.productUuid!, {
        product: item,
        quantity: 1,
      });
    }
  };

  const data = products.data!.map((item) => {
    return {
      id: item.productUuid,
      imageUrl: item.imageUrl,
      name: item.productName,
      description: item.productDescription,
      price: item.productPrice,
    };
  });

  return (
    <div className="w-[100%] flex flex-col justify-center flex-grow my-3">
      <div className="w-[80%] self-center h-[100%]">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead>Image</TableHead>
              <TableHead>name</TableHead>
              <TableHead>price</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {data.map((item) => (
              <TableRow key={item.id}>
                <TableCell className="w-fit p-0">
                  <Image
                    src={item.imageUrl}
                    alt={item.name!}
                    width={50}
                    height={50}
                    className="rounded"
                  />
                </TableCell>
                <TableCell>
                  <div>
                    <span>{item.name}</span>
                    <br />
                    <span>{item.description}</span>
                  </div>
                </TableCell>
                <TableCell>{item.price}</TableCell>
                <TableCell>
                  <div>
                    <input
                      type="button"
                      value="추가"
                      onClick={(e) => {
                        e.preventDefault();
                        addProduct(item);
                      }}
                    ></input>
                    <input type="button" value="삭제"></input>
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
