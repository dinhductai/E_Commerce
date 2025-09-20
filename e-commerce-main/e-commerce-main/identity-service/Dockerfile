# Sử dụng một biến (ARG) để nhận diện module đang được build
ARG MODULE_PATH

# --- Stage 1: Build ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
ARG MODULE_PATH

WORKDIR /workspace

# Sao chép toàn bộ dự án vào trong image build
# Cách này đơn giản và đảm bảo Maven có đủ mọi thứ nó cần
COPY . .

# Chạy lệnh build từ thư mục gốc,
# nhưng chỉ build module được chỉ định (-pl) và các module phụ thuộc của nó (-am)
RUN mvn -pl ${MODULE_PATH} -am clean package -DskipTests

# --- Stage 2: Run ---
FROM eclipse-temurin:21-jre-jammy
ARG MODULE_PATH

WORKDIR /app

# Sao chép file .jar đã được build từ đúng thư mục target của module
COPY --from=build /workspace/${MODULE_PATH}/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]