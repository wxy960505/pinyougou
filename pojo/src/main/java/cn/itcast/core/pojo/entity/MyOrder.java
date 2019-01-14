package cn.itcast.core.pojo.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MyOrder implements Serializable {

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商家名称
     */
    private String seller;


    private List<MyOrderItem> myOrderItemList;



    public Date getCreateTime() {
        return createTime;
    }

    public List<MyOrderItem> getMyOrderItemList() {
        return myOrderItemList;
    }

    public void setMyOrderItemList(List<MyOrderItem> myOrderItemList) {
        this.myOrderItemList = myOrderItemList;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
