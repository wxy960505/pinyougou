package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.template.TypeTemplate;

import java.util.List;
import java.util.Map;

public interface TemplateCheckService {
    public void updateStatus(Long id, String status);
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows);
    public List<Map> findBySpecList(Long id);


}
