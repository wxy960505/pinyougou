package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.DayTime;

import java.util.List;

public interface TimeService {

    public List<DayTime> salesVolume(String name);
}
