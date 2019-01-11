package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationQuery;
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
public class SpecCheckServiceImpl implements SpecCheckService {

    @Autowired
    private SpecificationDao specDao;
    @Autowired
    private JmsTemplate jmsTemplate;
    //用于商品上架
    @Autowired
    private ActiveMQTopic topicPageAndSolrDestination;

    @Override
    public void updateStatus(final Long id, String status) {
        if ("1".equals(status)) {
            Specification spec = specDao.selectByPrimaryKey(id);
            String name = spec.getSpecName();
            String[] split = name.split(Constants.CART_SHENHE);
            String s = split[0];
            Specification spec1 = new Specification();
            spec1.setSpecName(s);
            SpecificationQuery query = new SpecificationQuery();
            SpecificationQuery.Criteria criteria = query.createCriteria();
            criteria.andIdEqualTo(id);
            specDao.updateByExampleSelective(spec1, query);


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
            specDao.deleteByPrimaryKey(id);
        }

    }

    @Override
    public PageResult findPage(Specification spec, Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        SpecificationQuery specQuery = new SpecificationQuery();
        //创建where查询条件
        SpecificationQuery.Criteria criteria = specQuery.createCriteria();
        criteria.andSpecNameLike("%" + Constants.CART_SHENHE + "%");
        if (spec != null) {
            if (spec.getSpecName() != null && !"".equals(spec.getSpecName())) {
                criteria.andSpecNameLike("%" + spec.getSpecName() + "%");
            }

        }
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<Specification> specList = (Page<Specification>) specDao.selectByExample(specQuery);
        for (Specification spec1 : specList) {
            String[] split = spec1.getSpecName().split(Constants.CART_SHENHE);
            spec1.setSpecName(split[0]);
        }
        return new PageResult(specList.getTotal(), specList.getResult());
    }
}
