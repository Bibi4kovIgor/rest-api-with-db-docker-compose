# Docker compose
1. Install via chocolately package manager
   `$ choco install docker-compose`

2. create Dockerfiles for images (one file per one image) in the separate folder, for instance "docker" one

## postgres_server
```
FROM postgres:16-alpine3.18
EXPOSE 5432
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB test
```
## java_application
```
FROM amazoncorretto:17-alpine3.18
EXPOSE 8083
RUN addgroup -S spring-group \
    && adduser -S spring -G spring-group
USER spring:spring-group
ENV JAR_FILE=demo.jar
ENV WORKDIR_PATH=/app
WORKDIR ${WORKDIR_PATH}
COPY ${JAR_FILE} ${JAR_FILE}
RUN pwd \
    && ls
ENTRYPOINT java -jar ${WORKDIR_PATH}/${JAR_FILE}
```

## Create docker compose script: "docker-compose.yml"
```
version: "3.9"
# all images are presented as services inside of docker-compose file. Image == Service in the docker-compose
services:		
    # first service
    server:
      # internal network, which joins two images
      networks:	
        # network name
        - default					
      # setting up build configuration for image 
      build:		
        # set folder, which includes Dockerfiles
        context: ./dockerfiles	
        # set custom Dockerfile name
        dockerfile: docker-postgres-server	
      ports:
        # port forwarding for host (it works for host PC)
        - "5432:5432"				
        
    # Java application in container
    application:
      networks:
        - default
      build:
        context: ./dockerfiles
        dockerfile: docker-java-application
      depends_on:
        - server
      ports:
        - "8084:8084"
        
# Network description        
networks:
  default:
    driver: bridge
```	

### Docker-compose launch instructions
### Create and launch docker compose

```
$ docker-compose -p rest-api-with-db -f docker-compose.yaml up -d --build
```
### Stop docker-compose and free all resources, delete networks and containers
```
$ docker-compose -p rest-api-with-db -f docker-compose.yaml down
```

- -p -- set prefix (containers names will be specified with it)
- -f -- set custom docker-compose.yml name
- up -- docker-compose launch command
- -d -- detached mode (free console from supported info). Launch docker-compose in background mode
- --build -- build images from Dockerfiles inside of docker-compose when it launches