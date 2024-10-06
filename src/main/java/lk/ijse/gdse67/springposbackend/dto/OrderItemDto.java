package lk.ijse.gdse67.springposbackend.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private String orderId;
    private String itemId;
    private int itemCount;
    private double unitPrice;
    private double total;
}
