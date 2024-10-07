package lk.ijse.gdse67.springposbackend.dto.impl;

import lk.ijse.gdse67.springposbackend.dto.CustomerStatus;
import lk.ijse.gdse67.springposbackend.dto.SuperDto;
import lk.ijse.gdse67.springposbackend.entity.impl.PlaceOrder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto implements CustomerStatus , SuperDto {
    private String propertyId;
    private String name;
    private String email;
    private String address;
    private boolean availability;
}
