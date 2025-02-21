## 0. 상품 조회 API

### 상품 목록 조회

- **URL:** `/products`
- **Method:** `POST`
- **Parameters**
  - **Query string**
    - `keyword-type` : string, default=`"title"`, not null
    - `keyword` : string, nullable <br/>

  <details>
  <summary>success</summary> 
  
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
                    "productUuid": "product-11111-22222-33332",
                    "productName": "product2",
                    "category": "coffeeBean",
                    "productPrice": 5000,
                    "productDescription": "커피콩2",
                    "imageUrl": "image_url2"
                },
                {
                    "productUuid": "product-11111-22222-33333",
                    "productName": "product3",
                    "category": "coffeeBean",
                    "productPrice": 1000,
                    "productDescription": "커피콩3",
                    "imageUrl": "image_url3"
                },
                {
                    "productUuid": "product-11111-22222-33334",
                    "productName": "product4",
                    "category": "coffeeBean",
                    "productPrice": 5000,
                    "productDescription": "커피콩4",
                    "imageUrl": "image_url4"
                },
                {
                    "productUuid": "product-11111-22222-33335",
                    "productName": "product5",
                    "category": "coffeeBean",
                    "productPrice": 1000,
                    "productDescription": "커피콩5",
                    "imageUrl": "image_url5"
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
      "errcode": "PRODUCT001",
      "errmsg": "상품 목록 조회를 불러오지 못했습니다."
    }
    ```
  </details>


## 1. 사용자 API

### 1.1. 주문 생성

- **URL:** `/orders`
- **Method:** `POST`
- **설명:** 사용자가 주문 요청을 생성합니다.

- 파라미터

| 키 | 값 예시 | 필수 여부 |
| --- | --- | --- |
| buyer.email | user@example.com | true |
| buyer.address | 서울시 강남구 테헤란로 123 | true |
| buyer.zipcode | 12345 | true |
| items[].productId | p123 | true |
| items[].quantity | 2 | true |
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
          "productId": "p123",
          "quantity": 2
        },
        {
          "productId": "p456",
          "quantity": 1
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
      "orderId": "order-111111-222222-333333",
      "createdAt": "2025-02-18T14:00:00Z",
      "updatedAt": "2025-02-18T14:00:00Z",
      "buyer": {
        "email": "user@example.com"
      },
      "address": "서울시 강남구 테헤란로 123",
      "zipcode": "12345",
      "items": [
        {
          "productId": "p123",
          "quantity": 2,
          "name": "상품명1",
          "price": 10000,
          "image_url": "url/"
        },
        {
          "productId": "p456",
          "quantity": 1,
          "name": "상품명2",
          "price": 20000,
          "image_url": "url/"
        }
      ],
      "totalPrice": 40000,
      "deliveryStatus": "대기중"
    }
    
    ```
    
    fail
    
    ```json
    {
    	"errcode": "PRODUCT001",
    	"errmsg": "상품 목록 조회를 불러오지 못했습니다."
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


    ```json
    {
      "content": [
        {
          "orderId": "order789",
          "orderDate": "2025-02-18T14:00:00Z",
          "totalPrice": 40000,
          "deliveryStatus":"배송 전",
          "buyerEmail": "user@example.com",
        },
        {
          "orderId": "order790",
          "orderDate": "2025-02-17T14:00:00Z",
          "totalPrice": 15000,
          "deliveryStatus":"배송 ",
          "buyerEmail": "user@example.com",
        }
      ],
      "page": 1,
      "size": 10,
      "totalPages": 1
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


---

### 1.4. 주문 수정 (사용자)

- **URL:** `/orders/{orderId}?email=email@email.com`
- **Method:** `PUT`
- **설명:** 사용자가 주문 상세 페이지에서 주문을 수정합니다. 배송 상태가 **배송 중/배송 완료** 일 때 수정이 불가능합니다.
- **Query Parameter (옵션):**
    - `email` (혹은 인증 정보를 통해 결정)
    - ex) `/odrers/{orderId}?email=email@email.com`

- 바디 파라미터

| 키 | 값 예시 | 필수 여부 |
| --- | --- | --- |
| buyer.address | user@example.com | true |
| buyer.address | 서울시 강남구 테헤란로 123 | true |
| buyer.zipcode | 12345 | true |
| items[].productId | p123 | true |
| items[].quantity | 2 | true |
- **Request Body 예시:**
    - success

    ```json
    {
      "address": "서울시 강남구 새로운주소 456",
      "zipcode": "67890",
      "items": [
        {
          "productId": "p123",
          "quantity": 3
        },
        {
          "productId": "p789",
          "quantity": 2
        }
      ]
    }
    ```

    - fail

    ```json
    {
      "error": "NotFoundException",
      "orderId": "order789",
      "message": "해당하는 주문을 찾을 수 없어 삭제에 실패했습니다."
    }
    ```

    - **주의사항:**
        - 수정 시 주문의 `updatedAt` 타임스탬프를 갱신합니다.
        - 주문 상품이 모두 삭제되면 주문이 취소(삭제)됩니다.
