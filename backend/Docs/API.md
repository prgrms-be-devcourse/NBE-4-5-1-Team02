## 0. 상품 조회 API

### 상품 목록 조회

- **URL:** `/products`
- **Method:** `POST`
- **Parameters**
    - **Query string**
    - | 키              | 예시 값      | 필수 여부   | 기본값       | 
      |----------------|-----------|---------|-----------|
      | `keyword-type` | `"title"` | `false` | `"title"` |
      | `keyword`      | `coffee1` | `false` | `""`      |

  <details>
  <summary>success</summary> 

  `GET /products`
    ```json
      {
        "message": "Success.",
        "data": {
            "data": [
                {
                    "productUuid": "p1",
                    "productName": "Coffee 1",
                    "category": "coffeeBean",
                    "productPrice": 1200,
                    "productDescription": "싱글 오리진 커피 원두",
                    "imageUrl": "/img/coffee1.png"
                },
                {
                    "productUuid": "p2",
                    "productName": "Coffee 2",
                    "category": "coffeeBean",
                    "productPrice": 1300,
                    "productDescription": "에스프레소용 커피 원두",
                    "imageUrl": "/img/coffee2.png"
                },
                {
                    "productUuid": "p3",
                    "productName": "Coffee 3",
                    "category": "coffeeBean",
                    "productPrice": 1250,
                    "productDescription": "다크 로스트 커피 원두",
                    "imageUrl": "/img/coffee3.png"
                },
                {
                    "productUuid": "p4",
                    "productName": "Coffee 4",
                    "category": "coffeeBean",
                    "productPrice": 1400,
                    "productDescription": "라이트 로스트 커피 원두",
                    "imageUrl": "/img/coffee4.png"
                },
                {
                    "productUuid": "p5",
                    "productName": "Coffee 5",
                    "category": "coffeeBean",
                    "productPrice": 1350,
                    "productDescription": "블렌드 커피 원두",
                    "imageUrl": "/img/coffee5.png"
                },
                {
                    "productUuid": "p6",
                    "productName": "Tea 1",
                    "category": "tea",
                    "productPrice": 800,
                    "productDescription": "녹차 티백",
                    "imageUrl": "/img/tea1.png"
                },
                {
                    "productUuid": "p7",
                    "productName": "Tea 2",
                    "category": "tea",
                    "productPrice": 900,
                    "productDescription": "홍차 티백",
                    "imageUrl": "/img/tea2.png"
                },
                {
                    "productUuid": "p8",
                    "productName": "Tea 3",
                    "category": "tea",
                    "productPrice": 850,
                    "productDescription": "우롱차 티백",
                    "imageUrl": "/img/tea3.png"
                },
                {
                    "productUuid": "p9",
                    "productName": "Tea 4",
                    "category": "tea",
                    "productPrice": 950,
                    "productDescription": "허브차 티백",
                    "imageUrl": "/img/tea4.png"
                },
                {
                    "productUuid": "p10",
                    "productName": "Tea 5",
                    "category": "tea",
                    "productPrice": 800,
                    "productDescription": "과일차 티백",
                    "imageUrl": "/img/tea5.png"
                }
            ],
            "page": 0,
            "size": 10,
            "totalPages": 3
        },
        "code": 200
    }
    ```  
  </details>

## 1. 사용자 API

### 1.1. 주문 생성

- **URL:** `/orders`
- **Method:** `POST`
- **설명:** 사용자가 주문 요청을 생성합니다.

- **Body Parameter**

    | 키                   | 타입        | 값 예시                 | 필수 여부  | 기본값 |
    |---------------------|-----------|----------------------|--------|-----|
    | `buyer.email`       | `string`  | `"user@example.com"` | `true` |     |
    | `address`           | `string`  | `"서울시 강남구 테헤란로 123"` | `true` |     |
    | `zipcode`           | `integer` | `12345`              | `true` |
    | `items[].productId` | `string`  | `p123`               | `true` |
    | `items[].quantity`  | `number`  | `2`                  | `true` |

- **Request Body 예시:**
    
    ```json
    {
      "buyer": {
        "email": "user@example.com"
      },
      "address": "서울시 강남구 테헤란로 123",
      "zipcode": "12345",
      "items": [
        {
          "productId": "p1",
          "quantity": 3
        }
      ]
    }
    
    ```
    
    - **유효성 검사:**
        - `buyer.email`: 이메일 형식, 비어있지 않음
        - `buyer.address`: 문자열, 비어있지 않음
        - `buyer.zipcode`: 숫자 혹은 숫자형 문자열, 비어있지 않음
        - `items`: 하나 이상의 상품이 포함되어야 하며, 각 `quantity`는 0보다 커야 함
