package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.customStatusCodes.SelectedOrderStatus;
import lk.ijse.gdse67.springposbackend.dao.CustomerDao;
import lk.ijse.gdse67.springposbackend.dao.ItemDao;
import lk.ijse.gdse67.springposbackend.dao.OrderDao;
import lk.ijse.gdse67.springposbackend.dao.OrderItemDao;
import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.OrderItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.entity.impl.*;
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

        PlaceOrder placeOrder = mapper.mapToPlaceOrder(placeOrderDto);
        placeOrder.setOrderId(orderId);

        Customer orderPlacingCustomer = customerDao.getReferenceById(placeOrderDto.getCustomerId());
        placeOrder.setCustomer(orderPlacingCustomer);

        List<OrderItem> orderItemsList = new ArrayList<>();
        placeOrder.setOrderItems(orderItemsList);

        orderDao.save(placeOrder);

        placeOrderDto.getOrderItems().forEach(orderItemDto -> {
            OrderItemId orderItemId = new OrderItemId(orderId, orderItemDto.getItemId());
            Item orderItem = itemDao.getReferenceById(orderItemDto.getItemId());

            orderItem.setQty(orderItem.getQty() - orderItemDto.getItemCount());
            itemDao.save(orderItem);

            OrderItem orderItemToAdd = OrderItem.builder()
                    .propertyId(orderItemId)
                    .item(orderItem)
                    .itemCount(orderItemDto.getItemCount())
                    .unitPrice(orderItemDto.getUnitPrice())
                    .total(orderItemDto.getTotal())
                    .placeOrder(placeOrder)
                    .build();
            orderItemsList.add(orderItemToAdd);
            orderItemDao.save(orderItemToAdd);
        });
        placeOrder.setOrderItems(orderItemsList);
        orderDao.save(placeOrder);
    }

    @Override
    public List<PlaceOrderDto> getAllOrders() {
        List<PlaceOrder> placeOrders = orderDao.findAll();
        List<PlaceOrderDto> placeOrderDtos=new ArrayList<>();

        placeOrders.forEach(placeOrder -> {
            List<OrderItemDto> orderItemList = getOrderItemList(placeOrder.getOrderItems());
            PlaceOrderDto placeOrderDto = mapper.mapToPlaceOrderDto(placeOrder,orderItemList);
            placeOrderDtos.add(placeOrderDto);
        });
        return placeOrderDtos;
    }

    @Override
    public OrderStatus getOrder(String orderId) {
        PlaceOrder placeOrder = orderDao.getReferenceById(orderId);
        List<OrderItemDto> orderItemList = getOrderItemList(placeOrder.getOrderItems());
        return mapper.mapToPlaceOrderDto(placeOrder,orderItemList);
    }

    private List<OrderItemDto> getOrderItemList(List<OrderItem> orderItems){
        List<OrderItemDto> orderItemDtos=new ArrayList<>();
        orderItems.forEach(orderItem -> {
            OrderItemDto orderItemDto = mapper.mapToOrderItemDto(orderItem);
            orderItemDtos.add(orderItemDto);
        });
        return orderItemDtos;
    }
}
