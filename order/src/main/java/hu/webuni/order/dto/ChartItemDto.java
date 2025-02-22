package hu.webuni.order.dto;

import lombok.Data;

@Data
public class ChartItemDto {
    private ProductDto product;
    private int amount;
}