- **Response 예시:**
    
    ```json
    {
    "message": "ok",
    "data": {
        "orderId": "order-f03a8a4d-b875-42ad-8e22-2770e84f4893",
        "buyer": {
            "email": "email1@email.com"
        },
        "products": [
            {
                "productUuid": "p1",
                "productName": "Coffee 1",
                "category": "coffeeBean",
                "productPrice": 1200,
                "productDescription": "싱글 오리진 커피 원두",
                "imageUrl": "/img/coffee1.png"
            },
            {
                "productUuid": "p1",
                "productName": "Coffee 1",
                "category": "coffeeBean",
                "productPrice": 1200,
                "productDescription": "싱글 오리진 커피 원두",
                "imageUrl": "/img/coffee1.png"
            },
            {
                "productUuid": "p1",
                "productName": "Coffee 1",
                "category": "coffeeBean",
                "productPrice": 1200,
                "productDescription": "싱글 오리진 커피 원두",
                "imageUrl": "/img/coffee1.png"
            }
        ],
        "createAt": "2025-02-25T10:06:04.3688085",
        "modifiedAt": "2025-02-25T10:06:04.3688085",
        "totalAmount": 3600,
        "address": "addr1",
        "zipcode": 1073,
        "deliveryStatus": "PENDING"
    },
    "code": 200
    }
    
    ```

  fail

    ```json
    {
    	"errcode": "ORDER-1",
    	"errmsg": "주문 생성에 실패했습니다. 필수 값을 체크해주세요."
    }
    ```

---

### 1.2. 주문 리스트 조회 (사용자)

- **URL:** `/orders`
- **Method:** `GET`
- **설명:** 사용자가 본인의 주문 내역을 조회합니다.
- **Query Parameter (옵션):**
    - `email` (혹은 인증 정보를 통해 결정)
- **Response 예시:**
- 성공

```json
{
  "message": "ok",
  "data": {
    "content": [
      {
        "orderId": "o9",
        "orderDate": "2025-02-25T09:18:48.052555",
        "totalPrice": 4300,
        "deliveryStatus": "DELIVERED",
        "buyerEmail": "bob@example.com",
        "address": "Busan, Nam-gu"
      },
      {
        "orderId": "o7",
        "orderDate": "2025-02-25T09:18:48.052555",
        "totalPrice": 3250,
        "deliveryStatus": "PENDING",
        "buyerEmail": "bob@example.com",
        "address": "Busan, Suyeong-gu"
      }
    ],
    "page": 0,
    "size": 2,
    "totalPages": 2
  },
  "code": 200
}
```

- 실패

```json
{
    "message": "getOrders.email: 올바른 이메일 형식이어야 합니다.",
    "data": null,
    "code": 400
}
```

---

### 1.3. 주문 상세 조회 (사용자)

- **URL:** `/orders/{orderId}`
- **Method:** `GET`
- **설명:** 사용자가 특정 주문의 상세 내역을 조회합니다.
- **Response 예시:**

    ```json
    {
      "orderId": "order789",
      "createdAt": "2025-02-18T14:00:00Z",
      "updatedAt": "2025-02-18T14:00:00Z",
      "image_url": "url/",
      "buyer": {
        "email": "user@example.com"
      },
      "address": "서울시 강남구 테헤란로 123",
      "zipcode": "12345",
      "items": [
        {
          "productId": "p123",
          "name": "상품명1",
          "quantity": 2,
          "price": 10000
        },
        {
          "productId": "p456",
          "name": "상품명2",
          "quantity": 1,
          "price": 20000
        }
      ],
      "totalPrice": 40000,
      "deliveryStatus": "배송중",
      "pagination": {
    	  "page": 1,
    	  "size": 10,
    	  "totalPages": 1
      }
    }
    ```

### 1.3.1 주문 내 상품 페이지네이션 조회 (사용자)

- **URL:** `/orders/{orderId}/products`
- **Method:** `GET`
- **설명:** 사용자가 주문 내 상품을 페이지네이션 해서 조회합니다.
  - **Query Parameter**
  
    | 키       | 타입        | 값 예시                    | 필수 여부   | 기본값  |
    |---------|-----------|-------------------------|---------|------|
    | `email` | `string`  | `"example@example.com"` | `true`  |      |
    | `page`  | `integer` | `0`                     | `false` | `0`  |
    | `size`  | `integer` | `10`                    | `false` | `10` | 
  - **Path Parameter**

    | 키         | 타입       | 값 예시                        | 필수 여부  | 기본값 |
    |-----------|----------|-----------------------------|--------|-----|
    | `orderId` | `string` | `"order-11111-22222-33333"` | `true` |     |

