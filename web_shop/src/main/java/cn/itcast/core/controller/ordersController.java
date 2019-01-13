package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.OrdersService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class ordersController {
    @Reference
    private OrdersService ordersService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody Order order, Integer page, Integer rows) {
        //获取当前登录用户用户名
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        //将当前用户名放入卖家id字段
        order.setSellerId(userName);

        PageResult result = ordersService.findPage(order, page, rows);
        return result;
    }
    /*商品发货*/
    @RequestMapping("/updateStatus")
    public  Result updateStatus(Long[] selectIds, String status) {
        try {
            if (selectIds != null) {
                for (Long orderId : selectIds) {
                    Order order=new Order();
                    order.setOrderId(orderId);
                    //1. 更改数据库中商品的审核状态
                    ordersService.updateStatus(order, status);
                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

}
