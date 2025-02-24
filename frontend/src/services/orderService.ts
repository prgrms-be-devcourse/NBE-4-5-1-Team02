// /services/orderService.ts

export interface OrderItem {
  productId: string;
  name: string;
  quantity: number;
  price: number;
}

export interface Order {
  orderId: string;
  buyerEmail: string;
  address: string;
  zipcode: string;
  deliveryStatus: string;
  orderDate: string;
  items: OrderItem[];
  totalPrice: number;
}

export async function fetchOrderData(orderId: string) {
  const email = "email1@email.com";
  const res = await fetch(
    `http://localhost:8080/orders/${orderId}?email=${email}`
  );
  if (!res.ok) throw new Error("주문 정보를 불러오지 못했습니다.");
  const json = await res.json();
  const data = json.data;
  return {
    orderId: data.orderUuid,
    buyerEmail: data.user.email,
    address: data.deliveryAddress,
    zipcode: String(data.zipCode),
    deliveryStatus: data.deliveryStatus,
    orderDate: data.createDate,
    totalPrice: data.totalPrice,
  };
}

export async function fetchOrderProductsData(
  orderId: string
): Promise<OrderItem[]> {
  const email = "email1@email.com";
  const res = await fetch(
    `http://localhost:8080/orders/${orderId}/products?email=${email}`
  );
  if (!res.ok) throw new Error("주문 상품 목록을 불러오지 못했습니다.");
  const json = await res.json();

  const rawItems = json.data.data.map((p: any) => ({
    productId: p.productUuid,
    name: p.productName,
    quantity: p.quantity || 1,
    price: p.productPrice,
  }));
  const groupedItems = rawItems.reduce(
    (acc: Record<string, OrderItem>, item: OrderItem) => {
      if (acc[item.productId]) {
        acc[item.productId].quantity += item.quantity;
      } else {
        acc[item.productId] = { ...item };
      }
      return acc;
    },
    {}
  );
  return Object.values(groupedItems);
}

export async function fetchAllProducts() {
  const res = await fetch(
    `http://localhost:8080/products?keyword-type=title&keyword=`
  );
  if (!res.ok) throw new Error("전체 제품 목록을 불러오지 못했습니다.");
  const json = await res.json();
  return json.data.data;
}

export async function updateOrderData(order: any) {
  const res = await fetch(
    `http://localhost:8080/orders/${order.orderId}?email=${order.buyerEmail}`,
    {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        address: order.address,
        zipcode: parseInt(order.zipcode, 10),
        items: order.items.map((item: any) => ({
          productId: item.productId,
          quantity: item.quantity,
        })),
        buyer: {
          email: order.buyerEmail,
        },
        deliveryStatus: order.deliveryStatus,
      }),
    }
  );
  if (!res.ok) throw new Error("주문 수정 실패");
  return await res.json();
}
