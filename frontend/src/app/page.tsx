import createClient from "openapi-fetch";
import ClientPage from "./ClientPage";
import { components, paths } from "@/lib/backend/apiV1/schema";

export default async function Home({
  searchParams,
}: {
  size: number;
  page: number;
  "keyword-type": string;
  keyword: string;
}) {
  const client = createClient<paths>({
    baseUrl: "http://localhost:8080",
  });

  const {
    size = 10,
    page = 0,
    "keyword-type": keywordType,
    keyword = "",
  } = await searchParams;

  const response = await client.GET("/products", {
    params: {
      query: {
        size,
        page,
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
    ></ClientPage>
  );
}
