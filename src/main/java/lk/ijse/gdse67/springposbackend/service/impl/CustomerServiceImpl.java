package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.controller.CustomerController;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    static Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @Override
    public void addCustomer(CustomerDto customerDto){
        customerDto.setPropertyId(AppUtil.generateCustomerId());
        Customer savedNote = customerDao.save(mapper.mapToCustomer(customerDto));
        if(savedNote == null){
            logger.error("Failed to add customer , Data Persist Exception occurred");
            throw new DataPersistException("Failed to add customer");
        }
    }

    @Override
    public void updateCustomer(String propertyId, CustomerDto customerDto) {
        try{
            Customer customer = customerDao.getReferenceById(propertyId);
            if (customer == null) {
                logger.error("Customer not in database , Customer not found exception occurred (Searching failed to retrieve :"+propertyId+")");
                throw new CustomerNotFoundException("Customer not found");
            }
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setAvailability(customerDto.isAvailability());
            customerDao.save(customer);
        }catch (DataPersistException e){
            logger.error("Failed to update customer , Data Persist Exception occurred");
            throw new DataPersistException("Failed to update customer");
        }
    }

    @Override
    public void deleteCustomer(String propertyId) {
        Customer customer = customerDao.getReferenceById(propertyId);
        if (customer == null) {
            logger.error("Customer not in database , Customer not found exception occurred (Searching failed to retrieve :"+propertyId+")");
            throw new CustomerNotFoundException("Customer not found");
        }
        customerDao.delete(customer);
    }

    @Override
    public CustomerStatus getCustomer(String propertyId) {
        Customer fetchedCustomer = customerDao.getReferenceById(propertyId);
        if (fetchedCustomer == null) {
            logger.error("Customer not in database , Customer not found exception occurred (Searching failed to retrieve :"+propertyId+")");
            return new SelectedCustomerStatus(2, "Customer not found");
        }
        return mapper.mapToCustomerDto(fetchedCustomer);
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        return mapper.mapToCustomerDtoList(customerDao.findAll());
    }
}
