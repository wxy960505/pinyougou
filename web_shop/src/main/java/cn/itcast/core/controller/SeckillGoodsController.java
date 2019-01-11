package cn.itcast.core.controller;

import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/seckill")
public class SeckillGoodsController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    //通过id查询
    @RequestMapping("/findOne")
    public SeckillGoods findOne(Long id) {
        return seckillGoodsService.findOne(id);
    }

    //查询全部
    @RequestMapping("/findAll")
    public List<SeckillGoods> findAll() {
        return seckillGoodsService.findAll();
    }

}
