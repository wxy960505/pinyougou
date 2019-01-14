package cn.itcast.core.service;


import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.service.BrandApplyService;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BrandApplyServiceImpl implements BrandApplyService {
    @Autowired
    BrandDao brandDao;


    //提交品牌申请
    @Override
    public void add(Brand brand) {
        brand.setName(brand.getName()+Constants.BRAND_APPLY);
        brandDao.insert(brand);

    }


    @Override
    public PageResult findPage(Brand brand, Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        BrandQuery brandQuery = new BrandQuery();
        //创建where查询条件
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        criteria.andNameNotLike("%"+ Constants.BRAND_APPLY+"%");

        if (brand != null) {
            if (brand.getName() != null&& !"".equals(brand.getName())){
                criteria.andNameLike("%"+brand.getName()+"%");

            }
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }

        }
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<Brand> brandList = (Page<Brand>)brandDao.selectByExample(brandQuery);
        return new PageResult(brandList.getTotal(), brandList.getResult());
    }
}
