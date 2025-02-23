interface click {
  onClick: (key: string) => void;
}

export default function ProductSummary({
  products,
  clickAction,
}: {
  products: Map<string, { productName: string; quantity: number }>;
  clickAction: click;
}) {
  return (
    <div>
      <h3>Summary</h3>
      <br />
      {Array.from(products.entries()).map((entry, index) => {
        const key = entry[0];
        const name = entry[1].productName;
        const quantity = entry[1].quantity;
        return (
          <div key={key}>
            <span>이름:{name}</span>
            <span>수량:{quantity}</span>
            <input type="button" value="증가"></input>
            <input
              type="button"
              value="감소"
              onClick={(e) => {
                e.preventDefault();
                onclick(key);
              }}
            ></input>
          </div>
        );
      })}
    </div>
  );
}
