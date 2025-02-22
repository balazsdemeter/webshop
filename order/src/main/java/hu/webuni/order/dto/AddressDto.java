package hu.webuni.order.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Integer zipCode;
    private String city;
    private String street;
    private String houseNumber;
}
