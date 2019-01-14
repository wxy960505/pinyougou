package cn.itcast.core.pojo.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class MyOrderItem implements Serializable {

    /**
     * 商品图片地址  order_item 数据库中
     */
    private String picPath;

    /**
     * 商品标题  order_item  数据库中
     */
    private String title;

    /**
     * 商品单价   order_item  数据库中
     */
    private BigDecimal price;

    /**
     * 商品购买数量  order_item  数据库中
     */
    private Integer num;


    /**
     * 商品总金额  order_item
     */
    private BigDecimal totalFee;

    /**
     * 规格   item
     */
    private String spec;


    /**
     * 状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭,7、待评价
     * order
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
