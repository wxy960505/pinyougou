package cn.itcast.core.service;

import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {
    @Autowired
    private SeckillGoodsDao seckillGoodsDao;
    @Override
    public SeckillGoods findOne(Long id) {
        SeckillGoods seckillGoods = seckillGoodsDao.selectByPrimaryKey(id);
        return seckillGoods;
    }

    @Override
    public List<SeckillGoods> findAll() {
        return seckillGoodsDao.selectByExample(null);
    }

    @Override
    public PageResult search(SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);

        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        if (seckillGoods.getTitle() != null) {
            criteria.andTitleLike(seckillGoods.getTitle());
        }
        Page<SeckillGoods> goods = (Page<SeckillGoods>)seckillGoodsDao.selectByExample(seckillGoodsQuery);
        return new PageResult(goods.getTotal(), goods.getResult());
    }

    //秒杀商品添加
    @Override
    public void add(SeckillGoods seckillGoods) {
        seckillGoods.setStatus("0");
        seckillGoodsDao.insertSelective(seckillGoods);
    }
}
