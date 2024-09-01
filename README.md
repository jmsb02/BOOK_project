**자신이 원하는 책을 찾고 주문 할 수 있는 온라인 서점 플랫폼 - "북, 그럼"**

프로젝트 내용 관련 -  velog에 정리해두었습니다! :)

링크 : 
https://velog.io/@jaemm/%EA%B0%9C%EC%9D%B8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EB%AA%85%EC%84%B8%EC%84%9C-%EC%9E%91%EC%84%B1

## 로그인 화면

![image](https://github.com/user-attachments/assets/2bace273-3949-4c40-8f23-5870373bfffb)
- Spring Security 적용
- 밑에 Sign up 버튼을 통해 회원가입 폼으로 이동

## 회원 가입 화면 + Validation 적용
![image](https://github.com/user-attachments/assets/b2f24e4e-305e-4483-8d20-4a834f45844c)

## 로그인 성공 -> 홈 화면으로 이동 
![image](https://github.com/user-attachments/assets/295bc33d-e81b-439e-ab4e-20c7d4faaf79)

## 로그인 실패 -> Validation 적용
![image](https://github.com/user-attachments/assets/38766457-6bce-406d-a4a5-1beb386a84d1)

## 홈 화면
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
