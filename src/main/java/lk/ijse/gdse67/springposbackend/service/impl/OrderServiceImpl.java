package lk.ijse.gdse67.springposbackend.service.impl;

import lk.ijse.gdse67.springposbackend.dao.CustomerDao;
import lk.ijse.gdse67.springposbackend.dao.ItemDao;
import lk.ijse.gdse67.springposbackend.dao.OrderDao;
import lk.ijse.gdse67.springposbackend.dao.OrderItemDao;
import lk.ijse.gdse67.springposbackend.dto.OrderStatus;
import lk.ijse.gdse67.springposbackend.dto.impl.OrderItemDto;
import lk.ijse.gdse67.springposbackend.dto.impl.PlaceOrderDto;
import lk.ijse.gdse67.springposbackend.entity.impl.*;
import lk.ijse.gdse67.springposbackend.exception.*;
import lk.ijse.gdse67.springposbackend.service.OrderService;
import lk.ijse.gdse67.springposbackend.util.AppUtil;
import lk.ijse.gdse67.springposbackend.util.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Override
    public void addOrder(PlaceOrderDto placeOrderDto) {
        String orderId = AppUtil.generateOrderId();

        PlaceOrder placeOrder = mapper.mapToPlaceOrder(placeOrderDto);
        placeOrder.setOrderId(orderId);

        Customer orderPlacingCustomer = customerDao.getReferenceById(placeOrderDto.getCustomerId());
        if (orderPlacingCustomer == null) {
            logger.error("Customer not in database , Customer not found exception occurred");
            throw new CustomerNotFoundException("Customer not found");
        }else if(!customerDao.isAvailable(orderPlacingCustomer.getPropertyId())){
            logger.error("Customer not available , Customer not available exception occurred");
            throw new CustomerNotAvailableException("Customer not available");
        }
        placeOrder.setCustomer(orderPlacingCustomer);

        List<OrderItem> orderItemsList = new ArrayList<>();
        placeOrder.setOrderItems(orderItemsList);

        orderDao.save(placeOrder);

        placeOrderDto.getOrderItems().forEach(orderItemDto -> {
            OrderItemId orderItemId = new OrderItemId(orderId, orderItemDto.getItemId());
            Item orderItem = itemDao.getReferenceById(orderItemDto.getItemId());
            if (orderItem == null) {
                logger.error("Item not in database , Item not found exception occurred");
                throw new ItemNotFoundException("Item not found");
            }else if(orderItem.getQty() < orderItemDto.getItemCount()){
                logger.error("Item out of stock , Item out of stock exception occurred (Stock Remaining :"+orderItem.getQty()+")");
                throw new ItemOutOfStockException("Item out of stock");
            }
            orderItem.setQty(orderItem.getQty() - orderItemDto.getItemCount());
            itemDao.save(orderItem);
            logger.info("Item Quantity reduced successfully "+orderItemDto.getItemCount()+" amount is reduced.");

            OrderItem orderItemToAdd = OrderItem.builder()
                    .propertyId(orderItemId)
                    .item(orderItem)
                    .itemCount(orderItemDto.getItemCount())
                    .unitPrice(orderItemDto.getUnitPrice())
                    .total(orderItemDto.getTotal())
                    .placeOrder(placeOrder)
                    .build();
            orderItemsList.add(orderItemToAdd);
            logger.info("Order item added to the list successfully. Order item id : "+orderItemId);
            orderItemDao.save(orderItemToAdd);
            logger.info("Order item added to the database successfully. Order item id : "+orderItemId);
        });
        placeOrder.setOrderItems(orderItemsList);
        orderDao.save(placeOrder);
        logger.info("Order Placed successfully. Order id : "+placeOrder.getOrderId());
    }

    @Override
    public List<PlaceOrderDto> getAllOrders() {
        List<PlaceOrder> placeOrders = orderDao.findAll();
        List<PlaceOrderDto> placeOrderDtos = new ArrayList<>();

        placeOrders.forEach(placeOrder -> {
            List<OrderItemDto> orderItemList = getOrderItemList(placeOrder.getOrderItems());
            PlaceOrderDto placeOrderDto = mapper.mapToPlaceOrderDto(placeOrder, orderItemList);
            placeOrderDtos.add(placeOrderDto);
        });
        logger.info("All orders fetched at : "+LocalDate.now());
        return placeOrderDtos;
    }

    @Override
    public OrderStatus getOrder(String orderId) {
        PlaceOrder placeOrder = orderDao.getReferenceById(orderId);
        if (placeOrder == null) {
            logger.error("Order not in database , Order not found exception occurred");
            throw new OrderNotFoundException("Order not found");
        }
        List<OrderItemDto> orderItemList = getOrderItemList(placeOrder.getOrderItems());
        return mapper.mapToPlaceOrderDto(placeOrder, orderItemList);
    }

    private List<OrderItemDto> getOrderItemList(List<OrderItem> orderItems) {
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            OrderItemDto orderItemDto = mapper.mapToOrderItemDto(orderItem);
            orderItemDtos.add(orderItemDto);
        });
        logger.info("Order items fetched at : "+LocalDate.now());
        return orderItemDtos;
    }


    @Override
    public void returnOrderItems(List<OrderItemDto> orderItemDtos) {
        orderItemDtos.forEach(orderItemDto -> {
            PlaceOrder fetchedOrder = orderDao.getReferenceById(orderItemDto.getOrderId());
            LocalDate today = LocalDate.now();
            LocalDate sevenDaysAgo = today.minusDays(7);
            if (fetchedOrder.getOrderDate().toLocalDate().isBefore(sevenDaysAgo)) {
                logger.error("Return Date Exceeded , Return Date Exceeded exception occurred. (Order date : "+fetchedOrder.getOrderDate().toLocalDate()+")");
                throw new ReturnDateExceededException("Return Date Exceeded");
            } else {
                OrderItemId orderItemId = new OrderItemId(orderItemDto.getOrderId(), orderItemDto.getItemId());
                OrderItem fetchedOrderItem = orderItemDao.getReferenceById(orderItemId);
                if (fetchedOrderItem.getItemCount() == 0 || fetchedOrderItem.getItemCount() < orderItemDto.getItemCount()) {
                    logger.error("Order Item already returned or too many items attempted to be returned (Order contains : "+fetchedOrderItem.getItemCount()+" while attempting to return : "+orderItemDto.getItemCount()+")");
                    throw new DataPersistException("Order Item already returned or too many items attempted to be returned");
                }
                Item item = itemDao.getReferenceById(orderItemId.getItemId());
                if (item == null) {
                    logger.error("Item not in database , Item not found exception occurred (Searching failed to retrieve :"+orderItemId.getItemId()+")");
                    throw new ItemNotFoundException("Item not found");
                } else {
                    item.setQty(item.getQty() + orderItemDto.getItemCount());
                    itemDao.save(item);

                    fetchedOrder.setBalance(fetchedOrder.getBalance() + ((orderItemDto.getTotal() / 100) * (100 - fetchedOrder.getDiscount()) * orderItemDto.getItemCount()));
                    fetchedOrderItem.setItemCount(fetchedOrderItem.getItemCount() - orderItemDto.getItemCount());
                    fetchedOrderItem.setTotal(fetchedOrderItem.getTotal() - (fetchedOrderItem.getUnitPrice() * orderItemDto.getItemCount()));
                    orderItemDao.save(fetchedOrderItem);
                }
            }
        });
    }
}
