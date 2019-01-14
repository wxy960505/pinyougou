package cn.itcast.core.controller;


import cn.itcast.core.pojo.order.Order;
import cn.itcast.core.service.ExcelService;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class ExcelOrderController {

    @Reference
    ExcelService orderService;


    @RequestMapping("/orderExport")
    public void orderExport () {
        /*数据库的数据*/
        List<Order> orderList = orderService.findAll();
        /*新建map*/
        List<Map<String, Object>> listMap = new ArrayList<>();

        if (orderList!=null){
            for (Order order : orderList) {

                Map<String,Object> dataMap=new HashMap<>();
                dataMap.put("order_id",order.getOrderId());
                dataMap.put("payment",order.getPayment());
                dataMap.put("payment_type",order.getPaymentType());
                dataMap.put("post_fee",order.getPostFee());
                dataMap.put("status",order.getStatus());
                dataMap.put("create_time",order.getCreateTime());
                dataMap.put("update_time",order.getUpdateTime());
                dataMap.put("payment_time",order.getPaymentTime());
                dataMap.put("consign_time",order.getConsignTime());
                dataMap.put("end_time",order.getEndTime());
                dataMap.put("close_time",order.getCloseTime());
                dataMap.put("shipping_name",order.getShippingName());
                dataMap.put("shipping_code",order.getShippingCode());
                dataMap.put("user_id",order.getUserId());
                dataMap.put("buyer_message",order.getBuyerMessage());
                dataMap.put("buyer_nick",order.getBuyerNick());
                dataMap.put("buyer_rate",order.getBuyerRate());
                dataMap.put("receiver_area_name",order.getReceiverAreaName());
                dataMap.put("receiver_mobile",order.getReceiverMobile());
                dataMap.put("receiver_zip_code",order.getReceiverZipCode());
                dataMap.put("receiver",order.getReceiver());
                dataMap.put("expire",order.getExpire());
                dataMap.put("invoice_type",order.getInvoiceType());
                dataMap.put("source_type",order.getSourceType());
                dataMap.put("seller_id",order.getSellerId());
                listMap.add(dataMap);
            }

        }


        String title = "商家";
        String[] rowsName = new String[]{"订单id", "支付", "支付方式", "邮费", "状态", "创建时间", "更新时间",
                "支付时间", "consign_time","end_time", "close_time", "shipping_name", "shipping_code",
                "user_id", "buyer_message", "buyer_nick", "buyer_rate",
                "receiver_area_name","receiver_mobile", "receiver_zip_code", "receiver",
                "expire", "invoice_type", "source_type", "seller_id"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> data = listMap.get(i);// 获取5次
            objs = new Object[rowsName.length];
            objs[0] = data.get("order_id");
            objs[1] = data.get("payment");
            objs[2] = data.get("payment_type");
            objs[3] = data.get("post_fee");
            objs[4] = data.get("status");

            objs[5] = data.get("create_time");
            objs[6] = data.get("update_time");
            objs[7] = data.get("payment_time");
            objs[8] = data.get("consign_time");
            objs[9] = data.get("end_time");

            objs[10] = data.get("close_time");
            objs[11] = data.get("shipping_name");
            objs[12] = data.get("shipping_code");
            objs[13] = data.get("user_id");
            objs[14] = data.get("buyer_message");

            objs[15] = data.get("buyer_nick");
            objs[16] = data.get("buyer_rate");
            objs[17] = data.get("receiver_area_name");
            objs[18] = data.get("receiver_mobile");
            objs[19] = data.get("receiver_zip_code");
            objs[20] = data.get("receiver");
            objs[21] = data.get("expire");
            objs[22] = data.get("invoice_type");
            objs[23] = data.get("source_type");
            objs[24] = data.get("seller_id");

            dataList.add(objs);
        }
        ExportExcel2 ex = new ExportExcel2(title, rowsName, dataList);
        try {
            ex.export();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

