package lk.ijse.gdse67.springposbackend.customStatusCodes;

import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedOrderStatus implements OrderStatus {
    private int statusCode;
    private String message;
}
