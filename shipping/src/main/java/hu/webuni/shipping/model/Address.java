package hu.webuni.shipping.model;

import lombok.Data;

@Data
public class Address {
    private long id;
    private Integer zipCode;
    private String city;
    private String street;
    private String houseNumber;
}