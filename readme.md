# backend-coding-challenge

A Spring boot Microservice which enable Retrieving information about Trending Repositories from Github REST API. 

## Getting Started

This Project is going to make use of Maven and Docker, so make sure those two tools are installed on your machine.


1 - clone this porject into your local machine.

```
git clone https://github.com/ismailer7/trending-repos
```

2 - build and package the project

```
cd trending-repos
mvn clean install
```

3 - Building the image

```
docker build -f Dockerfile -t trending-repos-docker .
```

4 - Run the image.

```
docker run -p 8081:8081 -t trending-repos-docker
```


## Documentation

the documentation is generated using swagger library

you can access to the documentation of our End points from the below url

```
http://localhost:8081/swagger-ui.html
```

## Some Tests

let get the languages used for 100 Trending Repository for recent 30 days from now.

```
http://localhost:8081/repos/trending/languages
```

![alt text](https://github.com/ismailer7/trending-repos/blob/master/src/main/resources/static/image-1.PNG?raw=true)

let get all Repositories for some specific Language

```
Example: java
```

```
http://localhost:8081/repos/trending/language?language=java&page=1
```

![alt text](https://github.com/ismailer7/trending-repos/blob/master/src/main/resources/static/image-2.PNG?raw=true)

## Author

* **Ismail Rzouki**
