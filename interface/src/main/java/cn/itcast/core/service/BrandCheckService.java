package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.Goods;

public interface BrandCheckService {
    public void updateStatus(Long id, String status);
    public PageResult findPage(Brand brand, Integer page, Integer rows);

}
