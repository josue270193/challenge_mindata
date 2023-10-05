# challenge_mindata
## Instalacion
Se requiere de Java version 17+, MongoDB y Apache Kafka

## Uso
Se debe configurar los puerto en el caso de MongoDB y Apache Kafka en el archivo de application.yml o en las configuracion del jar.
```
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/
```
```
spring:
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
    producer:
      bootstrap-servers: localhost:9092
```
