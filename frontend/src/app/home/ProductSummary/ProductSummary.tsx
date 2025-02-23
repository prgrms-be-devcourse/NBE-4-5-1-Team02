"use client";

import { Button } from "@/components/ui/button";
import { components } from "@/lib/backend/apiV1/schema";

interface ProductSummaryProps {
  onDecrease: (key: string) => void;
  onIncrease: (key: string) => void;
  products: Map<
    string,
    { product: components["schemas"]["ProductDto"]; quantity: number }
  >;
}

export default function ProductSummary({
  products,
  onDecrease,
  onIncrease,
}: ProductSummaryProps) {
  return (
    <div className="h-[30%] flex flex-col mb-3">
      <div className="w-[100%] h-fit pb-5 border-b-2 border-black">
        <h3 className="text-3xl font-bold">Summary</h3>
      </div>
      <div
        className="h-full mt-3 overflow-auto border-2"
        style={{ scrollbarGutter: "stable" }}
      >
        {Array.from(products.entries()).map((entry, index) => {
          const key = entry[0];
          const item = entry[1];
          const name = item.product.productName;
          const quantity = item.quantity;
          return (
            <div key={key} className="grid grid-cols-10 my-2">
              <span className="text-l font-bold mx-2 col-span-7">{name}</span>
              <Button className="px-2 py-0 h-full w-full" disabled={true}>
                {quantity}
              </Button>
              <Button
                className="h-full w-full px-1 py-0"
                type="button"
                onClick={(e) => {
                  e.preventDefault();
                  console.log("increase clicked");
                  onIncrease(key);
                }}
              >
                증가
              </Button>
              <Button
                className="h-full w-full py-0 px-1"
                type="button"
                onClick={(e) => {
                  e.preventDefault();
                  console.log("decrease clicked");
                  onDecrease(key);
                }}
              >
                감소
              </Button>
            </div>
          );
        })}
      </div>
    </div>
  );
}
