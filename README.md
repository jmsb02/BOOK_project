# **자신이 원하는 책을 찾고 주문 할 수 있는 온라인 서점 플랫폼 - "북, 그럼"**
![image](https://github.com/user-attachments/assets/d917dcb9-208f-4122-8ee8-4ba291a14257)

전체 프로젝트 내용 관련해서는 velog에 정리해두었습니다. 
- https://velog.io/@jaemm/%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%AA%85%EC%84%B8%EC%84%9C-%EC%9E%91%EC%84%B1

# 프로젝트 소개
## 1. 프로젝트 개요
- 프로젝트 명칭 : 북, 그럼

- 프로젝트 소개 : 자신이 원하는 책을 찾고 주문 할 수 있는 온라인 서점 프로젝트

- 개발 인원 : 1명

- 개발 기간 : 2024.03 ~ 05

- 주요 기능
회원 가입/로그인 기능 (@Validation 적용)
외부 도서 정보 연동/ 장바구니에 담기/ 찜하기 기능
상품 검색 기능 (네이버 검색 api 연동)
상품 검색 페이징
상품 주문/찜 목록 추가/상품 취소/주문 취소 기능
마이 페이지

- 백엔드 개발 언어 : JAVA 17

- 백엔드 개발 환경 : SpringBoot 3.2.2, gradle, Spring data jpa, Spring security, lombok, thymeleaf

- 프론트 개발 환경 : html, css, js, bootstrap

- 데이터 베이스 : h2

- 형상 관리, 이슈 관리 : GitHub

## 2. 요구사항 분석
### 1. 회원 가입 페이지
유효성 검사
이름 - 공백 없는 문자 값만 작성하도록 함
이메일 공백 없는 "이메일" 형식의 값만 받도록 함.
비밀번호 - 공백이 없고 최소 8글자 이상이며 영문 대 소문, 숫자, 특수문자를 사용하도록 함.
주소 - 공백 없는 문자 값만 받도록 함.
상세 주소 - (선택)
중복 확인
같은 아이디 입력시 "이미 존재하는 회원입니다." 메세지를 보여줌

### 2. 로그인 페이지
이메일 - 공백이 없고 이메일 형식의 값만 받도록 함.
패스워드 - 공백이 없고 최소 8글자 이상이며 영문 대 소문, 숫자, 특수문자를 사용하도록 함.
아이디가 일치하지 않을 시 - "로그인 실패 : 존재하지 않는 이메일입니다." 메세지를 보여주도록 함.
비밀번호가 일치하지 않을 시 - "비밀번호가 일치하지 않습니다. 다시 한 번 확인해주세요." 메세지를 보여주도록 함.

### 3. 홈 화면
그리드 형식으로 책들을 보여주고, 간단하게 프로젝트 명칭과 관련해서 소개글을 작성함.
검색창에 검색하고자 하는 책을 입력하면 그 책과 관련된 검색 목록 화면으로 이동하도록 함.
검색창에 아무 값도 입력 안하고 버튼 클릭 시 "다시 입력해주세요"라는 문구를 보여주도록 함.
상단바, 하단바를 통해 다른 페이지로 이동하도록 함.
View 버튼을 누르면 네이버 쇼핑 페이지로 이동시켜 책과 관련된 정보를 알려주도록 함.
Basket 버튼을 누르면 해당 상품을 장바구니에 저장하도록 함.
찜 하기 버튼을 누르면 해당 상품을 찜 목록에 저장하도록 함.
### 4. 검색 목록
페이징 처리를 통해 한 페이지당 10개 책들을 보여주도록 함.
책의 isbn, 이미지, 제목, 저자/출판사, 출판일, discount, 기타 정보를 보여주도록 함.
값 입력 안 하고 버튼 클릭시 검색창에 빨간 색 표시를 해주는 동시에 "다시 입력해주세요" 문구 뛰우도록 함.
"장바구니에 추가" 버튼 클릭 시 장바구니에 상품을 저장하도록 함.
"상품 주문하기" 버튼 클릭 시 상품 주문하기 페이지에 상품을 넘기도록 함.
"찜 목록에 추가" 버튼 클릭 시 찜 목로겡 해당 상품을 저장하도록 함.

### 5. 장바구니
해당 상품의 정보를 보여주도록 함.
"상품 주문하기" 버튼 클릭 시 상품 주문하기 페이지에 상품을 넘기도록 함.
"찜 목록에 추가" 버튼 클릭 시 찜 목로겡 해당 상품을 저장하도록 함.
"상품 취소하기" 버튼 클릭 시 해당 상품을 장바구니에서 삭제함.

### 6. 상품 주문
-넘어온 상품에 대한 정보를 표시하고, 책들의 가격인 totalPrice도 보여주도록 함.
"상품 취소하기" 버튼 클릭 시 해당 상품을 상품 주문 페이지에서 삭제하도록 함.
card와 관련된 정보를 "필수로" 작성하도록 하여 pay now 버튼 클릭시 해당 상품 정보와 card 정보를 같이 넘겨주도록 함.

### 7. 찜 목록
해당 상품의 정보를 보여주도록 함.
"상품 주문하기" 버튼 클릭 시 상품 주문하기 페이지에 상품을 넘기도록 함.
"찜 상품 삭제" 버튼 클릭 시 해당 상품을 장바구니에서 삭제함.

### 8. 주문 내역
"상품 주문하기" 화면에서 넘어온 해당 상품과 card 정보를 보여주도록 함.
"상품 취소하기" 버튼 클릭시 주문 내역에서 해당 상품을 삭제하도록 함.

### 9. 마이페이지
나의 프로필 사진을 보여주도록 함.
로그인 한 아이디와 비밀번호 등 정보를 표시하도록 함.
비밀번호를 해쉬화하여 가린 상태로 보여주도록 함.
"돌아가기" 버튼 클릭 시 홈 화면으로 돌아가도록 함.
"로그 아웃" 버튼 클릭 시 로그 아웃하고 로그인 페이지로 돌아가게 함.

## 3. DB 테이블 설계
![image](https://github.com/user-attachments/assets/6dea794d-59d0-40a9-bbed-05229fed379b)
네이버 검색 API를 통해 넘어오는 JSON 값들을 매핑하기 위해 BookItem 객체와 이에 대한 정보들을 담는 BookVO 객체만 매핑하였습니다.
복잡성 감소, 유연성 증가를 위해 나머지 객체들이 연관관계가 없도록 설계하였습니다.

## 4. API 설계
### 회원 API, 로그인 API
![image](https://github.com/user-attachments/assets/bef2795b-aba3-4a37-98c0-6635c558e65f)
### 홈 API, 검색 목록 API 
![image](https://github.com/user-attachments/assets/aed0a45b-c236-4e69-9286-702c1b5590fc)
### 장바구니 API, 주문하기 API
![image](https://github.com/user-attachments/assets/7054fa46-7798-4787-93d0-b1dd98180c0e)
### 주문 목록 API, 찜 API
![image](https://github.com/user-attachments/assets/41441abf-63e7-42de-b59f-96a580be4398)

--------------
# 기능 소개 
## 회원 가입 화면 + Validation 적용
![image](https://github.com/user-attachments/assets/b2f24e4e-305e-4483-8d20-4a834f45844c)


## 로그인 화면
![image](https://github.com/user-attachments/assets/2bace273-3949-4c40-8f23-5870373bfffb)
- Spring Security 적용
- 밑에 Sign up 버튼을 통해 회원가입 폼으로 이동

## 로그인 성공 -> 홈 화면으로 이동 
![image](https://github.com/user-attachments/assets/295bc33d-e81b-439e-ab4e-20c7d4faaf79)

## 로그인 실패 -> Validation 적용
![image](https://github.com/user-attachments/assets/38766457-6bce-406d-a4a5-1beb386a84d1)

## 홈 화면
![image](https://github.com/user-attachments/assets/d917dcb9-208f-4122-8ee8-4ba291a14257)
![image](https://github.com/user-attachments/assets/85319925-2ca1-49f2-8b8a-618eff15c689)

## 홈 화면 버튼 (View, Basket, 찜하기 버튼)
### "View" 버튼 클릭 시 외부 링크로 이동
![image](https://github.com/user-attachments/assets/757a8682-7746-4298-afdc-3a0fd6b1f002)
### Basket 버튼 
![image](https://github.com/user-attachments/assets/a7f4902d-d152-4e8b-a77b-f16f8be82d75)
### 찜 하기 버튼
![image](https://github.com/user-attachments/assets/6f78004e-b63f-4f9d-b2f2-a32cb9dbd2a0)
- 각 예외시 GlobalExceptionHandler로 묶어서 처리
- GlobalExceptionHandler로 관련 : (https://velog.io/@jaemm/Book-%EA%B0%9C%EC%9D%B8%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-4.-ControllerAdvice-ExceptionHandler%EB%A5%BC-%ED%86%B5%ED%95%B4-%EC%98%88%EC%99%B8-%EC%B2%98%EB%A6%AC)

## 홈 화면 -> 검색 목록 (네이버 api로 구현한 검색 목록 및 페이징)
![image](https://github.com/user-attachments/assets/b0588bec-f936-44db-a120-c3ec08caa434)
- 홈 화면에서 검색바에 내용을 입력하면 그 내용에 해당하는 책 목록을 보여주도록 한다.
### 검색 목록
![image](https://github.com/user-attachments/assets/8f7aec98-9448-4b74-822e-282691931dcb)
- isbn, 이미지, 제목, 저자/출판사, 출판일, discount,기타(장바구니 추가, 상품 주문하기, 찜 목록에 추가) 순

### 페이징
![image](https://github.com/user-attachments/assets/3cbead4d-620f-4f57-aa74-75384ba18705)
- 2페이지 클릭시 잘 넘어감을 확인할 수 있음

## 검색 목록 -> 장바구니 추가, 상품 주문, 찜 목록에 추가
### 현재 검색 목록 화면
![image](https://github.com/user-attachments/assets/142719a6-0d25-4278-8ee0-860438a0fe36)
### 장바구니 추가
![image](https://github.com/user-attachments/assets/b9b0bf8c-e887-42c4-b400-5963d4f81d2f)
### 상품 주문 
![image](https://github.com/user-attachments/assets/925350ef-e7cf-4bde-9457-5229282dee2e)
- 상품 주문 버튼 클릭
### 찜 목록 추가 
![image](https://github.com/user-attachments/assets/43ebc3ac-844d-4b88-818f-974c3a6cc72e)
- 찜 목록 버튼 클릭

## 장바구니 구현 (상품 주문, 찜 목록 추가, 상품 취소)
### 장바구니 화면
![image](https://github.com/user-attachments/assets/0d006a8e-e154-463e-8364-a45972260eb8)
### 상품 주문하기
![image](https://github.com/user-attachments/assets/acc9a6db-df6a-4d7c-a47c-64e7147ecb64)
### 찜 목록
![image](https://github.com/user-attachments/assets/9c729e58-8910-4a16-8e53-862eb5011061)
### 상품 삭제
#### 현 장바구니 상황
![image](https://github.com/user-attachments/assets/ca036402-eabd-40b2-b587-f3c4da6af649)
#### 장바구니 삭제 버튼
![image](https://github.com/user-attachments/assets/459b732b-7de5-45f8-9553-f991fb1bffb0)
#### DB 상태
![image](https://github.com/user-attachments/assets/04fafc8e-0d67-487a-8aca-331d0e172143)

## 찜 목록 -> 상품 주문하기, 찜 상품 삭제
### 찜 목록 화면
![image](https://github.com/user-attachments/assets/13dfe656-b334-45dd-b8d7-2e7d99e96fa3)
- https://velog.io/@jaemm/BOOK-%EA%B0%9C%EC%9D%B8%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-9.-%EC%B0%9C-%EB%AA%A9%EB%A1%9D-%EC%83%81%ED%92%88-%EC%A3%BC%EB%AC%B8%ED%95%98%EA%B8%B0-%EC%B0%9C-%EC%83%81%ED%92%88-%EC%82%AD%EC%A0%9C

## 상품 주문하기, 상품 삭제
### 상품 주문 화면
![image](https://github.com/user-attachments/assets/b7d58dab-7630-49f9-9a7d-9ddb5d31239f)
- https://velog.io/@jaemm/BOOK-%EA%B0%9C%EC%9D%B8%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-10.-%EC%83%81%ED%92%88-%EC%A3%BC%EB%AC%B8%ED%95%98%EA%B8%B0-%EC%83%81%ED%92%88-%EC%82%AD%EC%A0%9C

## 주문 목록 - 주문 취소
### 주문 목록
![image](https://github.com/user-attachments/assets/ff9b8bab-ba02-42c9-a9a9-af8ce8a0e5ec)
### 주문 취소
![image](https://github.com/user-attachments/assets/6ebdef66-9b2e-44d8-bdae-0074b1046bd1)
상품 취소하기 버튼 클릭 

## 마이페이지
### 마이페이지 화면
![image](https://github.com/user-attachments/assets/ee0ba9e0-2dcf-46a7-83ee-f34870206e9b)
비밀번호 해쉬화 후 저장
