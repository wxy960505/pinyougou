package cn.itcast.core.pojo.entity;

import java.io.Serializable;

public class DayTime implements Serializable {

    //日期
    private String ytime;
    //销量
    private Integer item1;

    public DayTime(String ytime, Integer item1) {
        this.ytime = ytime;
        this.item1 = item1;
    }

    public String getYtime() {
        return ytime;
    }

    public void setYtime(String ytime) {
        this.ytime = ytime;
    }

    public Integer getItem1() {
        return item1;
    }

    public void setItem1(Integer item1) {
        this.item1 = item1;
    }
}
