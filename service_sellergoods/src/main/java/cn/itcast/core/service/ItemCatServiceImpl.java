package cn.itcast.core.service;

import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatDao catDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<ItemCat> findByParentId(Long parentId) {
        //1. 查询所有分类数据
        List<ItemCat> itemAllList = catDao.selectByExample(null);
        //2. 将所有分类数据以分类名称为key, 对应的模板id为value, 存入redis中
        for (ItemCat itemCat : itemAllList) {
            redisTemplate.boundHashOps(Constants.CATEGORY_LIST_REDIS).put(itemCat.getName(), itemCat.getTypeId());
        }

        //3. 到数据库中查询数据返回到页面展示
        ItemCatQuery query = new ItemCatQuery();
        ItemCatQuery.Criteria criteria = query.createCriteria();
        //根据父级id查询
        criteria.andParentIdEqualTo(parentId);
        List<ItemCat> itemCats = catDao.selectByExample(query);
        return itemCats;
    }

    @Override
    public ItemCat findOne(Long id) {
        return catDao.selectByPrimaryKey(id);
    }

    @Override
    public List<ItemCat> findAll() {
        return catDao.selectByExample(null);
    }

    ////$scope.item_cat_list =
    // [{item1:{cat},sub2:[{item2:{cat},sub3:[{cat}...]}...]}...];
    @Override
    public List<Map> findPortolCategory() {
        List<Map> results = new ArrayList<>();
        //一级分类
        ItemCatQuery itemCatQuery = new ItemCatQuery();
        itemCatQuery.createCriteria().andParentIdEqualTo(0L);
        List<ItemCat> catList1 = catDao.selectByExample(itemCatQuery);

        for (ItemCat itemCat1 : catList1) {
            Map map1 = new HashMap();
            //二级分类
            List<Map> catList2_results = new ArrayList<>();
            itemCatQuery.clear();
            itemCatQuery.createCriteria().andParentIdEqualTo(itemCat1.getId());
            List<ItemCat> catList2 = catDao.selectByExample(itemCatQuery);
            for (ItemCat itemCat2 : catList2) {
                Map map2 = new HashMap();
                itemCatQuery.clear();
                itemCatQuery.createCriteria().andParentIdEqualTo(itemCat2.getId());
                List<ItemCat> catList3_results = catDao.selectByExample(itemCatQuery);
                map2.put("item2",itemCat2);
                map2.put("sub3",catList3_results);
                catList2_results.add(map2);
            }
            map1.put("item1",itemCat1);
            map1.put("sub2",catList2_results);
            results.add(map1);
        }



        return results;
    }
}
