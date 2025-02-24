"use client";

import { Button } from "@/components/ui/button";
import { components } from "@/lib/backend/apiV1/schema";
import { ScrollArea } from "@/components/ui/scroll-area";

interface ProductSummaryProps {
  onDecrease: (key: string) => void;
  onIncrease: (key: string) => void;
  products: Map<
    string,
    { product: components["schemas"]["ProductDto"]; quantity: number }
  >;
}

/*
```
text-xl  = 1.25rem = 20px
text-2xl = 1.5rem  = 24px
```

주요 Tailwind 텍스트 크기 클래스 전체 스케일
- `text-xs`  : 0.75rem (12px)
- `text-sm`  : 0.875rem (14px)
- `text-base`: 1rem (16px)
- `text-lg`  : 1.125rem (18px)
- `text-xl`  : 1.25rem (20px)
- `text-2xl` : 1.5rem (24px)
- `text-3xl` : 1.875rem (30px)
- `text-4xl` : 2.25rem (36px)
- `text-5xl` : 3rem (48px)
*/

export default function ProductSummary({
  products,
  onDecrease,
  onIncrease,
}: ProductSummaryProps) {
  return (
    <div className="flex flex-col w-full">
      <div className="w-full pb-4 border-b-2 border-black sticky top-0 bg-card z-10">
        <h3 className="text-2xl font-bold">Summary</h3>
      </div>
      <div className="flex flex-col gap-6 mt-4">
        <ScrollArea>
          {Array.from(products).map(([key, item]) => {
            return (
              <div
                key={key}
                className="flex items-center justify-between gap-2 mb-4"
              >
                <span className="text-lg font-medium flex-grow">
                  {item.product.productName}
                </span>
                <div className="flex items-center gap-2">
                  <Button
                    variant="outline"
                    size="icon"
                    className="h-8 w-8"
                    onClick={(e) => {
                      e.preventDefault();
                      onIncrease(key);
                    }}
                  >
                    +
                  </Button>
                  <span className="w-8 text-center">{item.quantity}</span>
                  <Button
                    variant="outline"
                    size="icon"
                    className="h-8 w-8"
                    onClick={(e) => {
                      e.preventDefault();
                      onDecrease(key);
                    }}
                  >
                    -
                  </Button>
                </div>
              </div>
            );
          })}
        </ScrollArea>
      </div>
    </div>
  );
}