- **Response예시**

  ```json
  {
    "message": "Success.",
    "data": {
      "data": [
        {
          "productUuid": "product-11111-22222-33331",
          "productName": "product1",
          "category": "coffeeBean",
          "productPrice": 1000,
          "productDescription": "커피콩1",
          "imageUrl": "image_url1"
        },
        {
          "productUuid": "product-11111-22222-33331",
          "productName": "product1",
          "category": "coffeeBean",
          "productPrice": 1000,
          "productDescription": "커피콩1",
          "imageUrl": "image_url1"
        },
        {
          "productUuid": "product-11111-22222-33332",
          "productName": "product2",
          "category": "coffeeBean",
          "productPrice": 5000,
          "productDescription": "커피콩2",
          "imageUrl": "image_url2"
        }
      ],
      "page": 0,
      "size": 10,
      "totalPages": 1
    },
    "code": 200
  }
  ```

### 1.3.2 주문 내 모든 상품 조회 (사용자)

- **URL:** `/orders/{orderId}/products/all`
- **Method:** `GET`
- **설명:** 주문에 포함된 모든 상품의 정보와 수량이 전부 조회횝니다.
- **Parameters**
    - **Query Parameters**

        | 키       | 타입       | 값 예시                    | 필수 여부  | 기본값 |
        |---------|----------|-------------------------|--------|-----|
        | `email` | `string` | `"example@example.com"` | `true` |     |
    - **Path Parameters**
  
        | 키         | 타입       | 값 예시                        | 필수 여부  | 기본값 |
        |-----------|----------|-----------------------------|--------|-----|
        | `orderId` | `string` | `"order-11111-22222-33333"` | `true` |     |
    
- **Response 예시**
<details>
<summary>success</summary>

```json
{
  "message": "Success.",
  "data": [
    {
      "product": {
        "productUuid": "p12",
        "productName": "Snack 2",
        "category": "snack",
        "productPrice": 1600,
        "productDescription": "고소한 견과류 믹스",
        "imageUrl": "/img/snack2.png"
      },
      "amount": 1
    },
    {
      "product": {
        "productUuid": "p11",
        "productName": "Snack 1",
        "category": "snack",
        "productPrice": 1500,
        "productDescription": "바삭한 감자칩",
        "imageUrl": "/img/snack1.png"
      },
      "amount": 1
    }
  ],
  "code": 200
}
```
</details>

---

### 1.4. 주문 수정 (사용자)

- **URL:** `/orders/{orderId}?email=email@email.com`
- **Method:** `PUT`
- **설명:** 사용자가 주문 상세 페이지에서 주문을 수정합니다. 배송 상태가 **배송 중/배송 완료** 일 때 수정이 불가능합니다.
- **Query Parameter:**
    - `email`
    - ex) `/orders/{orderId}?email=email@email.com`
  
- **Query Parameter (옵션):**
    - `email` (혹은 인증 정보를 통해 결정)
    - ex) `/odrers/{orderId}?email=email@email.com`

      | 키 | 값 예시 | 필수 여부 |
      | --- | --- | --- |
      | email | user@example.com | true |

- 바디 파라미터

| 키                 | 값 예시             | 필수 여부 |
|-------------------|------------------|-------|
| buyer.email       | user@example.com | true  |
| deliveryStatus    | PENDING          | true  |
| address           | 서울시 강남구 새로운주소 456 | true  |
| zipcode           | 12345            | true  |
| items[].productId | p123             | true  |
| items[].quantity  | 2                | true  |

- **Request Body 예시:**
    - success

    ```json
    {
      "buyer": {
        "email": "alice@example.com"
      },
      "deliveryStatus": "PENDING",
      "address": "서울시 강남구 새로운주소 456",
      "zipcode": "67890",
      "items": [
        {
          "productId": "p1",
          "quantity": 3
        },
        {
          "productId": "p2",
          "quantity": 2
        }
      ]
    }
    ```
    - **유효성 검사:**
      - `buyer.email`: 이메일 형식, 비어있으면 안됨
      - `deliveryStatus`: 지정된 enum 값(PENDING, SHIPPED, DELIVERED, CANCELLED), null이 될 수 없음
      - `address`: 비어있지 않은 String
      - `zipcode`: 비어있지 않은 Integer
      - `items`: 하나 이상의 상품이 포함되어야 함, 각 항목의 `quantity`는 0보다 커야함
    
  - **주의사항:**
      - 수정 시 주문의 `updatedAt` 타임스탬프를 갱신합니다.
      - 주문 상품이 모두 삭제되면 주문이 취소(삭제)됩니다.

  - fail

  ```json
  {
    "message": "해당 주문을 찾을 수 없습니다.",
    "data": null,
    "code": 400
  }
  ```

