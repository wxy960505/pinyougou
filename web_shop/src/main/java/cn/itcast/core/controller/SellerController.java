package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.seller.Seller;
import cn.itcast.core.service.SellerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    @RequestMapping("/add")
    public Result add(@RequestBody Seller seller) {
        try {
            sellerService.add(seller);
            return new Result(true, "注册成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "注册失败!");
        }
    }

    @RequestMapping("/findOne")
    public Seller findOne(String id) {
        Seller one = sellerService.findOne(id);
        return one;
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        List<Map> list = sellerService.selectOptionList();
        return list;
    }
}
