// /components/OrderUpdate/AvailableProducts.tsx
import React from "react";
import { Button } from "@/components/ui/button";

export interface Product {
  productUuid: string;
  productName: string;
  category: string;
  productPrice: number;
}

interface AvailableProductsProps {
  products: Product[];
  onAddProduct: (product: Product) => void;
}

export default function AvailableProducts({
  products,
  onAddProduct,
}: AvailableProductsProps) {
  return (
    <div className="flex-1 border p-4">
      <h2 className="text-xl font-semibold mb-2">전체 제품</h2>
      {products.length > 0 ? (
        products.map((product) => (
          <div
            key={product.productUuid}
            className="flex justify-between items-center mb-2 p-2 bg-gray-50 rounded"
          >
            <div>
              <p className="font-medium">{product.productName}</p>
              <p className="text-sm text-gray-600">
                {product.category} - {product.productPrice}원
              </p>
            </div>
            <Button onClick={() => onAddProduct(product)}>추가</Button>
          </div>
        ))
      ) : (
        <p>제품이 없습니다.</p>
      )}
    </div>
  );
}
