package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;

public interface SpecCheckService {
    public void updateStatus(Long id, String status);
    public PageResult findPage(Specification spec, Integer page, Integer rows);

}
