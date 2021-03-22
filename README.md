<p align="center">
  <a href="https://www.udemy.com/course/restful-web-service-with-spring-boot-jpa-and-mysql/">
  <img src="https://cdn.pixabay.com/photo/2018/08/06/21/32/darknet-3588402_1280.jpg" 
  height="300" 
  title="RESTful Web Services, Java, Spring Boot, Spring MVC and JPA"
  target="_blank">
  </a>
</p>
<p align="center">
<img src="https://img.shields.io/badge/Status-In Progress-blue.svg" />
  <img src="https://img.shields.io/badge/Made%20With-Spring-green.svg" />
</p>

### Tools and Projects used:

- ✅ Spring `Boot`
  - [Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/2.4.4/reference/html/)
- ✅ Spring `Web`
  - [Web Services Reference Documentation](https://docs.spring.io/spring-ws/docs/3.0.10.RELEASE/reference/)
- ✅ Spring Data `JPA`
  - [Data JPA - Reference Documentation](https://bit.ly/3cT5AQl)
  - [keywords supported for JPA](https://bit.ly/396AlAd)
- ✅ `Hibernate` Validation
  - [Validation Reference](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/)
- ✅ Spring `Security`
  - [Security Reference](https://docs.spring.io/spring-security/site/docs/5.4.5/reference/html5/)
  - Authentication
  - Authorization
- ✅ Tokenization using `JWT`
  - [jjwt](https://github.com/jwtk/jjwt)
  - [jwt.io](https://jwt.io/)
- ✅ Project `Lombok`
  - [Lombok features](https://projectlombok.org/features/all)
- ✅ `MySQL` DB
- ✅ `Postman` http client
- ...
- ...

### What has been implemented (see commits for more details)

- User sign-up (`POST /users` providing UserDetail)
- User sign-in (`POST /users` providing email and password)
- **_Stateless_** web-service (no token caching)
- Get single user by public user id  (`GET /users/{userId}` providing public
  user id)
- Provide option to process and produce both `JSON` and `XML` values