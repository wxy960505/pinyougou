package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.service.SeckillGoodsService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.opensaml.xml.signature.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seckillappLication")
public class SeckillAppLicationController {

    @Reference
    private SeckillGoodsService seckillGoodsService;

    @RequestMapping("/search")
    public PageResult search(@RequestBody SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageResult search = seckillGoodsService.search(seckillGoods, page, rows);
        return search;
    }

    @RequestMapping("/add")
    public Result add(@RequestBody SeckillGoods seckillGoods) {
        try {
            seckillGoodsService.add(seckillGoods);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }
}
