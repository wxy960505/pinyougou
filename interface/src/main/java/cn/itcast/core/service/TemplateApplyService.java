package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.template.TypeTemplate;

public interface TemplateApplyService {
    public void add(TypeTemplate template);
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows);
}
