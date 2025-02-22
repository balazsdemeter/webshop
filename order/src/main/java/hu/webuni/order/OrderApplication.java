package hu.webuni.order;

import hu.webuni.order.enums.OrderStatus;
import hu.webuni.order.model.Address;
import hu.webuni.order.model.ChartItem;
import hu.webuni.order.model.ShopOrder;
import hu.webuni.order.model.Product;
import hu.webuni.order.repository.ShopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class OrderApplication implements CommandLineRunner  {
    private final ShopOrderRepository orderRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ShopOrder order = new ShopOrder();
        order.setName("teszt elek");
        order.setStatus(OrderStatus.PENDING);

        Address address = new Address();
        address.setZipCode(2045);
        address.setCity("Törökbálint");
        address.setStreet("valami utca");
        address.setHouseNumber("4");
        address.setOrder(order);
        order.setAddress(address);

        Product milk = new Product();
        milk.setName("tej");
        milk.setPrice(200);

        Product bread = new Product();
        bread.setName("kenyér");
        bread.setPrice(1000);

        List<ChartItem> chart = new ArrayList<>();
        ChartItem chartItem1 = new ChartItem();
        chartItem1.setOrder(order);
        chartItem1.setProduct(milk);
        chartItem1.setAmount(2);
        chart.add(chartItem1);
        if (milk.getChart() == null) {
            milk.setChart(Collections.singletonList(chartItem1));
        } else {
            milk.getChart().add(chartItem1);
        }

        ChartItem chartItem2 = new ChartItem();
        chartItem2.setOrder(order);
        chartItem2.setProduct(bread);
        chartItem2.setAmount(1);
        chart.add(chartItem2);
        if (bread.getChart() == null) {
            bread.setChart(Collections.singletonList(chartItem2));
        } else {
            bread.getChart().add(chartItem2);
        }

        order.setChart(chart);

//        orderRepository.save(order);
    }
}
