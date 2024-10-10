package lk.ijse.gdse67.springposbackend.service;


import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.OrderItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.entity.impl.PlaceOrder;

import java.util.List;

public interface OrderService {
    void addOrder(PlaceOrderDto placeOrderDto);
    List<PlaceOrderDto> getAllOrders();
    OrderStatus getOrder(String orderId);
}
