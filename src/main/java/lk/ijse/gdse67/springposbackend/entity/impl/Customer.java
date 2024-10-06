package lk.ijse.gdse67.springposbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse67.springposbackend.entity.SuperEntity;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer implements SuperEntity {
    @Id
    @Column(name = "property_id")
    private String propertyId;
    private String name;
    private String email;
    private String address;
    private String branch;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceOrder> orders;
}
