# Docker compose
1. Install via chocolately package manager
   `$ choco install docker-compose`

2. create Dockerfiles for images (one file per one image) in the separate folder, for instance "docker" one

## postgres_pga
```
FROM dpage/pgadmin4:6.13
EXPOSE 80
ENV PGADMIN_DEFAULT_EMAIL admin@admin.com
ENV PGADMIN_DEFAULT_PASSWORD admin
```
## postgres_server
```
FROM postgres:15.1
EXPOSE 5432
ENV POSTGRES_USER admin
ENV POSTGRES_PASSWORD admin
ENV POSTGRES_DB internetstore
```
## java_application
```
FROM amazoncorretto:17.0.5-alpine
EXPOSE 8090
RUN addgroup -S spring-group && adduser -S spring -G spring-group
USER spring:spring-group
ARG JAR_FILE=application.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]
```

## Create docker compose script: "docker-compose.yml"
```
version: "3.9"
# all images are presented as services inside of docker-compose file. Image == Service in the docker-compose
services:		
    # first service
    postgres-server:
      # internal network, which joins two images
      networks:	
        # network name
        - default					
      # setting up build configuration for image 
      build:		
        # set folder, which includes Dockerfiles
        context: ./dockerfiles	
        # set custom Dockerfile name
        dockerfile: postgres_server	
      ports:
        # port forwarding for host (it works for host PC)
        - "5432:5432"				
    
    # Postgres admin in container
    pga:											       
      networks:
        - default
      build:
        context: ./dockerfiles
        dockerfile: pga_web_ui
        
      # describes range of internal image, which are restricted for this image. 
      # without image "server" pga should not be launched.
      depends_on:		
        - postgres-server:			
      ports:
        - "5333:80"
    
    # Java application in container
    application:
      networks:
        - default
      build:
        context: ./dockerfiles
        dockerfile: java_application
      depends_on:
        - postgres-server
      ports:
        - "8090:8090"
        
# Network description        
networks:
  default:
    driver: bridge
```	

### Docker-compose launch instructions
### Create and launch docker compose

```
$ docker-compose -p internetstore -f docker-compose.yaml up -d --build
```
### Stop docker-compose and free all resources, delete networks and containers
```
$ docker-compose -p internetstore -f docker-compose.yaml down
```

- -p -- set prefix (containers names will be specified with it)
- -f -- set custom docker-compose.yml name
- up -- docker-compose launch command
- -d -- detached mode (free console from supported info). Launch docker-compose in background mode
- --build -- build images from Dockerfiles inside of docker-compose when it launches