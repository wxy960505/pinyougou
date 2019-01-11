package cn.itcast.core.service;

import cn.itcast.core.pojo.seckill.SeckillGoods;

import java.util.List;

public interface SeckillGoodsService {

    public SeckillGoods findOne(Long id);

    List<SeckillGoods> findAll();
}
