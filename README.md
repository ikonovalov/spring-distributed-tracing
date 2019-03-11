### Layout
* Spring configuration server
* Spring admin server
* 3 business services
* Jaeger distributed tracing
* docker-compose with Make automation


### How to start
Starts configuration service first. ```make clean && make```. Business services are in a fail state now - configuration server has started first. You need to restart business services with ```make rs```


### Applications
* Spring admin server http://localhost:8099
* Jaeger WebUI http://localhost:16686
* Order service ```curl -XPOST http://localhost:8082/api/order```