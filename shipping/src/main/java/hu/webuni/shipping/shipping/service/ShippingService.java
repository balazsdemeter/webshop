package hu.webuni.shipping.shipping.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShippingService {
    private final Random random = new Random();

    public long orderShipment() {

        // TODO: orderstatus
        return random.nextLong();
    }
}