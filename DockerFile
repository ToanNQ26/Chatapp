# Dùng hình ảnh Java JRE chính thức
FROM openjdk:17-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Copy file jar đã build từ Maven/Gradle
COPY target/chatapp-0.0.1-SNAPSHOT.jar app.jar

# Cấu hình lệnh chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
