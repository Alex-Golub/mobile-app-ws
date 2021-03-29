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

### Tools and Projects used (see commits for detailed progress):

- ✅ - [Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/2.4.4/reference/html/)
- ✅ [Web Services Reference Documentation](https://docs.spring.io/spring-ws/docs/3.0.10.RELEASE/reference/)
- ✅ Data `JPA`
  - [Data JPA - Reference Documentation](https://bit.ly/3cT5AQl)
  - [keywords supported for JPA](https://bit.ly/396AlAd)
  - CRUD
  - Pagination
  - [JPA - Entity Relationships](https://www.tutorialspoint.com/jpa/jpa_entity_relationships.htm)
- ✅ [Hibernate Validation Reference](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/)
- ✅ [Security Reference](https://docs.spring.io/spring-security/site/docs/5.4.5/reference/html5/)
  - Authentication
  - Authorization
- ✅ `JWT`
  - [jjwt](https://github.com/jwtk/jjwt)
  - [jwt.io](https://jwt.io/)
- ✅ [Project Lombok](https://projectlombok.org/features/all)
- ✅ [MySQL DB](https://dev.mysql.com/doc/refman/8.0/en/)
- ✅ [Postman http client](https://learning.postman.com/docs/getting-started/introduction/)
- ✅ Run Spring-Boot project from command-line(`mvn` commands)
- ✅ Extract `.war` file from spring project and deploy
  to [Tomcat](https://tomcat.apache.org/whichversion.html) server
- ✅ [ModelMapper](http://modelmapper.org/) - Replace `BeanUtils` shallow mapping
  with deeper level mapping between objects that can contain Collection fields.
- ✅ [HATEOS](https://docs.spring.io/spring-hateoas/docs/current/reference/html/#reference) - response can contain other links related to the current returned resource.
  Let clients use those resources without manually type them in.
    - extending `RepresentationModel<T>` - adding links to specific class
    - `EntityModel<T>` - using the `WebMvcLinkBuilder` to add addresses with relation
    to this class
    - `CollectionModel<T>` - ability to add multiple links into a collection