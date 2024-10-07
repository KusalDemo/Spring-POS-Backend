package lk.ijse.gdse67.springposbackend.dto.impl;

import lk.ijse.gdse67.springposbackend.dto.ItemStatus;
import lk.ijse.gdse67.springposbackend.dto.SuperDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto implements SuperDto, ItemStatus {
    private String propertyId;
    private String name;
    private String description;
    private double price;
    private int qty;
}
