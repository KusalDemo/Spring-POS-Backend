package lk.ijse.gdse67.springposbackend.controller;

import jakarta.persistence.EntityNotFoundException;
import lk.ijse.gdse67.springposbackend.customStatusCodes.SelectedOrderStatus;
import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.OrderItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.exception.CustomerNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.ItemNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.OrderNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.ReturnDateExceededException;
import lk.ijse.gdse67.springposbackend.service.OrderService;
import lk.ijse.gdse67.springposbackend.util.Regex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        try{
            orderService.addOrder(placeOrderDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (CustomerNotFoundException | ItemNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<PlaceOrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderStatus getOrder(@PathVariable("propertyId") String propertyId) {
        boolean isOrderIdValid = Regex.ORDER_ID.validate(propertyId);
        try{
            if(isOrderIdValid){
                return orderService.getOrder(propertyId);
            }else{
                return new SelectedOrderStatus(1, "Order Id Invalid");
            }
        }catch (EntityNotFoundException | OrderNotFoundException e){
            e.printStackTrace();
            return new SelectedOrderStatus(2,"Order Not Found");
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> returnOrderItem(@RequestBody List<OrderItemDto> orderItemDtos){
        try{
            orderService.returnOrderItems(orderItemDtos);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (ItemNotFoundException | ReturnDateExceededException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
