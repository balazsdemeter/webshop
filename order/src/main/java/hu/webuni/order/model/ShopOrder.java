package hu.webuni.order.model;

import hu.webuni.order.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "shop_order")
@Builder
public class ShopOrder {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String username;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<ChartItem> chart;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @Enumerated(value= EnumType.STRING)
    private OrderStatus status;

    private long shipmentId;
}