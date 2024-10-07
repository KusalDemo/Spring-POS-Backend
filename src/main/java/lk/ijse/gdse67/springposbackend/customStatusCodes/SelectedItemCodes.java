package lk.ijse.gdse67.springposbackend.customStatusCodes;

import lk.ijse.gdse67.springposbackend.dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SelectedItemCodes implements ItemStatus {
    private int statusCode;
    private String message;
}
