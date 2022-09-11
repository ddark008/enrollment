# Yet Another Disk
Вступительное задание в Осеннюю Школу Бэкенд Разработки Яндекса 2022

## Package the application

- Package the application

`$ ./mvnw clean package`

> To skip the tests use: `-DskipTests=true`

- Extract libraries from `fat-jar`
  - Unix:  
  `$ mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)`
  - Windows:  
  `$   mkdir target/dependency; cd target/dependency; jar -xf ../*.jar`  

## Build
Run from root:  
`$ docker build -t ddark008/yadisk .`

## Run

`$ docker compose up`

## Verify the application is running

> Application listens on port 80.

Open in browser documentation: [http://localhost](http://localhost)

## Run tests
`$  py .\unit_test.py`