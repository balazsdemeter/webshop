package hu.webuni.order.repository;

import hu.webuni.order.model.ChartItem;
import hu.webuni.order.model.ShopOrder;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChartItemRepository extends JpaRepository<ChartItem, Long> {
    @EntityGraph(attributePaths = {"product"})
    @Query("SELECT c from ChartItem c where c.order in :orders")
    List<ChartItem> findChartItemByOrderWithProduct(List<ShopOrder> orders);
}