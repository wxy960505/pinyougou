package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillOrderDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.pojo.seckill.SeckillOrderQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SeckillServiceImpl implements SeckillService{

    @Autowired
    private SeckillOrderDao seckillOrderDao;

    //查询秒杀订单
    @Override
    public PageResult search(SeckillOrder seckillOrder, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        SeckillOrderQuery seckillOrderQuery = new SeckillOrderQuery();
        SeckillOrderQuery.Criteria criteria = seckillOrderQuery.createCriteria();
        //模糊查询, 当前商家
        if (seckillOrder.getSellerId() != null) {
            criteria.andSellerIdEqualTo(seckillOrder.getSellerId());
        }
        Page<SeckillOrder> seckillOrders =(Page<SeckillOrder>) seckillOrderDao.selectByExample(seckillOrderQuery);
        return new PageResult(seckillOrders.getTotal(), seckillOrders.getResult());
    }
}
