package lk.ijse.gdse67.springposbackend.dao;

import lk.ijse.gdse67.springposbackend.entity.impl.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDao extends JpaRepository<Item,String> {
}
