POC Rest API with Quarkus

## Herramientas

- Java 21
- Quarkus 3.15.1

## Compilacion

 ```
mvn compile quarkus:dev
 ```

## Limpiar paquetes

 ```
$ mvn clean package
 ```

## Ejecucion del binario generado

 ```
$ java -jar target\quarkus-app\quarkus-run.jar
 ```

## Consulta

```
$ curl -L -X GET 'http://localhost:8080/transfer/'
```

## Creacion

 ```
$ curl -L -X POST 'http://localhost:8080/transfer/' \
-H 'Content-Type: application/json' \
--data-raw '{
    "accountFrom":"4444",
    "accountTo": "55555",
    "amount" : 50000
}'
```

## Solución de problemas

A veces ejecutamos la compilación binaria directamente y obtenemos el siguiente error,

```
no main manifest attribute, in target\poc-quarkus-1.0-SNAPSHOT.jar
``` 

El error anterior ocurre porque estamos ejecutando el archivo jar incorrecto dentro de la carpeta `target`, el
correcto (a pesar de no ser un archivo jar) en realidad está en la carpeta `target\quarkus-app`

## Docker build

Ejecute el siguiente comando para realizar una compilación de Docker

```
$ docker build -t poc-quarkus-1.0-SNAPSHOT .
```

Y ejecútelo usando el siguiente comando

```
$ docker run poc-quarkus-transfer

Primero asegúrese de configurar correctamente la conexión de la base de datos en `application.properties`.