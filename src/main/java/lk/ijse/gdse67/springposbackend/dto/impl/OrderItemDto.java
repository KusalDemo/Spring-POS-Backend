package lk.ijse.gdse67.springposbackend.dto.impl;


import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lk.ijse.gdse67.springposbackend.dto.SuperDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto implements SuperDto, OrderStatus {
    private String orderId;
    private String itemId;
    private int itemCount;
    private double unitPrice;
    private double total;
}
