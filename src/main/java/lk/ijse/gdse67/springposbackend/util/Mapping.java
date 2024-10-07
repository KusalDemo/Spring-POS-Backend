package lk.ijse.gdse67.springposbackend.util;

import lk.ijse.gdse67.springposbackend.dto.impl.CustomerDto;
import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    public CustomerDto mapToCustomerDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
    public Customer mapToCustomer(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }
    public List<CustomerDto> mapToCustomerDtoList(List<Customer> customers){
        return modelMapper.map(customers, List.class);
    }
}
