package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.dao.order.OrderItemDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.pojo.order.OrderItem;
import cn.itcast.core.pojo.order.OrderItemQuery;
import cn.itcast.core.pojo.order.OrderQuery;
import cn.itcast.core.util.DateUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    /*
    * 分页查询
    * */
    @Override
    public PageResult findPage(Order order, Integer page, Integer rows) {
        PageHelper.startPage(page,rows);

        OrderQuery query=new OrderQuery();
        OrderQuery.Criteria criteria = query.createCriteria();
        if (order!=null){
            if (order.getStatus()!=null&&!"".equals(order.getStatus())){
                criteria.andStatusEqualTo(order.getStatus());
            }
            if (order.getCreateTime()!=null&&!"".equals(order.getCreateTime())){
                criteria.andCreateTimeBetween(DateUtils.stringToDate(DateUtils.getDayStartAndEndTimePointStr(order.getCreateTime())[0]),DateUtils.stringToDate(DateUtils.getDayStartAndEndTimePointStr(order.getCreateTime())[1]));
            }
            if (order.getSear()!=null&&!"".equals(order.getSear())){
                if ("1".equals(order.getSear())){
                criteria.andUpdateTimeBetween(DateUtils.stringToDate(DateUtils.getDayStartAndEndTimePointStr(new Date())[0]),DateUtils.stringToDate(DateUtils.getDayStartAndEndTimePointStr(new Date())[1]));
                }
                if ("2".equals(order.getSear())){
                    criteria.andUpdateTimeBetween(DateUtils.getWeekStartAndEndDate(new Date())[0],DateUtils.getWeekStartAndEndDate(new Date())[1]);
                }
                if ("3".equals(order.getSear())){
                    criteria.andUpdateTimeBetween(DateUtils.getMonthStartAndEndDate(new Date())[0],DateUtils.getMonthStartAndEndDate(new Date())[1]);
                }
            }
            if (order.getStatus()!=null&&!"".equals(order.getStatus())){
                criteria.andStatusEqualTo(order.getStatus());
            }
        }
        criteria.andSellerIdEqualTo(order.getSellerId());
       Page<Order> ordersList = (Page<Order>) orderDao.selectByExample(query);
        for (Order order1 : ordersList) {
            OrderItemQuery orderItemQuery = new OrderItemQuery();
            OrderItemQuery.Criteria criteria1 = orderItemQuery.createCriteria();
            criteria1.andOrderIdEqualTo(order1.getOrderId());
            criteria1.andSellerIdEqualTo(order.getSellerId());
            List<OrderItem> orderItemList = orderItemDao.selectByExample(orderItemQuery);
            order1.setOrderItemList(orderItemList);
            order1.setOrderIdStr(String.valueOf(order1.getOrderId()));
        }



        return new PageResult(ordersList.getTotal(),ordersList.getResult());
    }

    @Override
    public void updateStatus(Order order, String status) {
        //获取当前时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time= sdf.format( new Date());
        Date date = DateUtils.stringToDate(time);
        //根据订单id改变数据库中订单的状态
        order.setStatus(status);
        order.setConsignTime(date);
        orderDao.updateByPrimaryKeySelective(order);
    }


}
