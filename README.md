# PhotoMania 웹사이트 프로젝트 

**Photomania**는 사용자가 일상에서 공유하고 싶은 사진을 업로드하고, 수정하고, 공유할 수 있는 웹 애플리케이션입니다. 
이 웹사이트는 사진 업로드 기능을 제공하며, 사용자는 다른 사용자들과 일상의 공유와 소통을 할 수 있으며, 서로 공감하는 공간을 마련합니다.

**프로젝트 기간**: **2025.1.7 ~ 2025.2.21**

**개발 인원**: 3명

## 주요 기능

- **사진 업로드**: 사용자는 자신의 사진을 업로드할 수 있음.
- **게시물 관리**: 업로드된 사진을 쉽게 관리하고, 제목 및 설명을 추가하여 게시물을 작성할 수 있음.
- **탐색**: 탐색을 통해, 대표적인 사진으로 카테고리에서 여러 사진을 다운로드 할 수 있음. 
- **사진 공유**:  사진을 통해 소통하고, 일상을 공유할 수 있음..
- **사용자 인증**: 이메일과 비밀번호를 통한 로그인 및 회원가입 기능을 제공.
- **AI 이미지 생성**: AI 이미지 생성을 통해 여러 이미지들을 쉽게 생성하여 사용자에게 제공.

## 데모
**[메인 화면]** ( 로고를 클릭시, 메인페이지로 돌아가도록 구현. )
![메인화면](https://github.com/user-attachments/assets/f2cb70ac-a07b-49c7-971f-9e14f882ff54)
- 한 화면에 공지사항, 이벤트, 탐색, 게시판 , 로그인창 표시. (헤더,푸터는 디자인 페이지마다 고정)
- Swiper 라이브러리 이용하여, 3초마다 자동으로 좌에서 우로 동작 구현.
- 영상은 웹 사이트 관련성을 위해서 동영상 AI 생성을 통해 동영상을 적절한 위치에 배치.

**[로그인/회원가입]**
![로그인/회원가입 화면](https://github.com/user-attachments/assets/36d5e4aa-7af4-4a8d-bf8e-89fbe18f1161)
- 로그인/회원가입 화면구성 ( DB를 통해서 회원 관리 ) ▶ 비밀번호는 암호화 처리.
- 회원가입을한 후 로그인이 가능하고, 회원은 해당 웹 사이트의 기능 이용 가능 ( 접근O, 게시물 업로드, 탐색 페이지 접근 )

**[게시판 목록]**

![게시판 목록](https://github.com/user-attachments/assets/e81a7838-ad81-4341-98b2-52269f3297db)
- 로그아웃 상태일때, 게시판 목록만 볼 수있음. ( 업로드나 게시물을 클릭시 메인페이지로 돌아옴. )
- 로그인 상태일때, 게시물 접근 가능, 업로드 기능도 사용 할수 있음. 
- 해당 게시물에 좋아요, 댓글, 답글 기능까지 작성 가능. ( DB에 저장된 수치에 따라 등록일, 좋아요, 조회수 표시 )

![게시물 업로드](https://github.com/user-attachments/assets/21705158-b0d3-4ccd-b914-f8deb0e5233d)

**[게시물 업로드]**
- 게시물 제목, 내용은 필수적으로 작성 해야함.
- 파일은 이미지 파일만 선택할수있게 제한. (jpg,png,jpeg 등)

![게시판 상세](https://github.com/user-attachments/assets/f86fa048-e3f8-4b12-8d17-d2287e2244f4)
**[게시판 상세]**
- 댓글, 답글 , 좋아요 기능 구현. [ 좋아요는 한 사용자당 한명씩 ]
- 등록일수 입력시간에 맞게 표시.
- 게시물 삭제 구현.

![탐색](https://github.com/user-attachments/assets/4e900adc-3178-43e0-9f1a-194bdbe6c680)
**[탐색]**
- 해당 카테고리 별로 대표적인 이미지가 나타남. (이미지를 우클릭후 직접 다운로드 받을수 있음.)
- AI 이미지 생성 버튼을 누르면 해당 페이지로 넘어감.


![AI 이미지 생성](https://github.com/user-attachments/assets/e64aed73-1597-46c3-abd9-e9f4ffe6a67e)

**[AI 이미지 생성]**
- 버튼 클릭시 무작위의 이미지가 생성됨. ( 단, 이미지를 캡쳐모드로 저장할 것. )
- 이미지 생성 갯수 제한없음.

## 기술 스택

- **프론트엔드**: HTML, CSS, JavaScript(Jquery)
- **백엔드**: 
  - **Java**: Spring Framework (Spring security), Spring Boot, Thymeleaf
  - **JPA**: Hibernate
  - **RESTful API**: Spring MVC
- **데이터베이스**: MySQL
- **버전 관리**: Git (Git Desktop)

## 실행 방법
사용하고 있는 컴퓨터에 Mysql과 해당프로그램의 비밀번호 필요 ★ 
=> src/main/resources/application.properties 에서  spring.datasource.password = (해당 칸에 mysql 비번으로 변경) 
1. main 레포지토리에 있는 파일을 다운받고, Intellij IDEA를 실행한다.
2. IDE에서 다운받은 폴더를 경로를 ( Open api Project/photomania_project ) 지정해서 그 폴더를 연다.
3. 이후, ( src/main/java/com.example.project/ProjectApplication ) 경로에서 프로그램을 실행한다.
4. 웹사이트에서 URL주소창에 ( localhost:8080 ) 진입하면 웹사이트에 접속 성공한다. ( 기본경로는 서버포트가 8080임. )



! **레포지토리 클론**: 
   ```bash
   git clone https://github.com/kimminu7/photomania.git
