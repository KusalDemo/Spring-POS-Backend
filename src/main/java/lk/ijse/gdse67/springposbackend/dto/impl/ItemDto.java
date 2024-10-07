package lk.ijse.gdse67.springposbackend.dto.impl;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
    private String propertyId;
    private String name;
    private String description;
    private double price;
    private int qty;
}
