# Базовый образ
FROM eclipse-temurin:17-jre-alpine
MAINTAINER i@ddark008.ru
# Запускаем приложение не от root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
# Используем не jar файл, а приложение распакованное приложение, командой mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
# Обозначаем порт подключения
EXPOSE 8080
# Точка запуска
ENTRYPOINT ["java","-cp","app:app/lib/*","ru.ddark008.yadisk.YadiskApplication"]