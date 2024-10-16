package lk.ijse.gdse67.springposbackend.controller;

import lk.ijse.gdse67.springposbackend.customStatusCodes.SelectedItemCodes;
import lk.ijse.gdse67.springposbackend.dto.ItemStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.ItemDto;
import lk.ijse.gdse67.springposbackend.exception.DataPersistException;
import lk.ijse.gdse67.springposbackend.exception.ItemNotFoundException;
import lk.ijse.gdse67.springposbackend.service.ItemService;
import lk.ijse.gdse67.springposbackend.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/item")
@CrossOrigin
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addItem(@RequestBody ItemDto itemDto) {
        try{
            itemService.addItem(itemDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDto> getAllItems(){
        return itemService.getAllItems();
    }

    @GetMapping(value = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ItemStatus getItem(@PathVariable("propertyId") String propertyId){
        boolean isItemIdValid = Regex.ITEM_ID.validate(propertyId);
        if (isItemIdValid){
            return itemService.getItem(propertyId);
        }else{
            return new SelectedItemCodes(1, "Item Id Invalid");
        }
    }

    @PutMapping(value = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateItem(@PathVariable("propertyId") String propertyId, @RequestBody ItemDto itemDto){
        boolean isItemIdValid = Regex.ITEM_ID.validate(propertyId);
        try{
            if(isItemIdValid){
                itemService.updateItem(propertyId, itemDto);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{propertyId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("propertyId") String propertyId){
        boolean isItemIdValid = Regex.ITEM_ID.validate(propertyId);
        try{
            if(isItemIdValid){
                itemService.deleteItem(propertyId);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }catch (ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
