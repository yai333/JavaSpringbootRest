# Deploying a Spring Boot REST API on Azure Kubernetes Service with Azure Database for PostgreSQL

## Blog article

https://yia333.medium.com/deploying-a-spring-boot-rest-api-on-azure-kubernetes-service-with-azure-database-for-postgresql-4bf86a8059e0

## Compile

```
$mvn install dependency:copy-dependencies -DskipTests && cd target/dependency; jar -xf ../*.jar && cd ../..
```
