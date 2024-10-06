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
@Table(name = "item")
public class Item implements SuperEntity {
    @Id
    @Column(name = "property_id")
    private String propertyId;
    private String name;
    private String description;
    private double price;
    private int qty;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;
}
