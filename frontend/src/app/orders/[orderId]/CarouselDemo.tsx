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
import { Badge } from "@/components/ui/badge";

export default function SingleItemCarousel({
  imageList,
}: {
  imageList: ProductWithAmountList;
}) {
  return (
    <Carousel className="w-full max-w-xl mx-auto">
      <CarouselContent>
        {imageList.map((data, idx) => (
          <CarouselItem key={idx}>
            <Card className="border-none shadow-none">
              <CardContent className="flex flex-col items-center p-4">
                <div className="relative w-[80%] aspect-square">
                  <Image
                    src={`/${data.product?.productName?.replace(/\s+/g, '')}.png`}
                    alt={data.product?.productName || 'Product Image'}
                    fill
                    className="object-contain"
                    priority
                  />
                </div>
                <CardTitle className="mt-3">
                  <Badge variant="secondary" className="text-lg">
                    {data.product?.productName}
                  </Badge>
                </CardTitle>
              </CardContent>
            </Card>
          </CarouselItem>
        ))}
      </CarouselContent>
      <CarouselPrevious />
      <CarouselNext />
    </Carousel>
  );
}
