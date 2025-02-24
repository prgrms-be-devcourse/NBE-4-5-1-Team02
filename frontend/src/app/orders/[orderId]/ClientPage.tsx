"use client";

import { useEffect, useState } from "react";
import SingleItemCarousel from "./CarouselDemo";
import { components, paths } from "@/lib/backend/apiV1/schema";
import createClient from "openapi-fetch";
import ProductSummary from "@/app/home/ProductSummary/ProductSummary";
import { productWithQuantity } from "@/app/ClientPage";
import UserData from "./UserData";
import { Button } from "@/components/ui/button";
import { useRouter } from "next/navigation";

const client = createClient<paths>({ baseUrl: "http://localhost:8080" });

export type OrderWithoutItemDto =
  components["schemas"]["OrderInfoWithoutItemDto"];
export type ProductWithAmountList =
  components["schemas"]["ProductWithAmount"][];
export default function ClientPage({
  searchParams,
}: {
  searchParams: {
    orderId: string;
    page: number;
    size: number;
  };
}) {
  const [order, setOrder] = useState<OrderWithoutItemDto>();
  const [products, setProducts] = useState<ProductWithAmountList>([]);
  const [productsMap, setProductsMap] = useState<
    Map<string, productWithQuantity>
  >(new Map());

  //   const decreaseQuantityCallBack = useCallback(
  //     (key: string) => {
  //       const changedProductsMap = new Map<string, productWithQuantity>(
  //         productsMap
  //       );

  //       const item = changedProductsMap.get(key);

  //       // 수량이 1개 이상이라면 개수 감소
  //       if (
  //         changedProductsMap.has(key) &&
  //         changedProductsMap.get(key)!.quantity! > 1
  //       ) {
  //         changedProductsMap.set(key, {
  //           product: item!.product,
  //           quantity: item!.quantity - 1,
  //         });
  //       } else {
  //         // 수량이 1개 미만이라면 삭제
  //         changedProductsMap.delete(key);
  //       }
  //       setProductsMap(changedProductsMap);
  //     },
  //     [productsMap]
  //   );

  //   const increaseQuantityCallBack = useCallback(
  //     (key: string) => {
  //       const changedProductsMap = new Map<string, productWithQuantity>(
  //         productsMap
  //       );

  //       const item = changedProductsMap.get(key);

  //       changedProductsMap.set(key, {
  //         product: item!.product,
  //         quantity: item!.quantity + 1,
  //       });
  //       setProductsMap(changedProductsMap);
  //     },
  //     [productsMap]
  //   );

  useEffect(() => {
    const a = async () => {
      const { orderId, page, size } = searchParams;
      const email = sessionStorage.getItem("userEmail")!;
      console.log(orderId, page, size, email);
      const orderResponse = await client.GET("/orders/{orderId}", {
        params: {
          query: {
            email: email,
          },
          path: {
            orderId: orderId,
          },
        },
      });

      if (orderResponse.error) {
        console.log(orderResponse);
      }
      console.log("orderResponse:", orderResponse);
      setOrder(orderResponse.data.data);

      const productResposne = await client.GET(
        "/orders/{orderId}/products/all",
        {
          params: {
            query: {
              email: email,
              pageable: {},
            },
            path: {
              orderId: orderId,
            },
          },
        }
      );

      if (productResposne.error) {
        console.log(productResposne);
      }
      const changedMap = new Map<string, productWithQuantity>();
      productResposne.data.data!.map((item) => {
        changedMap.set(item.product!.productUuid!, {
          product: item.product!,
          quantity: item.amount!,
        });
      });
      console.log("changedMap:", changedMap);
      setProductsMap(changedMap);
      setProducts(productResposne.data.data!);
    };
    a();
  }, []);

  const router = useRouter();

  return (
    <div className="min-h-screen w-full flex flex-row">
      {/* 왼쪽 상품 목록 섹션 */}
      <div className="w-3/4 min-h-screen p-8 flex justify-center items-center">
        <div className="flex justify-center">
          <SingleItemCarousel imageList={products!} />
        </div>
      </div>

      {/* 오른쪽 주문 요약 섹션 */}
      <div className="w-1/4 min-h-screen bg-[#DDDDDD] p-6 flex flex-col">
        <div className="h-2/5">
          <UserData order={order}></UserData>
        </div>
        <ProductSummary
          products={productsMap}
          onIncrease={() => {}}
          onDecrease={() => {}}
        />

        <div className="flex flex-col">
          <Button className="py-5 m-3">
            <span
              className="text-xl"
              onClick={(e) => {
                e.preventDefault();
                router.push(`/orders/${order?.orderUuid}/update`);
              }}
            >
              주문 수정
            </span>
          </Button>
          <div className="flex justify-between h-1/5">
            <Button className="py-5 m-3 w-1/2">
              <span
                className="text-xl"
                onClick={(e) => {
                  e.preventDefault();
                  router.push("/");
                }}
              >
                메인 화면으로
              </span>
            </Button>
            <Button className="py-5 m-3 w-1/2">
              <span
                className="text-xl"
                onClick={(e) => {
                  e.preventDefault();
                  router.back();
                }}
              >
                리스트 화면으로
              </span>
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
}
