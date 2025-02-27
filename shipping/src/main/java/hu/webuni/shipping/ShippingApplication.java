package hu.webuni.shipping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ShippingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShippingApplication.class, args);
    }

}
