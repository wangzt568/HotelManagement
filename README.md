# HotelManagement
A hotel management system (HMS) is software that facilitates a hotel’s reservation management and administrative tasks. The most important functions include front-desk operations, reservations, channel management, housekeeping, rate and occupancy management, and payment processing. Although PMS software mostly controls reservation and financial transactions, it may allow you to manage housekeeping and perform human resources management as well. In general, PMS facilitates the main processes in a hotel related to internal and external operations.

Hotel Hotel Management System (PMS): Functions, Modules & Integrations

This Project is based on microservices structure.Which is seperated in different services.

                                                                                  1. Booking-Client

We built booking-client as a central reservation module.Which can connect to an front-end web ui based on Angular 8.It contains the detail of the room in a hotel for customers to choose.

public class RoomDetail {

  private final String id;
  private final String name;
  private final Type type;

  public static enum Type {
    SINGLE, DOUBLE, QUEEN, SUITE, STUDIO
  }

}

And I used feign as a tool to implement microservice structure and build RESTful API.I used REST template.And here is the configureation.
public class RestTemplateConfig {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
                                                                            2. Boot-admin

We also build a admin server for the start up to management of all the data.
Used @EnableAdminServer annotations

                                                                            3. Config-server 
This part is used to do the configuration for the server.
Used @EnableConfigServer.

                                                                            4. Hystrix dashboard

It's the circuit breaker for maintaining the stability of the whole system.

                                                                            5. Management System

It's the service to implement the backoffice module.


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

Use this class to contain basic information of a booking.

                                                                  6. Revenue-service

This service is for collecting revenue of every day/week.

                                                                  7. Service Registry 

This service is to implement SpringCloud Eureka for service dicovery.
This is the config:

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

                                                                        8. Turbine Service

                              This is a type of dashboard for the hystrix.


