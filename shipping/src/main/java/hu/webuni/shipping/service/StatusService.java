package hu.webuni.shipping.service;

import hu.webuni.jms.model.Message;
import hu.webuni.jms.model.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final JmsTemplate jmsTemplate;
    private final Random random = new Random();

    @Async
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