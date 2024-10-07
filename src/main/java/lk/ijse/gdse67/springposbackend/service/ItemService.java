package lk.ijse.gdse67.springposbackend.service;

import lk.ijse.gdse67.springposbackend.dto.ItemStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.ItemDto;

import java.util.List;

public interface ItemService {
    void addItem(ItemDto itemDto);
    void updateItem(String propertyId,ItemDto itemDto);
    void deleteItem(String propertyId);
    ItemStatus getItem(String propertyId);
    List<ItemDto> getAllItems();
}
