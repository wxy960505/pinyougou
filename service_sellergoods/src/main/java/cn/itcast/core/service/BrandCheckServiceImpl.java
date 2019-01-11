package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
@Transactional
public class BrandCheckServiceImpl implements BrandCheckService {
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    //用于商品上架
    @Autowired
    private ActiveMQTopic topicPageAndSolrDestination;

    @Override
    public void updateStatus(final Long id, String status) {
        if ("1".equals(status)) {
            Brand brand2 = brandDao.selectByPrimaryKey(id);
            String name = brand2.getName();
            String[] split = name.split(Constants.BRAND_APPLY);
            String s = split[0];
            Brand brand1 = new Brand();
            brand1.setName(s);
            BrandQuery query = new BrandQuery();
            BrandQuery.Criteria criteria = query.createCriteria();
            criteria.andIdEqualTo(id);
            brandDao.updateByExampleSelective(brand1, query);


            /**
             * 判断如果审核通过, 将商品id作为消息发送给消息服务器
             */

                //发送消息, 第一个参数是发送到的队列, 第二个参数是一个接口, 定义发送的内容
                jmsTemplate.send(topicPageAndSolrDestination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage textMessage = session.createTextMessage(String.valueOf(id));
                        return textMessage;
                    }
                });

        } else {
            brandDao.deleteByPrimaryKey(id);
        }

    }

    @Override
    public PageResult findPage(Brand brand, Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        BrandQuery brandQuery = new BrandQuery();
        //创建where查询条件
        BrandQuery.Criteria criteria = brandQuery.createCriteria();
        criteria.andNameLike("%"+Constants.BRAND_APPLY+"%");
        if (brand != null) {
            if (brand.getName() != null && !"".equals(brand.getName())){
                criteria.andNameLike("%"+brand.getName()+"%");
            }
            if (brand.getFirstChar() != null && !"".equals(brand.getFirstChar())) {
                criteria.andFirstCharLike("%"+brand.getFirstChar()+"%");
            }
        }
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<Brand> brandList = (Page<Brand>)brandDao.selectByExample(brandQuery);
        for (Brand brand1 : brandList) {
            String[] split = brand1.getName().split(Constants.BRAND_APPLY);
            brand1.setName(split[0]);
        }
        return new PageResult(brandList.getTotal(), brandList.getResult());
    }
}
