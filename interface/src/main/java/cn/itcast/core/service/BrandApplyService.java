package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;

public interface BrandApplyService {
    public void add(Brand brand);
    public PageResult findPage(Brand brand, Integer page, Integer rows);
}
