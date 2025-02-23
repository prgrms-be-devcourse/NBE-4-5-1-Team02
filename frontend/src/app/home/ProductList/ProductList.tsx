"use client";

import { components } from "@/lib/backend/apiV1/schema";
import React from "react";

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
  console.log("productsMap", productsMap);
  return (
    <div>
      <ul>
        {products!.data!.map((item, idx) => (
          <li key={idx}>
            <div>image:{item.imageUrl}</div>
            <div>
              <div>productName:{item.productName}</div>
              <div>productDescription:{item.productDescription}</div>
              <div>price:{item.productPrice}</div>
              <input
                type="button"
                value="추가"
                onClick={(e) => {
                  e.preventDefault();
                  // 값을 바꿀 맵 선언
                  const changedProductMap = productsMap;
                  // 만약 상품이 이 맵맵에 존재한다면
                  if (changedProductMap.has(item.productUuid!)) {
                    // 상품 수량을 1 늘린다
                    changedProductMap.set(item.productUuid!, {
                      product: item,
                      quantity:
                        changedProductMap.get(item.productUuid!)!.quantity + 1,
                    });
                    // 상품이 이 맵에 존재하지 않는다면
                  } else {
                    // 상품 수량을 1로 해 맵에 새로 추가한다
                    changedProductMap.set(item.productUuid!, {
                      product: item,
                      quantity: 1,
                    });
                  }

                  setProductsMap(changedProductMap);
                }}
              />
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
