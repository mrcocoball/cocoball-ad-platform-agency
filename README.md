# 광고 플랫폼 대행사 센터 페이지 제작

광고 플랫폼의 대행사 전용 관리 센터를 제작해보고 추가 기능을 구현해본다 (장기적으로) </br>
광고 플랫폼이 보편적으로 제공하고 기능을 구현해보고 </br>
나아가 매체 커뮤니케이션 및 영업지원 부서의 입장에서 필요한 기능을 추가로 구현해보고자 한다. </br></br>
순차적으로 릴리즈를 하며 기능을 추가할 예정.

## 사용 예정 스택

* Java
* Spring Boot
* Spring Data JPA
* Spring Data REST
* Spring Data REST HAL Explorer
* Spring Security
* Spring Properties DevTools
* Thymeleaf
* MySQL
* Chart.js
* Querydsl
* GitKraken
* Heroku </br></br>

## 1.0.0 릴리즈 구현 기능 (2022-09-11)
- URL : https://cocoball-ad-agency-platform.herokuapp.com/
- 최초 로그인이 필요 (ID : TestAgency / PW : test1234)
- 대행사 어드민 기능이 구현되었음

* 에이전트 관리 기능
   * 에이전트 리스트 조회
   * 에이전트 상세 정보 및 매핑된 광고주 리스트 조회
   * 에이전트에 매핑된 광고주 리스트 > 광고 관리 페이지로 이동
   * 에이전트 삭제 (매핑된 광고주 없을 시)
* 에이전트 그룹 관리 기능
   * 에이전트 그룹 리스트 조회
   * 에이전트 그룹에 소속된 에이전트 리스트 조회 및 관리 페이지로 이동
   * 에이전트 그룹 삭제 (소속된 광고주 없을 시)
   * 에이전트 그룹 이름 수정
* 광고주 관리 기능
   * 광고주 리스트 조회
   * 광고주 상세 정보 조회
   * 광고 관리 페이지로 이동
* 광고 관리 기능
   * 광고주 리스트 조회
   * 광고주의 캠페인 리스트 조회
   * 광고주의 캠페인 생성 / 수정 / 삭제
   * 광고주의 캠페인 내의 소재 리스트 조회
   * 광고주의 캠페인 내의 소재 생성 / 수정 / 삭제

## 2.0.0 릴리즈를 위한 개발 진행 중
- 기획 문서 : https://velog.io/@mrcocoball/220913%EA%B4%91%EA%B3%A0-%EA%B4%80%EB%A6%AC-%ED%94%8C%EB%9E%AB%ED%8F%BC-%EB%8C%80%ED%96%89%EC%82%AC-%EC%84%BC%ED%84%B0-%EC%A0%9C%EC%9E%91-172.0.0-%EB%B2%84%EC%A0%84-%EB%A6%B4%EB%A6%AC%EC%A6%88-%EA%B8%B0%ED%9A%8D
- 순차적 진행 중

## 추가 예정 기능
* 대행사 어드민 기능
    * 에이전트 생성 기능
    * 에이전트 수정 - 소속 그룹 수정 기능
    * 광고주 수정 - 담당 에이전트 수정 기능
    * 캠페인 / 소재 통계 기능
    * 대시보드 기능
    * 리포트 출력 기능
* 실제 플랫폼처럼 이관 요청 - 승인 작업의 경우 광고주 어드민도 있어야 하므로 현재 버전에서는 일단 구현 생략 </br>
  (추후 버전업 시 구현 고려 중) </br></br>

## 참고 플랫폼

* Criteo
* ABLY
* Mobon
* TargetingGates
* 11st
* Google
