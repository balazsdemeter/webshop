package hu.webuni.shipping.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class ShippingService {
    private final Random random = new Random();
    private final StatusService statusService;

    public long orderShipment() {
        long shipmentId = random.nextLong(0, 19);
        statusService.sendShipmentStatus(shipmentId);
        return shipmentId;
    }
}