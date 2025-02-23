"use client";

import { components } from "@/lib/backend/apiV1/schema";
import React from "react";

type PaginationDatatProductDto =
  components["schemas"]["PaginationDataProductDto"];

export default function ProductList({
  products,
  selectedProducts,
  setSelectedProducts,
}: {
  products: PaginationDatatProductDto;
  selectedProducts: PaginationDatatProductDto;
  setSelectedProducts: React.Dispatch<
    React.SetStateAction<PaginationDatatProductDto>
  >;
}) {
  console.log("selectedProducts", selectedProducts);
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
                  setSelectedProducts((prev) => ({
                    data: prev.data!.concat(item),
                    page: prev.page,
                    size: prev.size,
                    totalPages: Math.ceil((prev.data!.length + 1) / prev.size!),
                  }));
                }}
              />
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
