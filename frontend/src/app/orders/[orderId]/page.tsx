import ClientPage from "./ClientPage";

export default async function Page({ searchParams, params }) {
  console.log("params:", await searchParams, await params);
  const { orderId } = await params;
  const { page = 0, size = 10 } = await searchParams;

  const parameters = {
    orderId,
    page,
    size,
  };
  return <ClientPage searchParams={parameters}></ClientPage>;
}
