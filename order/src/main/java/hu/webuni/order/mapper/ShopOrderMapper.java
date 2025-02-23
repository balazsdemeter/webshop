package hu.webuni.order.mapper;

import hu.webuni.order.dto.OrderDto;
import hu.webuni.order.model.ShopOrder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ShopOrderMapper {
    OrderDto shopOrderToDto(ShopOrder order);
    ShopOrder dtoToShopOrder(OrderDto orderDto);

    List<OrderDto> shopOrdersToDtos(List<ShopOrder> shopOrders);
}