- **Response 예시:** (성공 시 갱신된 주문 데이터 반환)

    ```json
    {
      "message": "ok",
      "data": {
         "orderId": "o1",
         "orderDate": "2025-02-25T10:30:12.667673",
         "totalPrice": 9950,
         "deliveryStatus": "CANCELLED",
         "buyerEmail": "alice@example.com",
         "address": "a1"
      },
       "code": 200
    }
    ```

---

### 1.5. 주문 취소 (사용자)

- **URL:** `/orders/{orderId}?email=email@email.com`
- **Method:** `DELETE`
- **설명:** 사용자가 상세보기 페이지나 리스트에서 주문을 취소합니다.
- **Query Parameter:**
  - `email`
  - ex) `/orders/{orderId}?email=email@email.com`

    | 키 | 값 예시 | 필수 여부 |
          | --- | --- | --- |
    | email | user@example.com | true |

- **Response 예시:**
    - success

    ```json
    {
      "message": "주문이 성공적으로 취소되었습니다.",
      "data": {
           "orderId": "o1",
           "message": "주문이 성공적으로 취소되었습니다."
    },
    "code": 200
    }
    ```

    - fail

    ```json
    {
      "message": "해당하는 주문을 찾을 수 없어 삭제에 실패했습니다.",
      "data": null,
      "code": 400
    }
    ```

---

## 2. 관리자 API

관리자 API는 모든 사용자의 주문에 접근할 수 있으며, 사용자 API와 거의 유사하지만 모든 주문 데이터를 조회, 수정, 삭제할 수 있는 권한을 가집니다.

> 주의: 관리자 API 엔드포인트는 별도의 인증 및 권한 검증 로직이 적용되어야 합니다.
>

- **URL:** `/admin/orders`

### 2.1. 주문 리스트 조회 (관리자)

- **URL:** `/admin/orders`
- **Method:** `GET`
- **설명:** 관리자가 모든 사용자의 주문 리스트를 조회합니다.
- **Response 예시:**
- success

```json
{
    "message": "ok",
    "data": {
        "content": [
            {
                "orderId": "o9",
                "orderDate": "2025-02-25T09:18:48.052555",
                "totalPrice": 4300,
                "deliveryStatus": "DELIVERED",
                "buyerEmail": "bob@example.com",
                "address": "Busan, Nam-gu",
                "items": [
                    {
                        "name": "Beverage 3",
                        "quantity": 1
                    },
                    {
                        "name": "Beverage 2",
                        "quantity": 1
                    }
                ]
            },
            {
                "orderId": "o8",
                "orderDate": "2025-02-25T09:18:48.052555",
                "totalPrice": 3650,
                "deliveryStatus": "SHIPPED",
                "buyerEmail": "bob@example.com",
                "address": "Busan, Dongnae-gu",
                "items": [
                    {
                        "name": "Snack 5",
                        "quantity": 1
                    },
                    {
                        "name": "Beverage 1",
                        "quantity": 1
                    }
                ]
            },
            {
                "orderId": "o7",
                "orderDate": "2025-02-25T09:18:48.052555",
                "totalPrice": 3250,
                "deliveryStatus": "PENDING",
                "buyerEmail": "bob@example.com",
                "address": "Busan, Suyeong-gu",
                "items": [
                    {
                        "name": "Snack 4",
                        "quantity": 1
                    },
                    {
                        "name": "Snack 3",
                        "quantity": 1
                    }
                ]
            },
          /*추가 조회 목록 생략*/
        ],
        "page": 1,
        "size": 10,
        "totalPages": 4
    },
    "code": 200
}
```

- fail
  ```json
  {
    "error": "NotFoundException",
    "orderId": "order789",
    "message": "주문 정보들을 찾을 수 없습니다."
  }
  ```

---

### 2.2.1 주문 상세 조회 (관리자)

