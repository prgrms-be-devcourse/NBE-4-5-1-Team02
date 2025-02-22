import OrderClientPage from "./OrderClientPage";

export default async function Page({
  searchParams,
}: {
  searchParams: { page?: string };
}) {
  try {
    // 임시로 테스트용 이메일 사용
    const testEmail = "email1@email.com"; // 실제 구현시에는 사용자 인증 정보에서 가져와야 합니다
    const page = Number(searchParams.page) || 0;
    
    const response = await fetch(
      `http://localhost:8080/orders?email=${testEmail}&page=${page}&size=10`,
      {
        next: { revalidate: 0 },
        headers: {
          'Content-Type': 'application/json',
        }
      }
    );

    if (!response.ok) {
      const errorData = await response.json();
      console.error('API Error:', errorData);
      throw new Error(`API 오류: ${errorData.message || '주문 목록을 불러오는데 실패했습니다.'}`);
    }

    const rsData = await response.json();
    return <OrderClientPage rsData={rsData} />;

  } catch (error) {
    console.error('Fetch Error:', error);
    return (
      <div className="p-4 text-red-500">
        오류가 발생했습니다. 잠시 후 다시 시도해주세요.
        {process.env.NODE_ENV === 'development' && (
          <div className="mt-2 text-sm">
            {(error as Error).message}
          </div>
        )}
      </div>
    );
  }
}