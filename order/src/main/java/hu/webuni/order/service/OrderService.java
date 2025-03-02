package hu.webuni.order.service;

import hu.webuni.jms.model.Message;
import hu.webuni.order.dto.OrderDto;
import hu.webuni.order.enums.OrderStatus;
import hu.webuni.order.mapper.ShopOrderMapper;
import hu.webuni.order.model.ChartItem;
import hu.webuni.order.model.ShopOrder;
import hu.webuni.order.repository.ChartItemRepository;
import hu.webuni.order.repository.ShopOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ShopOrderRepository orderRepository;
    private final ChartItemRepository chartItemRepository;
    private final ShopOrderMapper orderMapper;
    private final CallShippingServiceWs callShippingServiceWs;

    @Transactional
    public OrderDto registerOrder(OrderDto orderDto) {
        ShopOrder order = orderMapper.dtoToShopOrder(orderDto);
        for (ChartItem chartItem : order.getChart()) {
            chartItem.setOrder(order);
            chartItem.getProduct().setChart(Collections.singletonList(chartItem));
        }
        order.setUsername(getUser().getUsername());
        order.setStatus(OrderStatus.PENDING);
        order = orderRepository.save(order);
        return orderMapper.shopOrderToDto(order);
    }

    @Transactional
    public List<OrderDto> findByUsername(String username) {
        List<ShopOrder> orders = orderRepository.findOrderByNameWithAddress(username);
        if (orders.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        orders = orderRepository.findOrderByIdsWithChart(orders.stream().map(ShopOrder::getId).toList());
        chartItemRepository.findChartItemByOrderWithProduct(orders);
        return orderMapper.shopOrdersToDtos(orders);
    }

    @Transactional
    public Long confirmedOrDeclined(Long id, OrderStatus status) {
        ShopOrder order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        order.setStatus(status);
        Long shipmentId = null;
        if (OrderStatus.CONFIRMED == status) {
            shipmentId = callShippingServiceWs.addOrderToShippingService(order);
            order.setShipmentId(shipmentId);
        }
        orderRepository.save(order);
        return shipmentId;
    }

    @JmsListener(destination = "orderstatus", containerFactory = "jmsFactory")
    public void onPaymentMessage(Message message) {
        orderRepository.findOrderByShipmentId(message.getShipmentId()).ifPresent(order -> {
            order.setStatus(OrderStatus.valueOf(message.getStatus()));
            orderRepository.save(order);
        });
    }

    private User getUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}