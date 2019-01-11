package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Override
    public SeckillGoods findOne(Long id) {
        SeckillGoods seckillGoods = seckillGoodsDao.selectByPrimaryKey(id);
        return seckillGoods;
    }

    @Override
    public List<SeckillGoods> findAll() {
        return seckillGoodsDao.selectByExample(null);
    }
}
