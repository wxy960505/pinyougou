package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.item.ItemDao;
import cn.itcast.core.dao.seckill.SeckillGoodsDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.good.Goods;
import cn.itcast.core.pojo.good.GoodsQuery;
import cn.itcast.core.pojo.item.Item;
import cn.itcast.core.pojo.item.ItemQuery;
import cn.itcast.core.pojo.seckill.SeckillGoods;
import cn.itcast.core.pojo.seckill.SeckillGoodsQuery;
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
public class SeckillCheckServiceImpl implements SeckillCheckService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    private SeckillGoodsDao secDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    //用于商品上架
    @Autowired
    private ActiveMQTopic topicPageAndSolrDestination;

    @Override
    public void updateStatus(final Long id, String status) {
        /**
         * 根据商品id改变数据库中商品的上架状态
         */
        //1. 修改商品状态
        SeckillGoods goods = new SeckillGoods();
        goods.setId(id);
        goods.setStatus(status);
        secDao.updateByPrimaryKeySelective(goods);

        //2. 修改库存状态
        Item item = new Item();
        item.setStatus(status);

        ItemQuery query = new ItemQuery();
        ItemQuery.Criteria criteria = query.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        itemDao.updateByExampleSelective(item, query);


        /**
         * 判断如果审核通过, 将商品id作为消息发送给消息服务器
         */
        if ("1".equals(status)) {
            //发送消息, 第一个参数是发送到的队列, 第二个参数是一个接口, 定义发送的内容
            jmsTemplate.send(topicPageAndSolrDestination, new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    TextMessage textMessage = session.createTextMessage(String.valueOf(id));
                    return textMessage;
                }
            });
        }
    }
    @Override
    public PageResult findPage(SeckillGoods seckillGoods, Integer page, Integer rows) {
        PageHelper.startPage(page, rows );

        SeckillGoodsQuery seckillGoodsQuery = new SeckillGoodsQuery();
        SeckillGoodsQuery.Criteria criteria = seckillGoodsQuery.createCriteria();
        criteria.andStatusEqualTo("0");

        if (seckillGoods != null) {

            if (seckillGoods.getTitle() != null && !"".equals(seckillGoods.getTitle() )) {
                criteria.andTitleLike("%"+seckillGoods.getTitle() +"%");
            }
            if (seckillGoods.getSellerId() != null && !"".equals(seckillGoods.getSellerId())
                    && !"admin".equals(seckillGoods.getSellerId()) && !"wc".equals(seckillGoods.getSellerId())) {
                criteria.andSellerIdEqualTo(seckillGoods.getSellerId());
            }
        }
        Page<SeckillGoods> seckillList = (Page<SeckillGoods>)secDao.selectByExample(seckillGoodsQuery);
        return new PageResult(seckillList.getTotal(), seckillList.getResult());
    }
}
