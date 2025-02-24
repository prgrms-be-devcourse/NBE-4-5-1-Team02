"use client";

import { Card, CardContent, CardTitle } from "@/components/ui/card";
import { components } from "@/lib/backend/apiV1/schema";
import Image from "next/image";
import { ProductWithAmountList } from "./ClientPage";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel";

export default function SingleItemCarousel({
  imageList,
}: {
  imageList: ProductWithAmountList;
}) {
  return (
    <Carousel className="w-3/4">
      <CarouselContent className="">
        {imageList.map((data, idx) => (
          <CarouselItem key={idx}>
            <Card className="flex flex-col justify-center items-center ">
              <CardContent className="">
                <Image
                  src={`/${data.product?.productName?.replace(/\s+/g, '')}.png`}
                  alt={data.product?.productName || 'Product Image'}
                  width={500}
                  height={500}
                />
              </CardContent>
              <CardTitle className="mb-7">
                <span className="text-2xl font-semibold">
                  {data.product?.productName}
                </span>
              </CardTitle>
            </Card>
          </CarouselItem>
        ))}
      </CarouselContent>

      {/* 화살표(이전/다음) - 원하는 스타일/위치로 조정 */}
      <CarouselPrevious className="absolute left-2 top-1/2 -translate-y-1/2 bg-black/50 text-white w-10 h-10 flex items-center justify-center rounded-full" />
      <CarouselNext className="absolute right-2 top-1/2 -translate-y-1/2 bg-black/50 text-white w-10 h-10 flex items-center justify-center rounded-full" />
    </Carousel>
  );
}
