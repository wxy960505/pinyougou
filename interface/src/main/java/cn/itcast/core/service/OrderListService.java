package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.log.PayLog;

import java.util.List;
import java.util.Map;

public interface OrderListService {

    public List<PayLog> findAll();

    public PageResult findPage(Integer page, Integer rows);

    /*public PageResult findPage(PayLog payLog,Integer page, Integer rows);*/

    public void delete(String[] outTradeNos);
}
