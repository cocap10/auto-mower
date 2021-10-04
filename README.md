# Auto-Mower
## Summary
This is an implementation of the auto-mower program running with java 17 and Spring Boot Web 2.5 framework.
The specification of this program can be find in the INSTRUCTION.md file.
Once running, it exposes an endpoint at http://localhost:8080/api/mower/runFromInput
The end of this document shows how to use it.

## Limits
This implementation handles most communes input errors but:
- The program cannot detect if one mower goes on the same cell of another (we assume that it is possible)
- The lawn dimensions cannot be higher than the integer limit (2^31-1)
  
## Test
- Run the app:
with maven
```
mvn org.springframework.boot:spring-boot-maven-plugin:2.5.5:run
```

or with docker
```
docker build -t piegaym/auto-mower .
docker run -p 8080:8080 piegaym/auto-mower
```
- Test it:
```
curl --location --request POST 'http://localhost:8080/api/mower/runFromInput' \
--header 'Content-Type: text/plain' \
--data-raw '5 5    
1 2 N
LFLFLFLFF    
3 3 E
FFRFFRFRRF'
```
You will get the response HTTP 200
```
1 3 N
5 1 E

```