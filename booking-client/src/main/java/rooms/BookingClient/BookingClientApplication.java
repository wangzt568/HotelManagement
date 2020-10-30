package tacos.ingredientclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableHystrix
public class BookingClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingClientApplication.class, args);
	}
}
