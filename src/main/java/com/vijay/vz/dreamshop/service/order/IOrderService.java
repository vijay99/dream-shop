package com.vijay.vz.dreamshop.service.order;

import com.vijay.vz.dreamshop.dto.OrderDto;
import com.vijay.vz.dreamshop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
