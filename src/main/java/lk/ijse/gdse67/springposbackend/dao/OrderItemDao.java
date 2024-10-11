package lk.ijse.gdse67.springposbackend.dao;

import lk.ijse.gdse67.springposbackend.entity.impl.OrderItem;
import lk.ijse.gdse67.springposbackend.entity.impl.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDao extends JpaRepository<OrderItem, OrderItemId> {
}
