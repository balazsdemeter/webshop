package hu.webuni.shipping.xmlws;

import hu.webuni.shipping.model.ShipmentRequest;
import hu.webuni.shipping.model.ShipmentResponse;
import hu.webuni.shipping.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingServiceXmlWsImpl implements ShippingServiceXmlWs {
    private final ShippingService service;
    @Override
    public ShipmentResponse orderShipment(ShipmentRequest request) {
        ShipmentResponse shipmentResponse = new ShipmentResponse();
        shipmentResponse.setShipmentId(service.orderShipment());
        return shipmentResponse;
    }
}