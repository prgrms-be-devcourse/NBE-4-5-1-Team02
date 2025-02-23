"use client";

import { components } from "@/lib/backend/apiV1/schema";

interface ProductSummaryProps {
  onDecrease: (key: string) => void;
  products: Map<
    string,
    { product: components["schemas"]["ProductDto"]; quantity: number }
  >;
}

export default function ProductSummary({
  products,
  onDecrease,
}: ProductSummaryProps) {
  console.log("summary-products:", Array.from(products.entries()));
  return (
    <div>
      <h3>Summary</h3>
      <br />
      {Array.from(products.entries()).map((entry, index) => {
        const key = entry[0];
        const item = entry[1];
        const name = item.product.productName;
        const quantity = item.quantity;
        return (
          <div key={key}>
            <span>이름:{name}</span>
            <span>수량:{quantity}</span>
            <input type="button" value="증가"></input>
            <input
              type="button"
              value="감소"
              onClick={(e) => {
                e.preventDefault();
                onDecrease(key);
              }}
            ></input>
          </div>
        );
      })}
    </div>
  );
}
