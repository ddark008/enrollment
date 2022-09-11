# Yet Another Disk
Вступительное задание в Осеннюю Школу Бэкенд Разработки Яндекса 2022

## Package the application

- Package the application

`$ ./mvnw clean package`

> To skip the tests use: `-DskipTests=true`

- Extract libraries from `fat-jar`

`$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)`

## Build

`$ docker build -t ddark008/yadisk .`

## Run

`$ docker-compose up`

## Verify the application is running

> Application listens on port 80.

Open in browser documentation: [http://localhost](http://localhost)