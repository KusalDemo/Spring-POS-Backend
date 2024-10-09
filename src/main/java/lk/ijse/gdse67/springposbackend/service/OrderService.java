package lk.ijse.gdse67.springposbackend.service;


import lk.ijse.gdse67.springposbackend.dto.impl.OrderItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;

public interface OrderService {
    void addOrder(PlaceOrderDto placeOrderDto);
}
