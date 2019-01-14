package cn.itcast.core.controller;

import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.service.ItemCatService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分类管理
 *
 */
@RestController
@RequestMapping("/itemCat")
public class ItemCatController {

    @Reference
    private ItemCatService catService;

    /**
     * 根据父级id查询它对应的子集数据
     * @param parentId   父级id
     * @return
     */
    @RequestMapping("/findByParentId")
    public List<ItemCat> findByParentId(Long parentId) {
        List<ItemCat> list = catService.findByParentId(parentId);
        if(list == null)
            list = new ArrayList<>();
        return list;
    }

    @RequestMapping("/findAll")
    public List<ItemCat> findAll() {
        List<ItemCat> list = catService.findAll();
        return list;
    }

    ////$scope.item_cat_list =
    // [{item1:{cat},sub2:[{item2:{cat},sub3:[{cat}...]}...]}...];
    //首页分类
    @RequestMapping("/findPortolCategory")
    public List<Map> findPortolCategory(){
        return catService.findPortolCategory();
    }
}