package lk.ijse.gdse67.springposbackend.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceOrderDto {
    private String propertyId;
    private String customerId;
    private Date orderDate;
    private double paid;
    private int discount;
    private double balance;
    List<OrderItemDto> orderItems;
}
