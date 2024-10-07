package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.customStatusCodes.SelectedItemCodes;
import lk.ijse.gdse67.springposbackend.dao.ItemDao;
import lk.ijse.gdse67.springposbackend.dto.ItemStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.ItemDto;
import lk.ijse.gdse67.springposbackend.entity.impl.Item;
import lk.ijse.gdse67.springposbackend.exception.DataPersistException;
import lk.ijse.gdse67.springposbackend.exception.ItemNotFoundException;
import lk.ijse.gdse67.springposbackend.service.ItemService;
import lk.ijse.gdse67.springposbackend.util.AppUtil;
import lk.ijse.gdse67.springposbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private Mapping mapper;

    @Override
    public void addItem(ItemDto itemDto) {
       itemDto.setPropertyId(AppUtil.generateItemId());
        Item savedItem = itemDao.save(mapper.mapToItem(itemDto));
        if (savedItem == null) {
            throw new DataPersistException("Failed to add item");
        }
    }

    @Override
    public void updateItem(String propertyId, ItemDto itemDto) {
        Item fetchedItem = itemDao.getReferenceById(propertyId);
        if (fetchedItem == null) {
            throw new ItemNotFoundException("Item not found");
        }
        fetchedItem.setName(itemDto.getName());
        fetchedItem.setDescription(itemDto.getDescription());
        fetchedItem.setQty(itemDto.getQty());
        fetchedItem.setPrice(itemDto.getPrice());
        itemDao.save(fetchedItem);
    }

    @Override
    public void deleteItem(String propertyId) {
        Item fetchedItem = itemDao.getReferenceById(propertyId);
        if (fetchedItem == null) {
            throw new ItemNotFoundException("Item not found");
        }
        itemDao.delete(fetchedItem);
    }

    @Override
    public ItemStatus getItem(String propertyId) {
        Item fetchedItem = itemDao.getReferenceById(propertyId);
        if (fetchedItem == null) {
            return new SelectedItemCodes(1,"Item not found");
        }
        return mapper.mapToItemDto(fetchedItem);
    }

    @Override
    public List<ItemDto> getAllItems() {
        return mapper.mapToItemDtoList(itemDao.findAll());
    }
}
