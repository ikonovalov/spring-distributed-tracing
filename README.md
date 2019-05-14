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
* Linkerd admin http://localhost:9990
* Graphite http://localhost:8032


### Linkerd as a HTTP proxy
Access to order service via Linkerd.
```bash
export http_proxy=http://localhost:4140
curl -v http://service-order-l5d/actuator/health
curl -XPOST http://service-order-l5d/api/order
ab -X localhost:4140 -c10 -n1000 http://service-order-l5d/actuator/health
```