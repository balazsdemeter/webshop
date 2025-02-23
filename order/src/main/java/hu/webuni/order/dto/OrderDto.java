package hu.webuni.order.dto;

import hu.webuni.order.enums.OrderStatus;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private long id;
    private String name;
    private AddressDto address;
    private List<ChartItemDto> chart;
    private long shipmentId;
    private OrderStatus status;
}