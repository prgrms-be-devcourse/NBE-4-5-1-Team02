"use client";

import { Card, CardContent, CardTitle } from "@/components/ui/card";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/hooks/useCarosels";
import { components } from "@/lib/backend/apiV1/schema";
import Image from "next/image";
import { ProductWithAmountList } from "./ClientPage";

export default function SingleItemCarousel({
  imageList,
}: {
  imageList: ProductWithAmountList;
}) {
  return (
    <Carousel className="w-3/4">
      <CarouselContent>
        {imageList.map((data, idx) => (
          <CarouselItem
            key={idx}
            className="
                w-full
                h-fit
                flex 
                items-center 
                justify-center 
                bg-white 
                m-2 
                overflow-hidden
              "
          >
            <Card className="flex flex-col justify-center items-center ">
              <CardContent className="">
                <Image
                  src={data.product!.imageUrl!}
                  alt={data}
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
