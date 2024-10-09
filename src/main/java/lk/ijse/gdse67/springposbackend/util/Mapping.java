package lk.ijse.gdse67.springposbackend.util;

import lk.ijse.gdse67.springposbackend.dto.impl.CustomerDto;
import lk.ijse.gdse67.springposbackend.dto.impl.ItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.OrderItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import lk.ijse.gdse67.springposbackend.entity.impl.Item;
import lk.ijse.gdse67.springposbackend.entity.impl.OrderItem;
import lk.ijse.gdse67.springposbackend.entity.impl.PlaceOrder;
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

    // Order related Mappings

    public OrderItemDto mapToOrderItemDto(OrderItem orderItem){
        return modelMapper.map(orderItem,OrderItemDto.class);
    }
    public OrderItem mapToOrderItem(OrderItemDto orderItemDto){
        return modelMapper.map(orderItemDto,OrderItem.class);
    }
    public List<OrderItemDto> mapToOrderItemDtoList(List<OrderItem> orderItems){
        return modelMapper.map(orderItems, List.class);
    }

    public List<OrderItem> mapToOrderItemList(List<OrderItemDto> orderItemDtos){
        return modelMapper.map(orderItemDtos, List.class);
    }
    public PlaceOrderDto mapToPlaceOrderDto(PlaceOrder placeOrder){
        return modelMapper.map(placeOrder, PlaceOrderDto.class);
    }
    public PlaceOrder mapToPlaceOrder(PlaceOrderDto placeOrderDto){
        return modelMapper.map(placeOrderDto, PlaceOrder.class);
    }
}
