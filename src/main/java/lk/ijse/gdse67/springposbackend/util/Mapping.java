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
import java.util.stream.Collectors;

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
    public List<CustomerDto> mapToCustomerDtoList(List<Customer> customers) {
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }


    // Item Related Mappings

    public ItemDto mapToItemDto(Item item){
        return modelMapper.map(item,ItemDto.class);
    }
    public Item mapToItem(ItemDto itemDto){
        return modelMapper.map(itemDto,Item.class);
    }
    public List<ItemDto> mapToItemDtoList(List<Item> items){
        return items.stream()
                .map(item -> modelMapper.map(item, ItemDto.class))
                .collect(Collectors.toList());
    }

    // Order related Mappings
    public OrderItem mapToOrderItem(OrderItemDto orderItemDto){
        return modelMapper.map(orderItemDto,OrderItem.class);
    }

    public PlaceOrder mapToPlaceOrder(PlaceOrderDto placeOrderDto){
        return modelMapper.map(placeOrderDto, PlaceOrder.class);
    }

    public PlaceOrderDto mapToPlaceOrderDto(PlaceOrder placeOrder,List<OrderItemDto> orderItemDtos){
        return PlaceOrderDto.builder()
                .orderId(placeOrder.getOrderId())
                .customerId(placeOrder.getCustomer().getPropertyId())
                .orderDate(placeOrder.getOrderDate())
                .paid(placeOrder.getPaid())
                .discount(placeOrder.getDiscount())
                .balance(placeOrder.getBalance())
                .orderItems(orderItemDtos)
                .build();
    }

    public OrderItemDto mapToOrderItemDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .orderId(orderItem.getPropertyId().getOrderId())
                .itemId(orderItem.getPropertyId().getItemId())
                .itemCount(orderItem.getItemCount())
                .unitPrice(orderItem.getUnitPrice())
                .total(orderItem.getTotal())
                .build();
    }
}
