package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;

public interface OrdersService {

    PageResult findPage(Order ordersEntity, Integer page, Integer rows);

    void updateStatus(Order order, String status);
}
