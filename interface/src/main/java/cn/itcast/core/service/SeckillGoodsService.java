package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillOrder;

import java.util.List;

public interface SeckillGoodsService {

    public SeckillGoods findOne(Long id);

    List<SeckillGoods> findAll();

    public PageResult search(SeckillGoods seckillGoods, Integer page, Integer rows);

    public void add(SeckillGoods seckillGoods);

}
