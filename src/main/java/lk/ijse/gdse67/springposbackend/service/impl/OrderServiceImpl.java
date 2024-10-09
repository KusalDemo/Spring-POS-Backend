package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.dao.CustomerDao;
import lk.ijse.gdse67.springposbackend.dao.ItemDao;
import lk.ijse.gdse67.springposbackend.dao.OrderDao;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.entity.impl.Customer;
import lk.ijse.gdse67.springposbackend.entity.impl.Item;
import lk.ijse.gdse67.springposbackend.entity.impl.OrderItem;
import lk.ijse.gdse67.springposbackend.entity.impl.PlaceOrder;
import lk.ijse.gdse67.springposbackend.exception.CustomerNotFoundException;
import lk.ijse.gdse67.springposbackend.service.CustomerService;
import lk.ijse.gdse67.springposbackend.service.OrderService;
import lk.ijse.gdse67.springposbackend.util.AppUtil;
import lk.ijse.gdse67.springposbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public void addOrder(PlaceOrderDto placeOrderDto) {
        List<OrderItem> orderItemList = null;
        placeOrderDto.getOrderItems().forEach(orderItemDto -> {
            /*orderItemList.add(itemDao.getReferenceById(orderItemDto.getItemId()));*/
            new OrderItem(orderItemDto.getItemId(), placeOrderDto.getOrderId(), orderItemDto.getItemCount(), orderItemDto.getUnitPrice(), orderItemDto.getUnitPrice() * orderItemDto.getItemCount());
        });
        PlaceOrder placeOrder = mapper.mapToPlaceOrder(placeOrderDto);
        placeOrder.setOrderId(AppUtil.generateOrderId());
        Customer orderPlacingCustomer;

        try {
            orderPlacingCustomer = customerDao.getReferenceById(placeOrderDto.getCustomerId());
            placeOrder.setCustomer(orderPlacingCustomer);
            System.out.println(placeOrder.getCustomer().getPropertyId() + " " + placeOrder.getOrderId());

            placeOrderDto.getOrderItems().stream().map(orderItemDto -> {
                /*return mapper.mapToItemDto(itemDao.getReferenceById(orderItemDto.getItemId()));*/
                Item itemInOrder = itemDao.getReferenceById(orderItemDto.getItemId());
                itemInOrder.setQty(itemInOrder.getQty() - orderItemDto.getItemCount());
                itemDao.save(itemInOrder);

            });

        } catch (CustomerNotFoundException e) {
            throw new CustomerNotFoundException("Invalid Customer Id");
        }

        if (orderPlacingCustomer == null) {
            throw new IllegalArgumentException("Invalid Customer Id");
        }
        placeOrderDto.getOrderItems().forEach(orderItemDto -> {

        });
        placeOrder.setCustomer(orderPlacingCustomer);
        orderDao.save(placeOrder);
    }
}
