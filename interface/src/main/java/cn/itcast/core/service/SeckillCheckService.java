package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.seckill.SeckillGoods;

public interface SeckillCheckService {
    public void updateStatus(Long id, String status);
    public PageResult findPage(SeckillGoods seckillGoods, Integer page, Integer rows);

}
