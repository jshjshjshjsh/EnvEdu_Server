# 스마트 환경 교육 시스템 백엔드
## 개발 목적
* 다양한 센서를 부착한 아두이노 기반의 기기에서 실시간으로 전송하는 환경 데이터를 웹 상에서 볼 수 있도록 하는 플랫폼을 기기와 연결하고, 데이터를 저장하기 위한 서버
* 앱 형태로 모바일 기기에서 블루투스를 이용해 센서 기기와 일대 일 통신과 제한된 거리라는 제약을 없애기 위해 웹 환경으로의 이전을 목표로 하고 있음

## 주요 기능
* 사용자
  * 학생과 교사로 구분
  * 공동
    * 로그인 & 로그아웃
    * 자신에게 등록된 기기가 있는 경우, 해당 기기의 데이터 모니터링
    * 자신의 기기가 전송한 데이터를 서버의 DB에 저장
    * 저장된 데이터 조회
    * 데이터 저장 시 사용할 위치 정보 등록(예정)
  * 교사
    * 자신이 지도하는 학생 등록
    * 자신이 지도하는 학생 목록 조회(예정)
    * 어드민에게 기기 등록 요청 전송(예정)
    * 자신의 기기뿐만 아니라 지도하는 학생에게 등록된 기기의 데이터도 모니터링 가능
* 어드민
  * 로그인 & 로그아웃
  * 기기 등록
  * 등록된 기기에 사용자 할당 및 기기 별칭 업데이트


## Package Structure
```
src
 ├─ main
 | ├─ java
 | | └─ com
 | | | └─ example
 | | | | └─ demo
 | | | | | ├─ admin
 | | | | | | ├─ cipher
 | | | | | | | └─ AdminCipher.java
 | | | | | | ├─ controller
 | | | | | | | └─ AdminController.java
 | | | | | | ├─ DTO
 | | | | | | | └─ AdminLoginDTO.java
 | | | | | | ├─ model
 | | | | | | | └─ Admin.java
 | | | | | | ├─ repository
 | | | | | | | └─ AdminRepository.java
 | | | | | | └─ service
 | | | | | | | └─ AdminService.java
 | | | | | ├─ cookie
 | | | | | | └─ util
 | | | | | | | └─ CookieUtil.java
 | | | | | ├─ device
 | | | | | | ├─ controller
 | | | | | | | └─ UserDeviceController.java
 | | | | | | ├─ dto
 | | | | | | | ├─ request
 | | | | | | | | └─ DeviceUpdateDTO.java
 | | | | | | | └─ response
 | | | | | | | | ├─ DeviceListDTO.java
 | | | | | | | | └─ RelatedUserDeviceListDTO.java
 | | | | | | ├─ model
 | | | | | | | └─ UserDevice.java
 | | | | | | ├─ repository
 | | | | | | | └─ UserDeviceRepository.java
 | | | | | | └─ service
 | | | | | | | └─ UserDeviceService.java
 | | | | | ├─ DTO
 | | | | | | ├─ AddMACDTO.java
 | | | | | | ├─ DataSaveDTO.java
 | | | | | | ├─ MacListDTO.java
 | | | | | | └─ ResponseDTO.java
 | | | | | ├─ exceptions
 | | | | | | ├─ CustomMailException.java
 | | | | | | ├─ DuplicateAttributeException.java
 | | | | | | └─ NoJwtTokenContainedException.java
 | | | | | ├─ jwt
 | | | | | | ├─ model
 | | | | | | | ├─ JwtAccessToken.java
 | | | | | | | ├─ JwtRefreshToken.java
 | | | | | | | └─ JwtToken.java
 | | | | | | └─ util
 | | | | | | | └─ JwtUtil.java
 | | | | | ├─ location
 | | | | | | └─ model
 | | | | | | | └─ Location.java
 | | | | | ├─ mail
 | | | | | | └─ service
 | | | | | | | └─ MailService.java
 | | | | | ├─ redis
 | | | | | | ├─ entity
 | | | | | | | └─ AuthNum.java
 | | | | | | ├─ repo
 | | | | | | | └─ AuthNumRepository.java
 | | | | | | ├─ RedisConfig.java
 | | | | | | └─ RedisService.java
 | | | | | ├─ security
 | | | | | | ├─ config
 | | | | | | | ├─ AuthenticationFilter.java
 | | | | | | | ├─ AuthenticationFilterApply.java
 | | | | | | | ├─ AuthorizationFilter.java
 | | | | | | | ├─ AuthorizationFilterApply.java
 | | | | | | | ├─ CorsConfig.java
 | | | | | | | └─ SecurityConfig.java
 | | | | | | ├─ jwt
 | | | | | | | ├─ JwtUtil.java
 | | | | | | | └─ Properties.java
 | | | | | | └─ principal
 | | | | | | | ├─ PrincipalDetails.java
 | | | | | | | └─ PrincipalDetailsService.java
 | | | | | ├─ seed
 | | | | | | ├─ controller
 | | | | | | | └─ SeedController.java
 | | | | | | ├─ misc
 | | | | | | | └─ Misc.java
 | | | | | | ├─ model
 | | | | | | | └─ Seed.java
 | | | | | | ├─ repository
 | | | | | | | └─ SeedRepository.java
 | | | | | | ├─ service
 | | | | | | | └─ SeedService.java
 | | | | | | └─ socket
 | | | | | | | ├─ config
 | | | | | | | | ├─ DeviceSocketInterceptor.java
 | | | | | | | | ├─ SocketConnectionInterceptor.java
 | | | | | | | | └─ WebSocketConfig.java
 | | | | | | | └─ controller
 | | | | | | | | └─ MessageController.java
 | | | | | ├─ token
 | | | | | | ├─ model
 | | | | | | | ├─ AccessToken.java
 | | | | | | | └─ RefreshToken.java
 | | | | | | └─ repository
 | | | | | | | └─ RefreshTokenRepository.java
 | | | | | ├─ user
 | | | | | | ├─ controller
 | | | | | | | ├─ UserLogoutController.java
 | | | | | | | └─ UserRegisterController.java
 | | | | | | ├─ dto
 | | | | | | | └─ request
 | | | | | | | | ├─ EmailDTO.java
 | | | | | | | | ├─ LoginDTO.java
 | | | | | | | | ├─ RegisterDTO.java
 | | | | | | | | └─ StudentAddDTO.java
 | | | | | | ├─ model
 | | | | | | | ├─ entity
 | | | | | | | | ├─ Educator.java
 | | | | | | | | ├─ EducatorInfo.java
 | | | | | | | | ├─ Student.java
 | | | | | | | | ├─ Student_Educator.java
 | | | | | | | | └─ User.java
 | | | | | | | └─ enumerate
 | | | | | | | | ├─ Gender.java
 | | | | | | | | ├─ IsAuthorized.java
 | | | | | | | | ├─ Role.java
 | | | | | | | | └─ State.java
 | | | | | | ├─ repository
 | | | | | | | ├─ EducatorRepository.java
 | | | | | | | ├─ StudentRepository.java
 | | | | | | | ├─ Student_EducatorRepository.java
 | | | | | | | └─ UserRepository.java
 | | | | | | ├─ service
 | | | | | | | └─ UserService.java
 | | | | | | └─ util
 | | | | | | | └─ Utils.java
 | | | | | └─ DemoApplication.java
 | └─ resources
 | | ├─ application.yml
 | | └─ logback-spring.xml
 └─ test
 | └─ java
 | | └─ com
 | | | └─ example
 | | | | └─ demo
 | | | | | ├─ admin
 | | | | | | ├─ controller
 | | | | | | | └─ AdminControllerTest.java
 | | | | | | └─ service
 | | | | | | | └─ AdminServiceTest.java
 | | | | | ├─ jwt
 | | | | | | └─ JwtTest.java
 | | | | | ├─ user
 | | | | | | ├─ controller
 | | | | | | | └─ UserControllerTest.java
 | | | | | | └─ repository
 | | | | | | | └─ RedisTest.java
 | | | | | └─ DemoApplicationTests.java
```

