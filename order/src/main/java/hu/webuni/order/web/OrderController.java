package hu.webuni.order.web;

import hu.webuni.order.dto.OrderDto;
import hu.webuni.order.enums.OrderStatus;
import hu.webuni.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class OrderController {
    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderDto> registerOrder(@RequestBody OrderDto orderDto) {
        OrderDto registeredOrder = service.registerOrder(orderDto);
        return ResponseEntity.ok(registeredOrder);
    }

    @GetMapping("/findByUsername")
    public ResponseEntity<List<OrderDto>> findByUsername(@RequestParam String username) {
        return ResponseEntity.ok(service.findByUsername(username));
    }

    @PutMapping("/{id}/confirmedOrDeclined")
    public Long confirmedOrDeclined(@PathVariable Long id, @RequestParam String status) {
        return service.confirmedOrDeclined(id, OrderStatus.valueOf(status));
    }
}
