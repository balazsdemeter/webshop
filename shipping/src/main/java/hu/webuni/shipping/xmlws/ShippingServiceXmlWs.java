package hu.webuni.shipping.xmlws;

import hu.webuni.shipping.model.ShipmentRequest;
import hu.webuni.shipping.model.ShipmentResponse;
import jakarta.jws.WebService;

@WebService
public interface ShippingServiceXmlWs {
    ShipmentResponse orderShipment(ShipmentRequest request);
}