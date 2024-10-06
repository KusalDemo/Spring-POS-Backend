package lk.ijse.gdse67.springposbackend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDto {
    private String propertyId;
    private String name;
    private String email;
    private String address;
    private String branch;
}
