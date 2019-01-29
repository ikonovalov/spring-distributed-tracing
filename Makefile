APP_VERSION=1.0.0

all: build up

build:
	mvn clean package

up:
	docker-compose up

down:
	docker-compose down

clean: down
	docker volume prune --force

rs:
	docker-compose restart service-time && docker-compose restart service-order && docker-compose restart service-reservation
