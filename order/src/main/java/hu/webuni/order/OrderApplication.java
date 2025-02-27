package hu.webuni.order;

import hu.webuni.tokenlib.tokenlib.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {JwtService.class, OrderApplication.class})
@RequiredArgsConstructor
public class OrderApplication  {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

}
