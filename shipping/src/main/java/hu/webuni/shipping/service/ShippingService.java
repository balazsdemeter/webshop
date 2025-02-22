package hu.webuni.shipping.service;

import hu.webuni.jms.model.Message;
import hu.webuni.jms.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final JmsTemplate jmsTemplate;
    private final Random random = new Random();

    public long orderShipment() {
        long shipmentId = random.nextLong(0, 19);
        CompletableFuture.runAsync(() -> sendShipmentStatus(shipmentId));
        return shipmentId;
    }

    public void sendShipmentStatus(long shipmentId) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        OrderStatus[] values = OrderStatus.values();
        jmsTemplate.convertAndSend("orderstatus", new Message(shipmentId, values[random.nextInt(values.length)].name()));
    }
}