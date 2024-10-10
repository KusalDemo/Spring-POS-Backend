package lk.ijse.gdse67.springposbackend.controller;

import lk.ijse.gdse67.springposbackend.dto.CustomerStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.CustomerDto;
import lk.ijse.gdse67.springposbackend.exception.CustomerNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.DataPersistException;
import lk.ijse.gdse67.springposbackend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addCustomer(@RequestBody CustomerDto customerDto) {
        try {
            customerService.addCustomer(customerDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping(value = "/{propertyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CustomerStatus getCustomer(@PathVariable("propertyId") String propertyId) {
        return customerService.getCustomer(propertyId);
    }

    @PutMapping(value = "/{propertyId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateCustomer(@PathVariable("propertyId") String propertyId, @RequestBody CustomerDto customerDto) {
        try {
            customerService.updateCustomer(propertyId, customerDto);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException | DataPersistException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{propertyId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("propertyId") String propertyId) {
        try {
            customerService.deleteCustomer(propertyId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (CustomerNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
