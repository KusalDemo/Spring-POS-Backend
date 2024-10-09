package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.dao.CustomerDao;
import lk.ijse.gdse67.springposbackend.dao.ItemDao;
import lk.ijse.gdse67.springposbackend.dao.OrderDao;
import lk.ijse.gdse67.springposbackend.dao.OrderItemDao;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.entity.impl.*;
import lk.ijse.gdse67.springposbackend.exception.CustomerNotFoundException;
import lk.ijse.gdse67.springposbackend.service.CustomerService;
import lk.ijse.gdse67.springposbackend.service.OrderService;
import lk.ijse.gdse67.springposbackend.util.AppUtil;
import lk.ijse.gdse67.springposbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Mapping mapper;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private OrderItemDao orderItemDao;


    @Override
    public void addOrder(PlaceOrderDto placeOrderDto) {
        String orderId = AppUtil.generateOrderId();
        List<OrderItem> orderItemList = new ArrayList<>();
        placeOrderDto.getOrderItems().forEach(orderItemDto -> {
            OrderItemId orderItemId = new OrderItemId(orderId, orderItemDto.getItemId());
            System.out.println("OrderItem id : "+orderItemId);
            Item orderItem = itemDao.getReferenceById(orderItemDto.getItemId());

            orderItem.setQty(orderItem.getQty() - orderItemDto.getItemCount());
            itemDao.save(orderItem);

            OrderItem orderItemToAdd = OrderItem.builder()
                    .propertyId(orderItemId)
                    .item(orderItem)
                    .itemCount(orderItemDto.getItemCount())
                    .unitPrice(orderItemDto.getUnitPrice())
                    .total(orderItemDto.getTotal())
                    .build();
            System.out.println(orderItemToAdd.getPropertyId()+" - "+orderItemToAdd.getUnitPrice());
            orderItemList.add(orderItemToAdd);
            orderItemDao.save(orderItemToAdd);
        });

        PlaceOrder placeOrder = mapper.mapToPlaceOrder(placeOrderDto);
        placeOrder.setOrderItems(orderItemList);
        placeOrder.setOrderId(orderId);
        Customer orderPlacingCustomer;

        try {
            orderPlacingCustomer = customerDao.getReferenceById(placeOrderDto.getCustomerId());
            placeOrder.setCustomer(orderPlacingCustomer);
            System.out.println(placeOrder.getCustomer().getPropertyId() + " " + placeOrder.getOrderId());

            orderDao.save(placeOrder);

        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("Invalid Customer Id");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