- **Response 예시:** (성공 시 갱신된 주문 데이터 반환)

    ```json
    {
      "orderId": "order789",
      "createdAt": "2025-02-18T14:00:00Z",
      "updatedAt": "2025-02-18T16:00:00Z",
      "image_url": "url/",
      "buyer": {
        "email": "user@example.com"
      },
      "address": "서울시 강남구 새로운주소 456",
      "zipcode": "67890",
      "items": [
        {
          "productId": "p123",
          "name": "상품명1",
          "quantity": 3,
          "price": 10000
        },
        {
          "productId": "p789",
          "name": "상품명3",
          "quantity": 2,
          "price": 15000
        }
      ],
      "totalPrice": 60000,
      "deliveryStatus": "배송전"
    }
    ```


---

### 1.5. 주문 취소 (사용자)

- **URL:** `/odrers/{orderId}?email=email@email.com`
- **Method:** `DELETE`
- **설명:** 사용자가 상세보기 페이지나 리스트에서 주문을 취소합니다.
- **Response 예시:**
    - success

    ```json
    {
      "message": "주문이 성공적으로 취소되었습니다.",
      "orderId": "order789"
    }
    ```

    - fail

    ```json
    {
      "error": "NotFoundException",
      "orderId": "order789",
      "message": "해당하는 주문을 찾을 수 없어 삭제에 실패했습니다."
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

    - fail

    ```json
    {
      "error": "NotFoundException",
      "orderId": "order789",
      "message": "주문 정보들을 찾을 수 없습니다."
    }
    ```


---

### 2.2. 주문 상세 조회 (관리자)

- **URL:** `/admin/orders/{orderId}`
- **Method:** `GET`
- **설명:** 관리자가 특정 주문의 모든 상세 정보를 조회합니다.
- **Response 예시:**
    - success

```json
{
  "message": "Success.",
  "data": {
    "orderUuid": "order-11111-22222-33331",
    "user": {
      "id": "user-11111-22222-33331",
      "email": "email1@email.com",
      "createdDate": "2025-02-21T10:14:11.083775",
      "modifiedDate": "2025-02-21T10:14:11.083775",
      "orders": null
    },
    "createDate": "2025-02-21T10:14:11.084784",
    "modifiedDate": "2025-02-21T10:14:11.084784",
    "totalAmount": 10000,
    "deliveryAddress": "addr1",
    "zipCode": 123123,
    "deliveryStatus": "PENDING"
  },
  "code": 200
}
```

- fail

```json
{
  "error": "NotFoundException",
  "orderId": "order789",
  "message": "해당하는 주문 정보를 가져올 수 없습니다."
}
```

---

### 2.3. 주문 수정 (관리자)

- **URL:** `/admin/orders/{orderId}`
- **Method:** `PUT`
- **설명:** 관리자가 주문 상세 페이지에서 주문 정보를 수정할 수 있습니다.
- 파라미터

| 키 | 값 예시 | 필수 여부 |  |
| --- | --- | --- | --- |
| buyer.email | user@example.com | true |  |
| buyer.address | 서울시 강남구 테헤란로 123 | true |  |
| buyer.zipcode | 12345 | true |  |
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

### 1. 유저 API

1.1 유저 생성

- **URL:**  `/users`
- **Method:** `POST`
- **설명:** 사용자가 주문 요청을 생성합니다. 생성하면서 유저의 정보를 저장

- 파라미터

| 키 | 값 예시 | 필수 여부 |
| --- | --- | --- |
| buyer.email | user@example.com | true |
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
        }
    }
    
    ```

### 2.5 주문에 포함된 상품 리스트 조회 (관리자)

- **URL:** `/admin/orders/{orderId}/products`
- **Method:** `GET`
- **설명:** 주문에 포함된 모든 상품 리스트를 페이지네이션 해서 조회합니다.
- **Parameters** 
  - **QueryParameter**
    - `size` : 한 페이지에 출력할 item 개수
    - `page` : 페이지 번호
  - **PathParameter** 
    - `orderId` : 주문 번호
- **Response 예시:**
  <details>
  <summary>success</summary>
  
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
  </details>
  <details>
  <summary>fail</summary> 

  ```json
  {
    "error": "NotFoundException",
    "orderId": "order789",
    "message": "해당 주문에 포함된 상품을 가져올 수 없습니다."
  }
  ```
  </details> 

