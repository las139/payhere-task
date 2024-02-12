# 사용할 기본 이미지 지정: OpenJDK 17 공식 이미지
FROM amazoncorretto:17

# 컨테이너 내부에서 애플리케이션 파일을 저장할 디렉토리 생성
WORKDIR /app

# 빌드한 애플리케이션 JAR 파일을 컨테이너의 작업 디렉토리로 복사
# 빌드 컨텍스트의 대상 JAR 파일 이름과 일치해야 합니다.
# 프로젝트의 build/libs 또는 target/ 디렉토리 내에 위치한 JAR 파일을 가정합니다.
COPY /build/libs/payhere-task-0.0.1-SNAPSHOT.jar /app/payhere-task-app.jar

# 컨테이너가 시작될 때 실행할 명령어 지정
# Spring Boot 애플리케이션 실행
ENTRYPOINT ["java","-jar","/app/payhere-task-app.jar"]
