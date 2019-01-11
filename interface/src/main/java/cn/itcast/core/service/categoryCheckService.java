package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.item.ItemCat;

public interface categoryCheckService {
    public void updateStatus(Long id, String status);
    public PageResult findPage( ItemCat itemCat,Integer page, Integer rows);

}
