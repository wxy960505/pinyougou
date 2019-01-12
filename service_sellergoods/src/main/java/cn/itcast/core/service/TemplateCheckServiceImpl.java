package cn.itcast.core.service;

import cn.itcast.core.dao.specification.SpecificationOptionDao;
import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.pojo.specification.SpecificationOptionQuery;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TemplateCheckServiceImpl implements TemplateCheckService {
    @Autowired
    private TypeTemplateDao templateDao;

    @Autowired
    private SpecificationOptionDao optionDao;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private JmsTemplate jmsTemplate;
    //用于商品上架
    @Autowired
    private ActiveMQTopic topicPageAndSolrDestination;

    @Override
    public void updateStatus(final Long id, String status) {
        if ("1".equals(status)) {
            TypeTemplate TT = templateDao.selectByPrimaryKey(id);
            String name = TT.getName();
            String[] split = name.split(Constants.template_APPLY);
            String s = split[0];
            TypeTemplate typeTemplate = new TypeTemplate();
            typeTemplate.setName(s);
            TypeTemplateQuery query = new TypeTemplateQuery();
            TypeTemplateQuery.Criteria criteria = query.createCriteria();
            criteria.andIdEqualTo(id);
            templateDao.updateByExampleSelective(typeTemplate, query);


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
            templateDao.deleteByPrimaryKey(id);
        }

    }

    @Override
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows) {
        //1. 查询所有模板数据
        List<TypeTemplate> typeTemplates = templateDao.selectByExample(null);

        for (TypeTemplate typeTemplate : typeTemplates) {
            //2. 模板id作为key, 对应的品牌集合作为value, 缓存入redis中
            String brandIdsJsonStr = typeTemplate.getBrandIds();
            List<Map> brandList = JSON.parseArray(brandIdsJsonStr, Map.class);
            //缓存品牌集合数据
            redisTemplate.boundHashOps(Constants.BRAND_LIST_REDIS).put(typeTemplate.getId(), brandList);

            //3. 模板id作为key, 对应的规格集合作为value, 缓存入redis中
            List<Map> specList = findBySpecList(typeTemplate.getId());
            redisTemplate.boundHashOps(Constants.SPEC_LIST_REDIS).put(typeTemplate.getId(), specList);
        }



        //4. 分页查询, 并且将数据返回到页面展示
        PageHelper.startPage(page, rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = query.createCriteria();
        if (template != null) {
            if (template.getName() != null && !"".equals(template.getName())){
                criteria.andNameLike("%"+template.getName()+"%");
            }
        }
        criteria.andNameLike("%" + Constants.template_APPLY + "%");

        Page<TypeTemplate> templateList = (Page<TypeTemplate>)templateDao.selectByExample(query);
        for (TypeTemplate tt : templateList) {
            String[] split = tt.getName().split(Constants.template_APPLY);
            tt.setName(split[0]);
        }
        return new PageResult(templateList.getTotal(), templateList.getResult());
    }
    @Override
    public List<Map> findBySpecList(Long id) {
        //1. 根据模板id查询对应的模板对象
        TypeTemplate typeTemplate = templateDao.selectByPrimaryKey(id);
        //2. 从模板对象中获取规格集合的json数据(字符串类型)
        String specIds = typeTemplate.getSpecIds();
        //3. 将json字符串类型的规格集合转换成Java对象,
        //使用阿里的json转化工具, 将json字符串转换成list集合, 第一个参数是转换的字符串, 第二个参数指定集合泛型的类型
        //这是一个map例如: id:1, text网络      这有是一个map,例如: id:32, text: 机身网络
        List<Map> maps = JSON.parseArray(specIds, Map.class);
        //4. 遍历规格集合数据
        if (maps != null) {
            for (Map map : maps) {
                //5. 在遍历的过程中将根据规格id获取对应的规格选项集合, 并且封装到规格对象中
                Long specId= Long.parseLong(String.valueOf(map.get("id")));

                SpecificationOptionQuery query = new SpecificationOptionQuery();
                SpecificationOptionQuery.Criteria criteria = query.createCriteria();
                criteria.andSpecIdEqualTo(specId);
                //根据规格id查询到规格选项集合
                List<SpecificationOption> optionList = optionDao.selectByExample(query);
                map.put("options", optionList);
            }
        }

        return maps;
    }
}
