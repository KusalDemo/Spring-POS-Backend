package lk.ijse.gdse67.springposbackend.entity.impl;

import jakarta.persistence.*;
import lk.ijse.gdse67.springposbackend.entity.SuperEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "order_items")
public class OrderItem implements SuperEntity {
    @EmbeddedId
    private OrderItemId propertyId;

    @MapsId("orderId")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private PlaceOrder placeOrder;

    @MapsId("itemId")
    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    private Integer itemCount;
    private Double unitPrice;
    private Double total;
}
