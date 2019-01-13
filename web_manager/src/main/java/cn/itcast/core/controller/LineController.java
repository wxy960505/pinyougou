package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.DayTime;
import cn.itcast.core.service.LineService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/line")
public class LineController {
    @Reference
    LineService lineService;

    //获取最近七天的销量
    @RequestMapping("/findAllTime")
    public List<DayTime> find() {

        //获取登陆的商家用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DayTime> dayTimes = lineService.salesVolume(name);
        //返回页面JSON
        return dayTimes;
    }
}
