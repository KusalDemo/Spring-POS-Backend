package lk.ijse.gdse67.springposbackend.customStatusCodes;

import lk.ijse.gdse67.springposbackend.dto.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedCustomerStatus implements CustomerStatus {
    private int statusCode;
    private String message;
}
