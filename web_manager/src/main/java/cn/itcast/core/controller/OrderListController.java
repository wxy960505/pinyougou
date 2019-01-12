package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.service.OrderListService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orderList")
public class OrderListController {
    @Reference
    private OrderListService orderListService;
    @RequestMapping("/findAll")
    public List<PayLog> findAll(){
        List<PayLog> payLogList = orderListService.findAll();
        return payLogList;
    }
    @RequestMapping("/search")
    public PageResult search(Integer page, Integer rows){
        PageResult result = orderListService.findPage(page, rows);
        return result;
    }
    /*@RequestMapping("/search")
    public PageResult search(@RequestBody PayLog payLog,Integer page, Integer rows){
        PageResult result = orderListService.findPage(payLog,page, rows);
        return result;
    }*/
    @RequestMapping("/delete")
    public Result delete(String[] outTradeNos){
        try {
            orderListService.delete(outTradeNos);
            return new Result(true, "删除成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败!");
        }
    }
}
