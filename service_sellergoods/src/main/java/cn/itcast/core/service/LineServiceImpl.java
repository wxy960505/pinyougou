package cn.itcast.core.service;

import cn.itcast.core.dao.order.OrderDao;
import cn.itcast.core.pojo.entity.DayTime;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LineServiceImpl implements LineService {

    @Autowired
    OrderDao orderDao;
    @Override
    public List<DayTime> salesVolume(String name) {
        List<DayTime> dayTimes = new ArrayList<>();
        //获取当前日期（今天）
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] split = format.split("-");
        String data = "19_"+split[1]+"_"+split[2];
        //拼装查询参数
        data = "%" + data + "%";
        int countDayTime = orderDao.findCountDayTime(data);
        System.out.println(data);
        //封装集合
        dayTimes.add(new DayTime(format, countDayTime));
        String s = split[2];
        //循环前六天查询封装集合
        for (int i = 1; i < 7 ; i++) {
            int i1 = Integer.parseInt(s);
            s = String.valueOf(i1 - 1);
            int count = orderDao.findCountDayTime("%"+"18_" + split[1] + "_" + s+"%");
            dayTimes.add(new DayTime(split[0]+"-"+split[1]+"-"+s, count));
        }
        return dayTimes;
    }

}
