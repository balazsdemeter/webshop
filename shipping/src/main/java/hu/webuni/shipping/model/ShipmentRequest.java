package hu.webuni.shipping.model;

import lombok.Data;

import java.util.List;

@Data
public class ShipmentRequest {
    private Address shipFrom;
    private Address shipTo;
    private List<Item> items;
}