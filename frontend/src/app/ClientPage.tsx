"use client";
import React, { useCallback, useEffect, useMemo, useState } from "react";
import ProductSummary from "./home/ProductSummary/ProductSummary";
import { components, paths } from "@/lib/backend/apiV1/schema";
import createClient from "openapi-fetch";
import UserDataInput from "./home/UserDataInput";
import SearchInput from "./home/ProductList/SearchInput";
import ProductList from "./home/ProductList/ProductList";
import { it } from "node:test";

type PaginationDataProductDto =
  components["schemas"]["PaginationDataProductDto"];

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
    new Map<
      string,
      { product: components["schemas"]["ProductDto"]; quantity: number }
    >()
  );

  const client = createClient<paths>({ baseUrl: "http://localhost:8080" });

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

  const decreaseQuantityCallBack = useCallback(async (key: string) => {}, []);
  useEffect(() => {
    let sum = 0;
    // 선택한 products들을 순회하면서 개수를 세서 맵에 저장장
    productsMap.forEach((item, key) => {
      sum += item.product.productPrice!;
    });
    setAmount(sum);
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
        <div className=" flex-grow h-[100%] bg-blue-300">
          <SearchInput onSearch={searchDataCallBack}></SearchInput>
          <ProductList
            products={products}
            productsMap={productsMap}
            setProductsMap={setProductsMap}
          ></ProductList>
        </div>
      </div>
      <div className="w-[35%] p-10 h-screen bg-[#DDDDDD]">
        <div className="h-[100%] ">
          <ProductSummary products={productsMap} onDecrease={decreaseQuantityCallBack} />
          <div>
            <UserDataInput
              addressStatus={[address, setAddress]}
              zipCodeStatus={[zipcode, setZipcode]}
              emailStatus={[email, setEmail]}
            ></UserDataInput>
          </div>
          <div>
            <span>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</span>
            <span>총 가격</span>
            <span>{amount}</span>
          </div>
          <input
            type="button"
            value={"결제하기"}
            onClick={(e) => {
              e.preventDefault();
              makeOrder();
            }}
          />
        </div>
      </div>
    </div>
  );
}
