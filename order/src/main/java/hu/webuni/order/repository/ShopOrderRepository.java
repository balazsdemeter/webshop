package hu.webuni.order.repository;

import hu.webuni.order.model.ShopOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Long> {
    @EntityGraph(attributePaths = {"address"})
    @Query("SELECT o FROM shop_order o where o.name=:name")
    Optional<ShopOrder> findOrderByNameWithAddress(String name);

    @EntityGraph(attributePaths = {"chart"})
    @Query("SELECT o FROM shop_order o where o.id=:id")
    ShopOrder findOrderByIdWithChart(long id);

    Optional<ShopOrder> findOrderByShipmentId(long shipmentId);
}