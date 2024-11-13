# 使用 openjdk:22 基础镜像
FROM openjdk:22

# 设置工作目录
WORKDIR /my-project

# 复制 gradlew 和其他相关文件
COPY ./gradlew ./gradlew
COPY ./gradle ./gradle
COPY ./src ./src
COPY ./build.gradle ./build.gradle
COPY ./settings.gradle ./settings.gradle

# 给予 gradlew 执行权限
RUN chmod +x gradlew

# 运行 Gradle 构建命令，生成可执行的 JAR 文件
RUN ./gradlew clean bootJar

# 复制 JAR 文件到容器中
COPY ./build/libs/Assignment4-1.0.jar app.jar


# 设置容器启动时运行的命令
ENTRYPOINT ["java", "-jar", "app.jar"]

