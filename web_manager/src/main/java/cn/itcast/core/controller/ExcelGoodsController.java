package cn.itcast.core.controller;


import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.service.GoodsService;
import cn.itcast.core.service.OrderService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class ExcelGoodsController {

    @Reference
    GoodsService goodsService;


    @RequestMapping("/goodsExport")
    public void goodsExport () {
        /*数据库的数据*/
        List<Goods> goodsList = goodsService.findAll();
        /*新建map*/
        List<Map<String, Object>> listMap = new ArrayList<>();

        if (goodsList!=null){
            for (Goods goods : goodsList) {

                Map<String,Object> dataMap=new HashMap<>();
                dataMap.put("id",goods.getId());
                dataMap.put("seller_id",goods.getSellerId());
                dataMap.put("goods_name",goods.getGoodsName());
                dataMap.put("default_item_id",goods.getDefaultItemId());
                dataMap.put("audit_status",goods.getAuditStatus());
                dataMap.put("is_marketable",goods.getIsMarketable());
                dataMap.put("brand_id",goods.getBrandId());
                dataMap.put("caption",goods.getCaption());
                dataMap.put("category1_id",goods.getCategory1Id());
                dataMap.put("category2_id",goods.getCategory2Id());
                dataMap.put("category3_id",goods.getCategory3Id());
                dataMap.put("small_pic",goods.getSmallPic());
                dataMap.put("price",goods.getPrice());
                dataMap.put("type_template_id",goods.getTypeTemplateId());
                dataMap.put("is_enable_spec",goods.getIsEnableSpec());
                dataMap.put("is_delete",goods.getIsEnableSpec());
                listMap.add(dataMap);
            }

        }


        String title = "商品";
        String[] rowsName = new String[]{"商品id", "商家id", "商品名称", "默认库存id", "商品状态", "is_marketable", "品牌id", "详细说明", "category1_id",
                "category2_id", "category3_id", "small_pic", "价格", "模板id", "is_enable_spec", "is_delete"};
        List<Object[]> dataList = new ArrayList<Object[]>();
        Object[] objs = null;
        for (int i = 0; i < listMap.size(); i++) {
            Map<String, Object> data = listMap.get(i);// 获取5次
            objs = new Object[rowsName.length];
            objs[0] = data.get("id");
            objs[1] = data.get("seller_id");
            objs[2] = data.get("goods_name");
            objs[3] = data.get("default_item_id");
            objs[4] = data.get("audit_status");

            objs[5] = data.get("is_marketable");
            objs[6] = data.get("brand_id");
            objs[7] = data.get("caption");
            objs[8] = data.get("category1_id");
            objs[9] = data.get("category2_id");

            objs[10] = data.get("category3_id");
            objs[11] = data.get("small_pic");
            objs[12] = data.get("price");
            objs[13] = data.get("type_template_id");
            objs[14] = data.get("is_enable_spec");

            objs[15] = data.get("is_delete");

            dataList.add(objs);
        }
        ExportExcel1 ex = new ExportExcel1(title, rowsName, dataList);
        try {
            ex.export();
        } catch (Exception e) {
            e.printStackTrace();
        }







    }

}

