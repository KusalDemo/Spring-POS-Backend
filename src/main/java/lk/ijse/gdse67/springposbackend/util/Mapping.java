package lk.ijse.gdse67.springposbackend.util;

import lk.ijse.gdse67.springposbackend.dto.impl.CustomerDto;
import lk.ijse.gdse67.springposbackend.dto.impl.ItemDto;
import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import lk.ijse.gdse67.springposbackend.entity.impl.Item;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper modelMapper;

    // Customer Related Mappings

    public CustomerDto mapToCustomerDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }
    public Customer mapToCustomer(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }
    public List<CustomerDto> mapToCustomerDtoList(List<Customer> customers){
        return modelMapper.map(customers, List.class);
    }

    // Item Related Mappings

    public ItemDto mapToItemDto(Item item){
        return modelMapper.map(item,ItemDto.class);
    }
    public Item mapToItem(ItemDto itemDto){
        return modelMapper.map(itemDto,Item.class);
    }
    public List<ItemDto> mapToItemDtoList(List<Item> items){
        return modelMapper.map(items, List.class);
    }
}
