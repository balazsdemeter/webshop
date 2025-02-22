package hu.webuni.order.service;

import hu.webuni.order.mapper.AddressMapper;
import hu.webuni.order.mapper.ChartItemMapper;
import hu.webuni.order.model.Address;
import hu.webuni.order.model.ShopOrder;
import hu.webuni.shipping.wsclient.Item;
import hu.webuni.shipping.wsclient.ShipmentRequest;
import hu.webuni.shipping.wsclient.ShipmentResponse;
import hu.webuni.shipping.wsclient.ShippingServiceXmlWs;
import hu.webuni.shipping.wsclient.ShippingServiceXmlWsImplService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CallShippingServiceWs {
    private final AddressMapper addressMapper;
    private final ChartItemMapper chartMapper;

    public long addOrderToShippingService(ShopOrder order) {
        ShippingServiceXmlWs port = new ShippingServiceXmlWsImplService().getShippingServiceXmlWsImplPort();
        ShipmentResponse response = port.orderShipment(mapRequest(order));
        return response.getShipmentId();
    }

    private ShipmentRequest mapRequest(ShopOrder order) {
        ShipmentRequest request = new ShipmentRequest();
        request.setShipFrom(addressMapper.addressToWsClientAddress(getDefaultAddress()));
        request.setShipTo(addressMapper.addressToWsClientAddress(order.getAddress()));

        List<Item> items = new ArrayList<>();
        order.getChart().forEach(chartItem -> {
            Item item = new Item();
            item.setAmount(chartItem.getAmount());
            item.setName(chartItem.getProduct().getName());
            items.add(item);
        });

        request.getItems().addAll(items);
        return request;
    }

    private Address getDefaultAddress() {
        Address address = new Address();
        address.setZipCode(1111);
        address.setCity("Budapest");
        address.setStreet("Teszt utca");
        address.setHouseNumber("23");
        return address;
    }
}
