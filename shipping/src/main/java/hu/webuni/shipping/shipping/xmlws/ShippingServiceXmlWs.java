package hu.webuni.shipping.shipping.xmlws;

import hu.webuni.shipping.shipping.model.ShipmentRequest;
import hu.webuni.shipping.shipping.model.ShipmentResponse;
import jakarta.jws.WebService;

@WebService
public interface ShippingServiceXmlWs {
    ShipmentResponse orderShipment(ShipmentRequest request);
}