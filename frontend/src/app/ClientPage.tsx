"use client";
import React, { useCallback, useEffect, useState } from "react";
import ProductSummary from "./home/ProductSummary/ProductSummary";
import { components, paths } from "@/lib/backend/apiV1/schema";
import createClient from "openapi-fetch";
import UserDataInput from "./home/UserDataInput";
import SearchInput from "./home/ProductList/SearchInput";
import ProductList from "./home/ProductList/ProductList";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";
import { ScrollArea } from "@/components/ui/scroll-area"
import { Separator } from "@/components/ui/separator"
import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "@/components/ui/card"
import { Label } from "@/components/ui/label"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"

type PaginationDataProductDto =
  components["schemas"]["PaginationDataProductDto"];

type productWithQuantity = {
  product: components["schemas"]["ProductDto"];
  quantity: number;
};

export default function ClientPage({
  productList,
  pageSize,
}: {
  productList: components["schemas"]["PaginationDataProductDto"];
  pageSize: number;
}) {
  const [keywordType, setKeywordType] = useState("title");
  const [products, setProducts] = useState<PaginationDataProductDto>(
    productList || []
  );

  const [amount, setAmount] = useState(0);
  const [email, setEmail] = useState<string>("");
  const [address, setAddress] = useState<string>("");
  const [zipcode, setZipcode] = useState<string>("");
  const [productsMap, setProductsMap] = useState<
    Map<
      string,
      { product: components["schemas"]["ProductDto"]; quantity: number }
    >
  >(new Map());

  useEffect(() => {
    const savedItems = sessionStorage.getItem("selectedItems");
    if (savedItems) {
      setProductsMap(new Map(JSON.parse(savedItems)));
    }
  }, []);

  const client = createClient<paths>({ baseUrl: "http://localhost:8080" });
  const router = useRouter();

  const searchDataCallBack = useCallback(async (keyword: string) => {
    const response = await client.GET("/products", {
      params: {
        query: {
          "keyword-type": keywordType,
          keyword: keyword,
          pageable: {},
        },
      },
    });

    if (response.error) {
      console.log(response);
      return;
    }
    const responseBody = response.data.data!;
    setProducts(responseBody);
  }, []);

  const decreaseQuantityCallBack = useCallback(
    (key: string) => {
      const changedProductsMap = new Map<
        string,
        { product: components["schemas"]["ProductDto"]; quantity: number }
      >(productsMap);

      const item = changedProductsMap.get(key);

      // 수량이 1개 이상이라면 개수 감소
      if (
        changedProductsMap.has(key) &&
        changedProductsMap.get(key)!.quantity! > 1
      ) {
        changedProductsMap.set(key, {
          product: item!.product,
          quantity: item!.quantity - 1,
        });
      } else {
        // 수량이 1개 미만이라면 삭제
        changedProductsMap.delete(key);
      }
      setProductsMap(changedProductsMap);
    },
    [productsMap]
  );

  const increaseQuantityCallBack = useCallback(
    (key: string) => {
      const changedProductsMap = new Map<
        string,
        { product: components["schemas"]["ProductDto"]; quantity: number }
      >(productsMap);

      const item = changedProductsMap.get(key);

      changedProductsMap.set(key, {
        product: item!.product,
        quantity: item!.quantity + 1,
      });
      setProductsMap(changedProductsMap);
    },
    [productsMap]
  );

  useEffect(() => {
    let sum = 0;
    productsMap.forEach((item, key) => {
      sum += item.product.productPrice! * item.quantity;
    });
    setAmount(sum);
    
    if (productsMap.size > 0) {
      sessionStorage.setItem(
        "selectedItems",
        JSON.stringify(Array.from(productsMap.entries()))
      );
    }
  }, [productsMap]);

  const makeOrder = async () => {
    const productsData: { productId: string; quantity: number }[] = [];
    productsMap.forEach((value, key) => {
      productsData.push({
        productId: key,
        quantity: value.quantity,
      });
    });

    const response = await client.POST("/orders", {
      body: {
        address: address,
        buyer: {
          email: email,
        },
        zipcode: Number(zipcode),
        items: productsData,
      },
    });

    if (response.error) {
      alert("결제 실패");
      return;
    }
    alert("주문 생성 성공");
  };

  useEffect(() => {
    // 컴포넌트 마운트 시 sessionStorage에 페이지 크기 저장
    sessionStorage.setItem('productListPageSize', pageSize.toString());
  }, [pageSize]);

  const handlePageSizeChange = (newSize: number) => {
    const currentParams = new URLSearchParams(window.location.search);
    currentParams.set('size', newSize.toString());
    currentParams.set('page', '0');
    window.location.href = `/?${currentParams.toString()}`;
  };

  return (
    <div className="min-h-screen w-full flex flex-row">
      {/* 왼쪽 상품 목록 섹션 */}
      <div className="w-3/4 min-h-screen p-8">
        <div className="flex flex-col h-full">
          <div className="p-6 flex items-center gap-4 border-b-4">
            <h2 className="text-2xl font-bold whitespace-nowrap">상품 목록</h2>
            <div className="flex items-center gap-2 whitespace-nowrap">
              <Label htmlFor="pageSize">페이지당 항목 수</Label>
              <Select value={pageSize.toString()} onValueChange={(value) => handlePageSizeChange(Number(value))}>
                <SelectTrigger className="w-[120px]">
                  <SelectValue placeholder="항목 수 선택" />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="1">1개</SelectItem>
                  <SelectItem value="5">5개</SelectItem>
                  <SelectItem value="10">10개</SelectItem>
                  <SelectItem value="15">15개</SelectItem>
                  <SelectItem value="20">20개</SelectItem>
                </SelectContent>
              </Select>
            </div>
            <Button
              className="whitespace-nowrap"
              onClick={(e) => {
                e.preventDefault();
                router.push("/orders");
              }}
            >
              주문 목록
            </Button>
            <div className="flex-grow flex justify-end">
              <SearchInput onSearch={searchDataCallBack} />
            </div>
          </div>

          <ProductList
            products={products}
            productsMap={productsMap}
            setProductsMap={setProductsMap}
          ></ProductList>
        </div>
      </div>

      {/* 오른쪽 주문 요약 섹션 */}
      <div className="w-1/4 min-h-screen bg-card p-6 overflow-hidden">
        <Card className="h-full flex flex-col">
          <CardHeader>
            <CardTitle>주문 요약</CardTitle>
          </CardHeader>
          <CardContent className="flex-1 overflow-hidden flex flex-col">
            <div className="flex-1 overflow-hidden">
              <ScrollArea className="h-[calc(100vh-300px)]">
                <ProductSummary
                  products={productsMap}
                  onIncrease={increaseQuantityCallBack}
                  onDecrease={decreaseQuantityCallBack}
                />
              </ScrollArea>
            </div>
            <Separator className="my-4" />
            <UserDataInput
              addressStatus={[address, setAddress]}
              zipCodeStatus={[zipcode, setZipcode]}
              emailStatus={[email, setEmail]}
            />
          </CardContent>
          <CardFooter className="flex flex-col gap-4">
            <p className="text-sm text-muted-foreground">
              당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
            </p>
            <div className="flex justify-between w-full">
              <span className="text-xl font-bold">총 가격</span>
              <span className="text-xl font-bold">{amount.toLocaleString()}원</span>
            </div>
            <Button className="w-full" size="lg" onClick={makeOrder}>
              주문하기
            </Button>
          </CardFooter>
        </Card>
      </div>
    </div>
  );
}
