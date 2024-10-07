package lk.ijse.gdse67.springposbackend.dao;

import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDao extends JpaRepository<Customer, String> {
}
