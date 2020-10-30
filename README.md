# HotelManagement
A hotel management system (HMS) is software that facilitates a hotel’s reservation management and administrative tasks. The most important functions include front-desk operations, reservations, channel management, housekeeping, rate and occupancy management, and payment processing. Although PMS software mostly controls reservation and financial transactions, it may allow you to manage housekeeping and perform human resources management as well. In general, PMS facilitates the main processes in a hotel related to internal and external operations.

Hotel Hotel Management System (PMS): Functions, Modules & Integrations

This Project is based on microservices structure.Which is seperated in different services.

##     1. Booking-Client

We built booking-client as a central reservation module.Which can connect to an front-end web ui based on Angular 8.It contains the detail of the room in a hotel for customers to choose.

```java
public class RoomDetail {

  private final String id;
  private final String name;
  private final Type type;

  public static enum Type {
    SINGLE, DOUBLE, QUEEN, SUITE, STUDIO
  }

}
```

And I used feign as a tool to implement microservice structure and build RESTful API.I used REST template.And here is the configureation.

```java
public class RestTemplateConfig {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
```

##                                 2. Boot-admin                                 

We also build a admin server for the start up to management of all the data.
Used @EnableAdminServer annotations

## 3. Config-server 

This part is used to do the configuration  and security for the server.
Used @EnableConfigServer.

```yml
server:
  port: 9090

spring:
  security:
    user:
      name: admin
      password: 53cr3t
```

The logic of Oauth2 is like the picture:

![Abstract Protocol Flow](https://assets.digitalocean.com/articles/oauth/abstract_flow.png)

##  4. Hystrix dashboard

It's the circuit breaker for maintaining the stability of the whole system.

##   5. Management System

It's the service to implement the backoffice module.

```
@Data
@Document
@RestResource(rel="Hotel", path="Hotel")
public class Hotel {

  @Id
  private String id;

  private String name;

  private Date createdAt = new Date();

  private List<Room> rooms;

}
```

Use this class to contain basic information of a booking.

##                                6. Revenue-service                              

This service is for collecting revenue of every day/week.

##                       7. Service Registry                    

This service is to implement SpringCloud Eureka for service dicovery.
This is the config:

```yml
spring:
  profiles: eureka-1
  application:
    name: eureka-1

server:
  port: 8761

eureka:
  instance:
    hostname: eureka1.hotel.com

other:
  eureka:
    host: localhost
    port: 8762

---

spring:
  profiles: eureka-2
  application:
    name: eureka-2

server:
  port: 8762

eureka:
  instance:
    hostname: eureka1.hotel.com

other:
  eureka:
    host: localhost
    port: 8762
```



##   8. Turbine Service

**Step 1:** Add the Spring-Cloud-Starter-hystrix:

```
&lt;dependency&gt;
```

```
&lt;groupId&gt;org.springframework.cloud&lt;/groupId&gt;
```

```
&lt;artifactId&gt;spring-cloud-starter-hystrix&lt;/artifactId&gt;
```

```
&lt;/dependency&gt;
```

**Step 2:** Enable Hystrix support for the Application, this will expose the hystrix stream at a "/hystrix.stream" uri:

```
@EnableHystrix
```

Now for the Hystrix Dashboard application to graphically view the Hystrix stream, the following annotation will enable that and the application should be available at "/hystrix" uri:

```

@SpringBootApplication
@EnableHystrixDashboard
public class AggregateApp {
  public static void main(String[] args) {
SpringApplication.run(AggregateApp.class, args);
}
}
```

## **Spring Cloud With Turbine**

Hystrix stream provides information on a single application, Turbine provides a way to aggregate this information across all installations of an application in a cluster. Integrating turbine into a Spring-Cloud based application is straightforward, all it requires is information on which clusters to expose information on and how to aggregate information about the specific clusters. As before to pull in the dependencies of Turbine:

1

```
&lt;dependency&gt;
&lt;groupId&gt;org.springframework.cloud&lt;/groupId&gt;
&lt;artifactId&gt;spring-cloud-starter-turbine&lt;/artifactId&gt;
&lt;exclusions&gt;
&lt;exclusion&gt;
&lt;groupId&gt;javax.servlet&lt;/groupId&gt;
&lt;artifactId&gt;servlet-api&lt;/artifactId&gt;
&lt;/exclusion&gt;
&lt;/exclusions&gt;
```



And to enable Turbine support in a Spring Boot based application:

```
@SpringBootApplication
```

```
@EnableHystrixDashboard
```

```
@EnableTurbine
```

```
public class MonitorApplication {
```

```
  public static void main(String[] args) {
```

```
SpringApplication.run(MonitorApplication.class, args)
```

This application is playing the role of both showing the Hystrix Dashboard and exposing turbine stream. Finally the configuration for turbine:

```
turbine:
```

```
aggregator:
```

```
clusterConfig: SAMPLE-HYSTRIX-AGGREGATE
```

```
appConfig: SAMPLE-HYSTRIX-AGGREGATE
```

Given this configuration a turbine stream for SAMPLE-HYSTRIX-AGGREGATE cluster is available at "/turbine.stream?cluster=SAMPLE-HYSTRIX-AGGREGATE" uri, it would figure out the instances of the cluster using Eureka, source the Hystrix stream from each instance and aggregate it into the Turbine stream. If we were to view the Hystrix dashboard against this stream:

[![img](http://2.bp.blogspot.com/-DtsvBUgWdgQ/Vpribug_HdI/AAAAAAAAWV4/cMKfUt0rMjY/s640/HystrixDashboard2.png)](http://2.bp.blogspot.com/-DtsvBUgWdgQ/Vpribug_HdI/AAAAAAAAWV4/cMKfUt0rMjY/s1600/HystrixDashboard2.png)

## Further Understanding

*A general structure of hotel management system*

Keep in mind that it is hard to divide the functions of PMS into more and less important because all of them are necessary. However, regardless of a hotel type, hotel hotel management systems must have a reservation system with a website booking engine and front-desk operations module. Other essential modules usually include channel management, revenue management, housekeeping, customer data management, report, and analytics. And big hotels or resorts certainly need point-of-sale (POS) services and back-office modules.

Reservation

For a modern hotel business, online bookings are in most cases the main sales channel. The reservation module, which helps manage online bookings, effectively becomes indispensable to a hotel management system. A central reservation system (CRS) or any other reservation platform may be available as a separate module of PMS or implemented as a hotel’s separate internal solution.

A hotel reservation system holds all inventory data and dates, sending this information to the front desk. The reservation system must be integrated with the website booking engine and other distribution channels. Chain hotels usually have one central reservation system for all properties, while independent hotels have their own reservation systems. If a hotel or a hotel chain already uses a particular reservation software, PMS must offer integration with the existing service.

Key functions of the reservation module include:

- **Room bookings.** The system checks room availability and status, shows free rooms across different channels and the website booking engine. This function monitors double bookings and allows group reservations. Then it schedules bookings and displays information about current and upcoming bookings on a dashboard.
- **Collection of e-payments**, and identification of types and categories of payments that are processed via this module.
- **Management of room inventory and allocation** that prevents overbookings and duplication of bookings. In some software, this function is part of a channel management module.
- **Reservation emails**. The system sends confirmations to guests after they complete booking. In some PMSs, this function is a part of the front-desk operations module.
- **Activities booking**. Some software allows guests to book not only accommodation but also activities with this system.