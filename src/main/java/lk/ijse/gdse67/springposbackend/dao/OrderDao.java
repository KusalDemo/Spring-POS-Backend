package lk.ijse.gdse67.springposbackend.dao;

import lk.ijse.gdse67.springposbackend.entity.impl.PlaceOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<PlaceOrder,String> {
}
