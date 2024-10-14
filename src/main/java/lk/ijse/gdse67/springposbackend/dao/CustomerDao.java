package lk.ijse.gdse67.springposbackend.dao;

import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerDao extends JpaRepository<Customer, String> {
    @Query("SELECT c.availability FROM Customer c WHERE c.propertyId = :propertyId")
    Boolean isAvailable(@Param("propertyId") String propertyId);
}
