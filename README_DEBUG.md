# 디버깅 환경 구축
README_TEST를 따라 성공했다면 디버깅 환경을 구축해보자
## 1. 테스트 코드 수정하기
위치 : /redis/src/main/java/TestApp.java<br>
수정할 곳 : /프로젝트_위치/ 에 calcite 프로젝트의 절대 경로를 넣어준다.
```java
String absolutePath = "/프로젝트_위치/calcite/redis/src/test/resources/redis-mix-model.json";
```

## 2. Redis Adapter 코드 수정
## 3. break point 걸기

## 4. TestApp을 디버깅 모드로 실행
위치 : /redis/src/main/java/TestApp.java<br>

## 5. 기존 방식대로 질의, 질의 결과 보기, 디버깅 가능
