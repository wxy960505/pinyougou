package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.item.ItemCatDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.item.ItemCat;
import cn.itcast.core.pojo.item.ItemCatQuery;
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
public class categoryCheckServiceImpl implements categoryCheckService {
    @Autowired
    private ItemCatDao catDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    //用于商品上架
    @Autowired
    private ActiveMQTopic topicPageAndSolrDestination;

    @Override
    public void updateStatus(final Long id, String status) {
        if ("1".equals(status)) {
            ItemCat ic = catDao.selectByPrimaryKey(id);
            String name = ic.getName();
            String[] split = name.split(Constants.CART_SHENHE);
            String s = split[0];
            ItemCat ic1 = new ItemCat();
            ic1.setName(s);
            ItemCatQuery query = new ItemCatQuery();
            ItemCatQuery.Criteria criteria = query.createCriteria();
            criteria.andIdEqualTo(id);
            catDao.updateByExampleSelective(ic1, query);


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
            catDao.deleteByPrimaryKey(id);
        }

    }

    @Override
    public PageResult findPage(ItemCat itemCat, Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        ItemCatQuery itquery = new ItemCatQuery();
        //创建where查询条件
        ItemCatQuery.Criteria criteria = itquery.createCriteria();
        criteria.andNameLike("%"+Constants.CART_SHENHE+"%");
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<ItemCat> ItemcatList = (Page<ItemCat>)catDao.selectByExample(itquery);
        for (ItemCat ItemCat1 : ItemcatList) {
            String[] split = ItemCat1.getName().split(Constants.CART_SHENHE);
            ItemCat1.setName(split[0]);
        }
        return new PageResult(ItemcatList.getTotal(), ItemcatList.getResult());
    }
}
