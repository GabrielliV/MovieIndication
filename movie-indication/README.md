# Getting Started

This API aims to read the CVS file with the list of nominees and winners in the “Worst Movie” category at the Golden Raspberry Awards. 
After inserting the file in the database, it is possible to obtain the producer with the longest interval between two consecutive awards, and the one who obtained two awards faster.

#### Development
- Run MovieIndication

#### Database
- To create the database, it will be necessary to upload the application, access the URL http://localhost:8080/h2 and inform the bank's path exactly as defined in the application.properties. 
If you want to create in another folder, just change the destination both in the spring.datasource.url propertie and in the database configuration.

#### Get the winners
- In order to obtain the producer with the longest interval between two consecutive awards, and the one who obtained two awards faster, it will be necessary to make a request for the URL http://localhost:8080/movie/winners GET type product.

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Web Starter](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/{bootVersion}/reference/htmlsingle/#production-ready)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)