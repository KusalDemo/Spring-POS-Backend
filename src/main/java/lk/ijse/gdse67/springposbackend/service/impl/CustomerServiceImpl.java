package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.customStatusCodes.SelectedCustomerStatus;
import lk.ijse.gdse67.springposbackend.dao.CustomerDao;
import lk.ijse.gdse67.springposbackend.dto.CustomerStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.CustomerDto;
import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import lk.ijse.gdse67.springposbackend.exception.CustomerNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.DataPersistException;
import lk.ijse.gdse67.springposbackend.service.CustomerService;
import lk.ijse.gdse67.springposbackend.util.AppUtil;
import lk.ijse.gdse67.springposbackend.util.Mapping;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private Mapping mapper;

    @Override
    public void addCustomer(CustomerDto customerDto){
        customerDto.setPropertyId(AppUtil.generateCustomerId());
        Customer savedNote = customerDao.save(mapper.mapToCustomer(customerDto));
        if(savedNote == null){
            throw new DataPersistException("Failed to add customer");
        }
    }

    @Override
    public void updateCustomer(String propertyId, CustomerDto customerDto) {
        try{
            Customer customer = customerDao.getReferenceById(propertyId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found");
            }
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setAvailability(customerDto.isAvailability());
            customerDao.save(customer);
        }catch (DataPersistException e){
            throw new DataPersistException("Failed to update customer");
        }

    }

    @Override
    public void deleteCustomer(String propertyId) {
        Customer customer = customerDao.getReferenceById(propertyId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        customerDao.delete(customer);
    }

    @Override
    public CustomerStatus getCustomer(String propertyId) {
        Customer fetchedCustomer = customerDao.getReferenceById(propertyId);
        if (fetchedCustomer == null) {
            return new SelectedCustomerStatus(2, "Customer not found");
        }
        return mapper.mapToCustomerDto(fetchedCustomer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return mapper.mapToCustomerDtoList(customerDao.findAll());
    }
}
