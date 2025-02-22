import createClient from "openapi-fetch";
import ClientPage from "./ClientPage";
import { paths } from "@/lib/backend/apiV1/schema";

export default async function Home() {
  const client = createClient<paths>({
    baseUrl: "http://localhost:8080",
  });
  const response = await client.GET("/products", {
    params: {
      query: {
        size: 10,
        page: 0,
        pageable: {},
      },
    },
  });

  if (response.error) {
    console.log(response);
  }
  const responseBody = response.data!.data!;

  return <ClientPage productList={responseBody}></ClientPage>;
}
