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
import lk.ijse.gdse67.springposbackend.exception.CustomerNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.ItemNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.OrderNotFoundException;
import lk.ijse.gdse67.springposbackend.exception.ReturnDateExceededException;
import lk.ijse.gdse67.springposbackend.service.OrderService;
import lk.ijse.gdse67.springposbackend.util.AppUtil;
import lk.ijse.gdse67.springposbackend.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        if(orderPlacingCustomer == null){
            throw new CustomerNotFoundException("Customer not found");
        }
        placeOrder.setCustomer(orderPlacingCustomer);

        List<OrderItem> orderItemsList = new ArrayList<>();
        placeOrder.setOrderItems(orderItemsList);

        orderDao.save(placeOrder);

        placeOrderDto.getOrderItems().forEach(orderItemDto -> {
            OrderItemId orderItemId = new OrderItemId(orderId, orderItemDto.getItemId());
            Item orderItem = itemDao.getReferenceById(orderItemDto.getItemId());
            if(orderItem == null){
                throw new ItemNotFoundException("Item not found");
            }

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
        /*Testing*/
        List<OrderItemDto> orderItemDtos= new ArrayList<>();
        OrderItemDto orderItemDto = OrderItemDto.builder()
                .orderId("O-5a4ca8f6-c414-4d97-b3f7-773fb6b5edc5")
                .itemId("I-fda353fe-b65d-44a5-b42e-c282d9814dda")
                .itemCount(2)
                .total(450000)
                .build();
        orderItemDtos.add(orderItemDto);
        returnOrderItems(orderItemDtos);
        // =============================


        return placeOrderDtos;
    }

    @Override
    public OrderStatus getOrder(String orderId) {
        PlaceOrder placeOrder = orderDao.getReferenceById(orderId);
        if(placeOrder == null){
            throw new OrderNotFoundException("Order not found");
        }
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

    public void returnOrderItems(List<OrderItemDto> orderItemDtos){
        orderItemDtos.forEach(orderItemDto -> {
            PlaceOrder fetchedOrder = orderDao.getReferenceById(orderItemDto.getOrderId());
            LocalDate today = LocalDate.now();
            LocalDate sevenDaysAgo = today.minusDays(7);
            if(fetchedOrder.getOrderDate().toLocalDate().isBefore(sevenDaysAgo)){
                throw new ReturnDateExceededException("Return Date Exceeded");
            }else{
                OrderItemId orderItemId = new OrderItemId(orderItemDto.getOrderId(), orderItemDto.getItemId());
                Item item = itemDao.getReferenceById(orderItemId.getItemId());
                if (item == null) {
                    throw new ItemNotFoundException("Item not found");
                }else{
                    item.setQty(item.getQty() + orderItemDto.getItemCount());
                    itemDao.save(item);
                }

                OrderItem fetchedOrderItem = orderItemDao.getReferenceById(orderItemId);
                if (fetchedOrderItem.getItemCount() == 0) {
                    orderItemDao.deleteById(orderItemId);
                }else{
                    fetchedOrder.setBalance(fetchedOrder.getBalance() + ((orderItemDto.getTotal()/100)*(100-fetchedOrder.getDiscount())*orderItemDto.getItemCount()));
                    fetchedOrderItem.setItemCount(fetchedOrderItem.getItemCount() - orderItemDto.getItemCount());
                    fetchedOrderItem.setTotal(fetchedOrderItem.getTotal() - (fetchedOrderItem.getUnitPrice()*orderItemDto.getItemCount()));
                    orderItemDao.save(fetchedOrderItem);
                }
            }
        });
    }
}
