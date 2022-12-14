# Cocoball AD Platform Agency

광고 플랫폼의 대행사 전용 관리 센터를 제작해보고 추가 기능을 구현해본다 (장기적으로) </br>
광고 플랫폼이 보편적으로 제공하고 기능을 구현해보고 </br>
나아가 매체 커뮤니케이션 및 영업지원 부서의 입장에서 필요한 기능을 추가로 구현해보고자 한다. </br></br>
순차적으로 릴리즈를 하며 기능을 추가할 예정.

## 사용 스택

* Java
* Spring Boot
* Spring Data REST
* Spring Data REST HAL Explorer
* Spring Security
* Spring Properties DevTools
* Spring Data JPA
* Querydsl
* MySQL
* Thymeleaf
* Javascript / jQuery
* Chart.js
* GitKraken
* AWS RDS / EC2 </br></br>

## 1.3.0 릴리즈 구현 기능 (2022-11-14)
- URL : <a href="http://cocoballagency.ga/" target='_blank'>확인하기</a>
- 최초 로그인이 필요 (ID : TestAgency / PW : test1234) 
- 대행사 어드민 기능이 구현되었음
- 1.3.0 기획 문서 : <a href="https://velog.io/@mrcocoball/221028%EA%B4%91%EA%B3%A0-%EA%B4%80%EB%A6%AC-%ED%94%8C%EB%9E%AB%ED%8F%BC-%EB%8C%80%ED%96%89%EC%82%AC-%EC%84%BC%ED%84%B0-%EC%A0%9C%EC%9E%91-431.3.0-%EB%B2%84%EC%A0%84-%EB%A6%B4%EB%A6%AC%EC%A6%88-%EA%B8%B0%ED%9A%8D" target='_blank'>확인하기</a>
- 1.3.0 업데이트 내용 정리 : <a href="https://velog.io/@mrcocoball/221114%EA%B4%91%EA%B3%A0-%EA%B4%80%EB%A6%AC-%ED%94%8C%EB%9E%AB%ED%8F%BC-%EB%8C%80%ED%96%89%EC%82%AC-%EC%84%BC%ED%84%B0-%EC%A0%9C%EC%9E%91-541.3.0-%EB%B2%84%EC%A0%84-%EB%A6%B4%EB%A6%AC%EC%A6%88-%EB%B0%B0%ED%8F%AC" target='_blank'>확인하기</a>
- Endpoints & API Spec (구글 스프레드 시트) : <a href="https://docs.google.com/spreadsheets/d/1-BwBfL-ueBmqHRt8ew1FzkCIftxCdYlsnspRv2GkSlk/edit#gid=2041215260" target='blank'>확인하기</a> 
- <b>에이전트 관리 기능</b>
   * 에이전트 리스트 조회
   * 에이전트 상세 정보 및 매핑된 광고주 리스트 조회
   * 에이전트에 매핑된 광고주 리스트 > 광고 관리 페이지로 이동
   * 에이전트 삭제 (매핑된 광고주 없을 시)
- <b>에이전트 그룹 관리 기능</b>
   * 에이전트 그룹 리스트 조회
   * 에이전트 그룹에 소속된 에이전트 리스트 조회 및 관리 페이지로 이동
   * 에이전트 그룹 생성
   * 에이전트 그룹 삭제 (소속된 광고주 없을 시)
   * 에이전트 그룹 이름 수정
- <b>광고주 관리 기능</b>
   * 광고주 리스트 조회
   * 광고주 상세 정보 조회
   * 광고 관리 페이지로 이동
- <b>광고 관리 기능</b>
   * 광고주 리스트 조회
   * 광고주의 캠페인 리스트 조회
   * 광고주의 캠페인 생성 / 수정 / 삭제
   * 광고주의 캠페인 활성화 상태 변경 (ON / OFF)
   * 광고주의 캠페인 내의 소재 리스트 조회
   * 광고주의 캠페인 내의 소재 생성 / 수정 / 삭제
   * 광고주의 캠페인 내의 소재 활성화 상태 변경 (ON / OFF)
   * 광고주의 캠페인 내의 소재의 실적 조회 및 통계 확인
   * 광고주의 캠페인의 실적 조회 및 통계 확인
   * 광고주의 실적 조회 및 통계 확인
   * 에이전시 내 광고주의 전체 소진액 조회
   * 광고주 / 캠페인 / 소재 / 소재의 실적 보고서 다운로드 기능 - 1.3.0 릴리즈 추가
   * 광고주의 캠페인 / 소재 / 소재의 실적 지표 차트 제공 - 1.3.0 릴리즈 추가
- <b>대시보드 기능 - 1.3.0 릴리즈 전체 추가</b>
   * 대행사 대시보드 - 일일 소진액 차트, 통계 제공 및 보고서 다운로드 기능
   * 대행사 대시보드 - 광고주별 소진액 차트 (TOP 10), 통계 제공 및 보고서 다운로드 기능
   * 그룹-에이전트 대시보드 - 그룹별 소진액 차트, 통계 제공 및 보고서 다운로드 기능
   * 그룹-에이전트 대시보드 - 에이전트별 소진액 차트 (TOP 10), 통계 제공 및 보고서 다운로드 기능
   * 업종별 대시보드 - 업종별 소진액 차트, 통계 제공 및 보고서 다운로드 기능
   * 업종별 대시보드 - 업종별 레퍼런스 차트(지표별 확인 가능), 통계 제공 및 보고서 다운로드 </br></br>
   
## 1.3.0 이후 지속적인 릴리즈 진행 중
- 릴리즈 작업 현황 : <a href="https://github.com/users/mrcocoball/projects/4/views/1" target='_blank'>Projects 확인하기</a> 
</br></br>

## 추가 예정 기능
* 대행사 어드민 기능
    * 에이전트 생성 기능
    * 에이전트 수정 - 소속 그룹 수정 기능
    * 광고주 수정 - 담당 에이전트 수정 기능
* 실제 플랫폼처럼 이관 요청 - 승인 작업의 경우 광고주 어드민도 있어야 하므로 현재 버전에서는 일단 구현 생략 </br>
  (추후 버전업 시 구현 고려 중) </br></br>

## 참고 플랫폼

* NAVER GFA
* Criteo
* ABLY
* Mobon
* TargetingGates
* 11st
* Google
