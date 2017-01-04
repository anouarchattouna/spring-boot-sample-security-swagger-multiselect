# spring-boot-sample-security-swagger-multiselect

[![Build Status](https://secure.travis-ci.org/anouarchattouna/spring-boot-sample-security-swagger-multiselect.png)](http://travis-ci.org/anouarchattouna/spring-boot-sample-security-swagger-multiselect)

This is a sample application based on:
- [Spring Boot](https://github.com/spring-projects/spring-boot.git).
- Spring Security.
- Thymeleaf.
- [Swagger UI](https://github.com/swagger-api/swagger-ui.git).
It allows you to have a separate Swagger UI that renders your APIs Specifications.

# build & run 
mvn clean install && java -jar target/spring-boot-sample-security-swagger-multiselect-0.0.1-SNAPSHOT.jar

# Star using the application
http://localhost:8080/api-documentation

# Available APIs
APIs are configured in the main application.yml file. An API has a key=label, a value=url of the api swagger/json file and a role:
```
instagram:
    key: Instagram API
    value: https://raw.githubusercontent.com/swagger-api/swagger-editor/master/spec-files/instagram.yaml
    role: ROLE_INSTAGRAM
uber:
    key: Uber API
    value: https://raw.githubusercontent.com/swagger-api/swagger-editor/master/spec-files/default.yaml
    role: ROLE_UBER
```
# Security & users
Users are configured in the main application.yml file. A user has a password=encripted password and a role=a comma separated list of roles:
```
user1:
    # encripted password: azertyuiop
    password: $2a$10$IONw4cy/7AXIbbf/WGM0I.zrpmDRfHA9eBHVy3l7Oc8O/UP2jcLUG
    roles: ROLE_INSTAGRAM
user2:
    # encripted password: 01010101
    password: $2a$10$KSXr.ooY3qXA5/iQb2zH..9mfW5bpbJLII72Q7UfD.C7.kwCCKx3O
    roles: ROLE_UBER
```
The idea is that a user is allowed to have access to one or more APIs Specifications.
