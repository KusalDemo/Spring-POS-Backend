package lk.ijse.gdse67.springposbackend.service;

import lk.ijse.gdse67.springposbackend.dto.CustomerStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.CustomerDto;

import java.util.List;

public interface CustomerService {
    void addCustomer(CustomerDto customerDto);
    void updateCustomer(String propertyId, CustomerDto customerDto);
    void deleteCustomer(String propertyId);
    CustomerStatus getCustomer(String propertyId);
    List<CustomerDto> getAllCustomers();
}
