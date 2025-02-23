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

type PaginationDataProductDto =
  components["schemas"]["PaginationDataProductDto"];

type productWithQuantity = {
  product: components["schemas"]["ProductDto"];
  quantity: number;
};

export default function ClientPage({
  productList,
}: {
  productList: components["schemas"]["PaginationDataProductDto"];
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
  >(
    new Map<string, productWithQuantity>(
      JSON.parse(typeof window !== 'undefined' ? sessionStorage.getItem("selectedItems") || "[]" : "[]")
    )
  );
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
    console.log("changed2");
    let sum = 0;
    // 선택한 products들을 순회하면서 개수를 세서 맵에 저장장
    productsMap.forEach((item, key) => {
      sum += item.product.productPrice! * item.quantity;
    });
    setAmount(sum);
    console.log("productsMap in useEffect:", productsMap);
    sessionStorage.setItem(
      "selectedItems",
      JSON.stringify(Array.from(productsMap.entries()))
    );
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

  return (
    <div className="w-screen h-screen flex">
      <div className="w-[75%] h-screen p-[4rem]">
        <div className="flex flex-col h-[100%] ">
          <div className="p-6 flex justify-between border-b-4">
            <h2 className="text-3xl font-bold">상품 목록</h2>
            <SearchInput onSearch={searchDataCallBack}></SearchInput>
          </div>

          <ProductList
            products={products}
            productsMap={productsMap}
            setProductsMap={setProductsMap}
          ></ProductList>
          <div className="w-full flex justify-end">
            <Button
              className="w-[20%] items-end"
              onClick={(e) => {
                e.preventDefault();
                router.push("/orders");
              }}
            >
              주문 목록
            </Button>
          </div>
        </div>
      </div>
      <div className="w-[35%] p-10 h-screen bg-[#DDDDDD]">
        <div className="h-[100%] ">
          <ProductSummary
            products={productsMap}
            onIncrease={increaseQuantityCallBack}
            onDecrease={decreaseQuantityCallBack}
          />
          <UserDataInput
            addressStatus={[address, setAddress]}
            zipCodeStatus={[zipcode, setZipcode]}
            emailStatus={[email, setEmail]}
          ></UserDataInput>
          <div className="h-[10%]">
            <span className="text-3xl">
              당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
            </span>
          </div>
          <div className="h-[7.5%] flex flex-column justify-between w-full">
            <div>
              <span className="text-3xl font-bold">총 가격</span>
            </div>
            <div>
              <span className="text-3xl font-bold">{amount}</span>
            </div>
          </div>
          <div className="h-[7.5%]">
            <Button
              type="button"
              className="w-full h-full"
              onClick={(e) => {
                e.preventDefault();
                makeOrder();
              }}
            >
              <span className="text-4xl font-bold">결제하기</span>
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}
