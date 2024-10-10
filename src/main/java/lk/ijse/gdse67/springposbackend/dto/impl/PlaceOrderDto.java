package lk.ijse.gdse67.springposbackend.dto.impl;

import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lk.ijse.gdse67.springposbackend.dto.SuperDto;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderDto implements OrderStatus , SuperDto {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private double paid;
    private int discount;
    private double balance;
    List<OrderItemDto> orderItems;
}
