# 프로젝트 명세서

# 팀원
|김지우|김윤주|박종현|신우석|정회찬|
|:-:|:-:|:-:|:-:|:-:|
|[![김지우]()]()|[![김윤주]()]()|![박종현](https://github.com/user-attachments/assets/8c4160d4-f6a8-40ef-97eb-ccf112b9ad2d)|[![신우석]()]()|[![정회찬]()]()|
|FE,BE|FE,BE|FE,BE|FE,BE|FE,BE|
|[GitHub](https://github.com/omegafrog)|[GitHub](https://github.com/yunjuKimm)|[GitHub](https://github.com/joungGo)|[GitHub](https://github.com/shinwoos)|[GitHub](https://github.com/hoechanj)|

# 역할
| 이름   | FE                                    | BE                                   |
| ------ | ----------------------------------------- | ---------------------------------------- |
| 김지우 | 상품 조회 화면 개발                         | 관리자 주문 상세 조회 API 구현              |
| 신우석 | 사용자 주문 생성 화면 개발 및 주문 세부 조회 UI 개발 | 사용자 주문 생성 및 세부 조회 API 구현         |
| 김윤주 | 사용자 주문 수정/삭제 화면 개발              | 사용자 주문 수정 및 삭제 API 구현            |
| 정회찬 | 관리자 주문 수정/삭제 화면 개발              | 관리자 주문 수정 및 삭제 API 구현            |
| 박종현 | 리스트 조회 화면 (사용자, 관리자) UI 및 전체 UI 꾸미기         | 리스트 조회 화면 (사용자, 관리자) 개발 |



# 스키마
## ERD
![erd1](https://github.com/user-attachments/assets/7b6870ad-ec39-4440-9a9a-332d3ad7b58e)

## SQL
```sql
CREATE TABLE MEMBER (
	`USER_UUID`	VARCHAR(255)	NOT NULL unique primary key,
	`EMAIL`	VARCHAR(255) unique	NULL,
	`CREATE_DATE`	DATETIME	NULL,
	`MODIFIED_DATE`	DATETIME	NULL
);

CREATE TABLE ORDERS (
	`ORDER_UUID`	VARCHAR(255) unique	NOT NULL primary key,
	`USER_UUID`	VARCHAR(255)	NOT NULL,
	`CREATE_DATE`	DATETIME	NULL,
	`MODIFIED_DATE`	DATETIME	NULL,
	`TOTAL_AMOUNT`	INTEGER	NULL,
	`DELIVERY_ADDRESS`	VARCHAR(255)	NULL,
	`ZIP_CODE`	INTEGER	NULL,
	`DELIVERY_STATUS`	VARCHAR(255)	NULL,
     foreign key(`USER_UUID`)
     references MEMBER(`USER_UUID`)
);


CREATE TABLE PRODUCT (
	`PRODUCT_UUID`	VARCHAR(255) unique	NOT NULL,
	`PRODUCT_NAME`	VARCHAR(255)	NULL,
	`CATEGORY`	VARCHAR(255)	NULL,
	`PRODUCT_PRICE`	INTEGER	NULL,
	`PRODUCT_DESCRIPTION`	VARCHAR(255)	NULL,
	`IMAGE_URL`	VARCHAR(255)	NULL
);


CREATE TABLE PRODUCT_ORDER_RELATION (
	`PRODUCT_UUID`	VARCHAR(255) 	NOT NULL,
	`ORDER_UUID`	VARCHAR(255) 	NOT NULL,
    foreign key(PRODUCT_UUID)
    references PRODUCT(PRODUCT_UUID),
	foreign key(ORDER_UUID)
    references `ORDERS`(ORDER_UUID)
);
```


---

# API 문서
[API 문서](backend/Docs/API.md)

---

# Git-Flow 전략
main > develop > (local)feat

# 컨벤션

## 브랜치 명명법
> 브랜치 이름은 `<github id>/feat-<issue 번호>` 로 작성합니다. <br/>
> ex ) omegafrog/feat-18 : omegafrog의 18번 issue에 대한 브랜치



## Pull Request

제목 : `<issue-title>`
> PR 제목은 이슈 이름과 완전히 동일하게 작성합니다.

본문
```markdown
## 개요
<!---- 변경 사항 및 관련 이슈에 대해 간단하게 작성해주세요. 어떻게보다 무엇을 왜 수정했는지 설명해주세요. -->

<!---- Resolves: #(Isuue Number) -->

## PR 유형
어떤 변경 사항이 있나요?

- [ ] 새로운 기능 추가
- [ ] 버그 수정
- [ ] 코드에 영향을 주지 않는 변경사항(오타 수정, 탭 사이즈 변경, 변수명 변경)
- [ ] 코드 리팩토링(성능, 기능 메서드)
- [ ] 주석 추가 및 수정
- [ ] 문서 수정
- [ ] 테스트 추가, 테스트 리팩토링
- [ ] 빌드 부분 혹은 패키지 매니저 수정
- [ ] 파일 혹은 폴더명 수정
- [ ] 파일 혹은 폴더 삭제

## PR Checklist
PR이 다음 요구 사항을 충족하는지 확인하세요.

- [ ] 커밋 메시지 컨벤션에 맞게 작성했습니다.  Commit message convention 참고  (Ctrl + 클릭하세요.) 
- [ ] 변경 사항에 대한 테스트를 했습니다.(버그 수정/기능에 대한 테스트).
```

---

## Code Review
| 코드 리뷰 유형 | 설명 |
| --- | --- |
| `L0 - 리뷰불가` | 코드 리뷰가 어려운 경우 (설명이 부족하거나, 변경이 너무 커서 리뷰가 어려운 경우) |
| `L1 - 변경요청` | 기능 결함, 코드 품질 문제, 팀 컨벤션 위반 등의 이유로 반드시 수정이 필요한 경우 |
| `L2 - 변경협의` | 변경이 필요할 수도 있지만, 배포 후 후속 작업으로 진행 가능하다고 판단되는 경우 |
| `L3 - 중요질문` | 코드에 대한 중요한 질문 (명확한 리뷰 및 피드백 가능) |
| `L4 - 변경제안` | 더 나은 방법을 제안하는 경우 (강제사항 아님) |
| `L5 - 참고의견` | 참고만 하면 되는 의견 (수정 여부 자유) |

---

## Issue template
```markdown
[feat] : 

# 구현할 요구사항 
---
- [ ] 
- [ ] 

# 구현 방법 설명
---
```

---

## Commit Message
| Type 키워드 | 사용 시점 |
| --- | --- |
| feat | 새로운 기능 추가 |
| fix | 버그 수정 |
| docs | 문서 수정 |
| test | 테스트 코드 추가 및 수정 |
| refactor | 코드 리팩토링 (성능 개선 포함) |
| build | 빌드 파일 수정 |
| chore | 빌드 관련 작업, 패키지 매니저 수정 (예: `.gitignore` 수정) |
| rename | 파일/폴더명 변경만 수행한 경우 |
| remove | 파일/폴더 삭제 |
