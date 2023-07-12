# 빌드 : docker build -t gumi-presso-back .
# tar파일 복사 : docker save -o gumi-presso-back.tar gumi-presso-back:latest


# 기반이 될 이미지 선택 (여기에서는 OpenJDK 1.8 사용)
FROM openjdk:8-jdk-alpine

# 작업 디렉토리 생성
WORKDIR /app

# 소스 코드를 도커 이미지로 복사
COPY . /app

# # Maven을 사용하여 애플리케이션 빌드
# RUN mvn clean install -Dmaven.compiler.source=1.8 -Dmaven.compiler.target=1.8

# 빌드된 JAR 파일을 도커 이미지로 복사
COPY target/mt_web_back_gumi0607_11-0.0.1-SNAPSHOT.jar /app/mt_web_back_gumi0607_11-0.0.1-SNAPSHOT.jar

# 8080 포트 공유
EXPOSE 8080

# 애플리케이션 실행을 위한 커맨드 설정
CMD ["java", "-jar", "mt_web_back_gumi0607_11-0.0.1-SNAPSHOT.jar"]