## ERD
<img src="https://user-images.githubusercontent.com/38274661/236670645-da1a58c3-9f2f-4a8f-ab7d-5d2a39ca008c.png" width="800" height="500"/>

* User
  * 각각의 사용자
  * Student, Educator
* Educator_Student(or Student_Educator)
  * 교사가 지도하는 학생을 나타내기 위한 다대 다 매핑 테이블
  * 한 명의 교사는 여러 명의 학생을 지도할 수 있고, 한 명의 학생은 여러 명의 지도 교사가 있을 수 있음
* EducatorInfo
  * 교사 회원가입 후, 제공되는 기능을 사용하기 위해 제출해야 하는 문서를 매핑하는 테이블
  * 자료 제출 기능은 구현 예정
* Location
  * 기기의 데이터를 저장할 때, 자주 사용하는 위치 정보를 위한 테이블
* Device
  * 각 유저에게 할당되는 센서 기기를 위한 테이블
* Seed
  * 기기가 전송하는 데이터를 위한 테이블
* Admin
  * 어드민 정보를 위한 테이블

## Architecture
<img src="https://user-images.githubusercontent.com/38274661/236678891-27725721-331f-451b-80a0-90ca7f573fc4.png" width="600" height="500"/>

* NGINX
  * Reverse Proxy
  * Certbot을 이용한 ssl 인증서 발급
* Redis
  * 회원가입 시 인증번호 저장
  * 센서 기기와 사용자를 연결하는 Messaging Queue로 사용(예정)
* Slack
  * 로그를 확인하기 위해 ssh 연결을 하는 과정을 생략함으로써 접근성 향상