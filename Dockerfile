# Dùng hình ảnh Java JRE chính thức
FROM openjdk:21-jdk-slim

# Đặt thư mục làm việc
WORKDIR /app

# Copy file jar đã build từ Maven/Gradle
COPY services/chatapp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Cấu hình lệnh chạy ứng dụng
CMD ["java", "-jar", "app.jar"]
