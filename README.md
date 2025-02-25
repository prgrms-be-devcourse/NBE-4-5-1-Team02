# 프로젝트 Intro.
## 팀원
|김지우|김윤주|박종현|신우석|정회찬|
|:-:|:-:|:-:|:-:|:-:|
|<img src="https://github.com/user-attachments/assets/ede38190-2308-44b6-b8e6-b269142226e4" width="150">|<img src="https://github.com/user-attachments/assets/5abd0806-bcd2-4512-b11d-09474e51ee5c" width="150">|<img src="https://github.com/user-attachments/assets/e118499a-02b9-495e-a90c-e04bbd2a9afb" width="150">|<img src="https://github.com/user-attachments/assets/65fc1b4a-615b-432a-84bf-057f3566a4d4" width="150">|<img src="https://github.com/user-attachments/assets/d3135603-64d6-47c2-8758-6c5b87089584" width="150">|
|FE,BE|FE,BE|FE,BE|FE,BE|BE|
|[GitHub](https://github.com/omegafrog)|[GitHub](https://github.com/yunjuKimm)|[GitHub](https://github.com/joungGo)|[GitHub](https://github.com/shinwoos)|[GitHub](https://github.com/hoechanj)|
<br>

## 역할
| 이름   | FE                                    | BE                                   |
| ------ | ----------------------------------------- | ---------------------------------------- |
| 김지우 | 메인 페이지 및 상품 상세 페이지 개발             | 상품 리스트 조회 및 관리자 주문 상세 조회 API 구현              |
| 신우석 | 관리자 주문 수정 페이지 개발 | 사용자 주문 생성 및 세부 조회 API 구현 |
| 김윤주 | 사용자 주문 수정/삭제 페이지 개발              | 사용자 주문 수정 및 삭제 API 구현 |
| 정회찬 |             | 관리자 주문 수정 및 삭제 API 구현  |
| 박종현 | 리스트 조회 페이지 (사용자, 관리자) 및 사용자 주문 상세 페이지, 전체 UI 꾸미기         | 리스트 조회 (사용자, 관리자) API 구현 |

<br>

## 기술스택
#### 언어
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white) ![TypeScript](https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white)
#### 웹 프레임워크
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Next JS](https://img.shields.io/badge/Next-black?style=for-the-badge&logo=next.js&logoColor=white) ![React](https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB) ![TailwindCSS](https://img.shields.io/badge/tailwindcss-%2338B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)
#### 데이터
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)
#### 협업
![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white) 

<br>

## 스키마
<details>
  <summary>ERD</summary>
  <img src="https://github.com/user-attachments/assets/7b6870ad-ec39-4440-9a9a-332d3ad7b58e" width="900">
</details>

<details>
  <summary>SQL</summary>
  <pre><code class="language-sql">
CREATE TABLE MEMBER (
	`USER_UUID`	VARCHAR(255)	NOT NULL unique primary key,
	`EMAIL`	VARCHAR(255) unique	NULL,
	`CREATE_DATE`	DATETIME	NULL,
	`MODIFIED_DATE`	DATETIME	NULL
);
	  
<br>
	  
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

<br>

CREATE TABLE PRODUCT (
	`PRODUCT_UUID`	VARCHAR(255) unique	NOT NULL,
	`PRODUCT_NAME`	VARCHAR(255)	NULL,
	`CATEGORY`	VARCHAR(255)	NULL,
	`PRODUCT_PRICE`	INTEGER	NULL,
	`PRODUCT_DESCRIPTION`	VARCHAR(255)	NULL,
	`IMAGE_URL`	VARCHAR(255)	NULL
);

<br>

CREATE TABLE PRODUCT_ORDER_RELATION (
	`PRODUCT_UUID`	VARCHAR(255) 	NOT NULL,
	`ORDER_UUID`	VARCHAR(255) 	NOT NULL,
    foreign key(PRODUCT_UUID)
    references PRODUCT(PRODUCT_UUID),
	foreign key(ORDER_UUID)
    references `ORDERS`(ORDER_UUID)
);
  </code></pre>
</details>


<br>

## API 문서
[API 문서](backend/Docs/API.md)

<br>

## 화면 설계서
<details>
	<summary>Flow Chart</summary>
	<img src="https://github.com/user-attachments/assets/61c69cf2-b2d2-4d65-bbec-e36d877d9f71" width="600">
	<img src="https://github.com/user-attachments/assets/2a2056cf-99a2-4d45-9620-74d5a2fd0ced" width="600">
</details>


<details>
  <summary>스토리보드</summary>
  
  <img src="https://github.com/user-attachments/assets/b07c5b23-baa8-4bf5-b731-26ba5493ea8f" width="330">
  <img src="https://github.com/user-attachments/assets/797872e8-6991-4aac-a744-a7f7d440b11f" width="330">
  <img src="https://github.com/user-attachments/assets/484bf6eb-0d53-4e90-a03e-7b33033b54e4" width="330">
  <img src="https://github.com/user-attachments/assets/549a0f1c-6b2d-40f3-ad75-11f8fb500364" width="330">
  <img src="https://github.com/user-attachments/assets/c2dfdd2c-8914-4f44-b744-d1041f558c34" width="330">
  <img src="https://github.com/user-attachments/assets/69d46e7f-c869-4fa0-8f5d-fe38a44e52ac" width="330">

</details>


## Git-Flow 전략
main > develop > (local)feat

<br>

## Code 컨벤션
<details>
  <summary>브랜치 명명법</summary>
> 브랜치 이름은 `<github id>/feat-<issue 번호>` 로 작성합니다. <br/>
> ex ) omegafrog/feat-18 : omegafrog의 18번 issue에 대한 브랜치
</details>

<details>
  <summary>Pull Request</summary>

  #### PR 유형
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

  #### PR Checklist
  PR이 다음 요구 사항을 충족하는지 확인하세요.

  - [ ] 커밋 메시지 컨벤션에 맞게 작성했습니다.  Commit message convention 참고  (Ctrl + 클릭하세요.) 
  - [ ] 변경 사항에 대한 테스트를 했습니다.(버그 수정/기능에 대한 테스트).
</details>

<details>
  <summary>Code Review</summary>

  #### Code Review
  | 코드 리뷰 유형 | 설명 |
  | --- | --- |
  | `L0 - 리뷰불가` | 코드 리뷰가 어려운 경우 (설명이 부족하거나, 변경이 너무 커서 리뷰가 어려운 경우) |
  | `L1 - 변경요청` | 기능 결함, 코드 품질 문제, 팀 컨벤션 위반 등의 이유로 반드시 수정이 필요한 경우 |
  | `L2 - 변경협의` | 변경이 필요할 수도 있지만, 배포 후 후속 작업으로 진행 가능하다고 판단되는 경우 |
  | `L3 - 중요질문` | 코드에 대한 중요한 질문 (명확한 리뷰 및 피드백 가능) |
  | `L4 - 변경제안` | 더 나은 방법을 제안하는 경우 (강제사항 아님) |
  | `L5 - 참고의견` | 참고만 하면 되는 의견 (수정 여부 자유) |

</details>

<details>
  <summary>Issue Template</summary>

  ### Issue template
  ```markdown
  [feat] : 

  # 구현할 요구사항 
  ---
  - [ ] 
  - [ ] 

  # 구현 방법 설명
  ```
</details>

<details>
  <summary>Commit Message</summary>

  ### Commit Message
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

</details>
<details>
  <summary>Commit Message</summary>

  ### Commit Message
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

</details>