- **URL:** `/admin/orders/{orderId}`
- **Method:** `GET`
- **설명:** 관리자가 특정 주문의 모든 상세 정보를 조회합니다.
- **Response 예시:**
    <details>
<summary>success</summary>
```json
`GET /admin/ord
{
"message": "Success.",
"data": {
"orderUuid": "order-11112-22222-33331",
"user": {
"id": "user-11112-22222-33331",
"email": "email0@email.com",
"createdDate": "2024-02-21T10:14:11.083775",
"modifiedDate": "2024-02-21T10:14:11.083775",
"orders": null
},
"createDate": "2024-02-21T10:14:11.084784",
"modifiedDate": "2024-02-21T10:14:11.084784",
"totalAmount": 9999,
"deliveryAddress": "addr0",
"zipCode": 123122,
"deliveryStatus": "PENDIN asdfasdf:G"
},
"code": 200
    
    
    </details>
- **설명:** 관리자가 특정 주문의 모든 상세 정보를 조회합니다. <br/> 이때 주문 내의 물품은 포함하지 않습니다
- **Parameters**
    - **PathParameter**

      | 키         | 타입       | 값 예시                      | 필수 여부  | 기본값 |
      |-----------|----------|---------------------------|--------|-----|
      | `orderId` | `string` | `order-11111-22222-33333` | `true` |     | 


- **Response 예시**
  <details>
  <summary>success</summary>

  `GET /admin/orders/o1`
  ```json
    {
      "message": "Success.",
      "data": {
          "orderUuid": "o1",
          "user": {
              "id": "u1",
              "email": "alice@example.com",
              "createdDate": "2025-02-25T09:41:22.407886",
              "modifiedDate": "2025-02-25T09:41:22.407886"
          },
          "createDate": "2025-02-25T09:41:22.407886",
          "modifiedDate": "2025-02-25T09:41:22.407886",
          "totalAmount": 15000,
          "deliveryAddress": "Seoul, Gangnam-gu",
          "zipCode": 101010,
          "deliveryStatus": "PENDING"
      },
      "code": 200
    }
  ```
  </details>
  <details>
  <summary>fail</summary>

  `GET /admin/orders/asdfasdf`
  ```json
  {
    "message": "orderId가 asdfasdf인 order를 찾을 수 없습니다.",
    "data": null,
    "code": 400
  }
  ```
  </details>

 --- 

### 2.2.2 주문에 포함된 모든 상품과 수량 조회 (관리자)

- **URL:** `/admin/orders/{orderId}/products/all`
- **Method:** `GET`
- **설명:** `{orderId}`주문에 포함된 모든 상품의 정보와 수량이 전부 조회횝니다.
- **Parameters**
  - **PathParameter**

    | 키         | 타입       | 값 예시                      | 필수 여부  | 기본값 |
    |-----------|----------|---------------------------|--------|-----| 
    | `orderId` | `string` | `order-11111-22222-33333` | `true` |     |

- **Response 예시**

    <details>
    <summary>success</summary>
    
    ```json
    {
        "message": "Success.",
        "data": [
            {
                "product": {
                    "productUuid": "p1",
                    "productName": "Coffee 1",
                    "category": "coffeeBean",
                    "productPrice": 1200,
                    "productDescription": "싱글 오리진 커피 원두",
                    "imageUrl": "/img/coffee1.png"
                },
                "amount": 1
            },
            {
                "product": {
                   "productUuid": "p2",
                   "productName": "Coffee 2",
                   "category": "coffeeBean",
                   "productPrice": 1300,
                   "productDescription": "에스프레소용 커피 원두",
                   "imageUrl": "/img/coffee2.png"
                },
                "amount": 1
           }
        ],
    "code": 200
    }
    ```
    
  </details>

  <details>
  <summary>fail</summary>
    
    `GET /admin/orders/asdfasdf/all`
    ```json
    {
      "message": "id가 asdfasdf인 order는 없습니다.",
      "data": null,
      "code": 400
    }
    ```

  </details>

---

### 2.3. 주문 수정 (관리자)

- **URL:** `/admin/orders/{orderId}`
- **Method:** `PUT`
- **설명:** 관리자가 주문 상세 페이지에서 주문 정보를 수정할 수 있습니다.
- 파라미터

| 키             | 값 예시             | 필수 여부 |  |
|---------------|------------------|-------|--|
| buyer.email   | user@example.com | true  |  |
| buyer.address | 서울시 강남구 테헤란로 123 | true  |  |
| buyer.zipcode | 12345            | true  |  |

- **Request Body 예시:**

  (사용자와 유사하며, 관리자는 주문 상태(배송 상태) 등 추가 필드를 수정할 수 있습니다.)

- **Response 예시:** (수정된 주문 데이터 반환)

    ```json
    {
      "buyer": {
    	  "email": "user@example.com"
      }
    }
    
    ```

    - success

    ```json
    {
      "orderId": "order789",
      "createdAt": "2025-02-18T14:00:00Z",
      "updatedAt": "2025-02-18T14:00:00Z",
      "buyer": {
        "email": "user@example.com"
      },
      "address": "서울시 강남구 테헤란로 123",
      "zipcode": "12345",
      "deliveryStatus": "대기중"
    }
    ```

    - fail

    ```json
    {
      "error": "ModificationNotAllowed",
      "message": "주문 일자로부터 오후 2시가 지난 주문은 수정할 수 없습니다."
    }
    ```

---

### 2.4. 주문 삭제 (관리자)

- **URL:** `/admin/orders/{orderId}`
- **Method:** `DELETE`
- **설명:** 관리자가 주문 리스트 혹은 상세 페이지에서 주문을 삭제합니다.
- **Response 예시:**
    - success

    ```json
    {
      "message": "주문이 성공적으로 삭제되었습니다.",
      "orderId": "order789"
    }
    ```

    - fail

    ```json
    {
      "message": "주문 삭제에 실패했습니다..",
      "orderId": "order789",
      "error": "NotFoundError"
    }
    ```

---

### 2.5 주문에 포함된 상품 리스트 조회 (관리자)

- **URL:** `/admin/orders/{orderId}/products`
- **Method:** `GET`
- **설명:** 주문에 포함된 모든 상품 리스트를 페이지네이션 해서 조회합니다.
- **Parameters**
    - **QueryParameter**

      | 키      | 타입        | 값 예시 | 필수 여부   | 기본값  |
      |--------|-----------|------|---------|------|
      | `size` | `integer` | `10` | `false` | `10` |
      | `page` | `integer` | `0`  | `false` | `0`  |
    - **PathParameter**

      | 키      | 타입        | 값 예시 | 필수 여부   | 기본값  |
      |--------|-----------|------|---------|------|
      | `size` | `integer` | `10` | `false` | `10` |
      | `page` | `integer` | `0`  | `false` | `0`  |



- **Response 예시:**
    <details>
    <summary>success</summary>
  
    ```json
    {
       "message": "Success.",
       "data": {
           "data": [
               {
                   "productUuid": "p1",
                   "productName": "Coffee 1",
                   "category": "coffeeBean",
                   "productPrice": 1200,
                   "productDescription": "싱글 오리진 커피 원두",
                   "imageUrl": "/img/coffee1.png"
               },
               {
                   "productUuid": "p2",
                   "productName": "Coffee 2",
                   "category": "coffeeBean",
                   "productPrice": 1300,
                   "productDescription": "에스프레소용 커피 원두",
                   "imageUrl": "/img/coffee2.png"
               }
           ],
           "page": 0,
           "size": 10,
           "totalPages": 1
       },
       "code": 200
    }
    ```
    </details>
    <details>
    <summary>fail</summary> 

    ```json
    {
        "message": "id가 asdf인 Order는 없습니다.",
        "data": null,
        "code": 400
    }
    ```
    </details> 

---

### 1. 유저 API

1.1 유저 생성

- **URL:**  `/users`
- **Method:** `POST`
- **설명:** 사용자가 주문 요청을 생성합니다. 생성하면서 유저의 정보를 저장

- 파라미터

| 키             | 타입       | 값 예시               | 필수 여부  |
|---------------|----------|--------------------|--------|
| `buyer.email` | `string` | `user@example.com` | `true` |
- **Request Body 예시:**

    ```json
    {
      "buyer":{
    	  "email": "user@example.com"
      }
    }
    
    ```

  - **유효성 검사:**
    - `buyer.email`: 이메일 형식, 비어있지 않음
- **Response 예시:**

    ```json
    {
        "message": "Success",
        "data": {
            "id": "user-43837299-71ef-4421-b2e1-03c19f43b75d",
            "email": "user@example.com",
            "createdDate": "2025-02-20T15:37:21.1689457",
            "modifiedDate": "2025-02-20T15:37:21.1689457"
        },
        "code": 200
    }
    
    ```
-fail
```json
{
    "message": "사용자 생성에 실패했습니다.",
    "data": null,
    "code": 400
}
```
