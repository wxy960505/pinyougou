package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.service.BrandApplyService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brandApply")
public class BrandApplyController {
    @Reference
    BrandApplyService brandApplyService;

    @RequestMapping("/add")
    public Result addBrand(@RequestBody Brand brand){

        try {
            brandApplyService.add(brand);
            return new Result(true,"提交申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"提交申请失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand, Integer page, Integer rows) {
        PageResult result = brandApplyService.findPage(brand, page, rows);
        return result;
    }
}
