import createClient from "openapi-fetch";
import ClientPage from "./ClientPage";
import { components, paths } from "@/lib/backend/apiV1/schema";

export default async function Home({
  searchParams,
}: {
  searchParams: {
    size?: string;
    page?: string;
    "keyword-type"?: string;
    keyword?: string;
  };
}) {
  const client = createClient<paths>({
    baseUrl: "http://localhost:8080",
  });

  // searchParams를 Promise.resolve()로 처리
  const resolvedParams = await Promise.resolve(searchParams);
  
  const size = resolvedParams.size || "10";
  const page = resolvedParams.page || "0";
  const keywordType = resolvedParams["keyword-type"] || "";
  const keyword = resolvedParams.keyword || "";

  const response = await client.GET("/products", {
    params: {
      query: {
        size: Number(size),
        page: Number(page),
        "keyword-type": keywordType,
        keyword,
        pageable: {},
      },
    },
  });

  if (response.error) {
    console.log(response);
  }
  const responseBody = response.data!.data!;

  return (
    <ClientPage
      productList={responseBody}
      pageSize={Number(size)}
    />
  );
}
