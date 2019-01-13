package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillOrder;
import cn.itcast.core.service.SeckillService;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckill")
public class SeckillController {
    @Reference
    private SeckillService seckillService;

    //秒杀订单查询
    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillOrder seckillOrder, Integer page, Integer rows) {

        //获取当前登录的商家
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        seckillOrder.setSellerId(name);
        //查询对应商家的秒杀订单
        PageResult search = seckillService.search(seckillOrder, page, rows);
        //返回页面
        return search;
    }
}
