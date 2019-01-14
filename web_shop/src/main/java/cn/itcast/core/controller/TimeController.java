package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.DayTime;
import cn.itcast.core.service.TimeService;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/line")
public class TimeController {

    @Reference
    TimeService timeService;

    //获取最近七天的销量
    @RequestMapping("/findAllTime")
    public List<DayTime> find() {

        //获取登陆的商家用户名
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<DayTime> dayTimes1 = timeService.salesVolume(name);
        //返回页面JSON
        return dayTimes1;
    }
}
