package cn.itcast.core.service;

import cn.itcast.core.dao.user.UserDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationQuery;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.pojo.user.UserQuery;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JmsTemplate jmsTemplate;

    //点对点发送, 向这个目标中发送
    @Autowired
    private ActiveMQQueue smsDestination;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${template_code}")
    private String template_code;

    @Value("${sign_name}")
    private String sign_name;

    @Autowired
    private UserDao userDao;

    @Override
    public void sendCode(final String phone) {

        //1. 生成随机6为数字作为验证码
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < 7; i++) {
            sb.append(new Random().nextInt(10));
        }

        final String smsCode = sb.toString();

        //2. 手机号作为key, 验证码最为value存入redis中, 有效时间为10分钟
        redisTemplate.boundValueOps(phone).set(sb.toString(), 60 * 10, TimeUnit.SECONDS);

        //3. 将手机号, 验证码, 模板编号, 签名封装成Map类型的消息发送给消息服务器
        jmsTemplate.send(smsDestination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("mobile", phone);//手机号
                message.setString("template_code", template_code);//模板编码
                message.setString("sign_name", sign_name);//签名
                Map map = new HashMap();
                map.put("code", smsCode);    //验证码
                message.setString("param", JSON.toJSONString(map));
                return (Message) message;
            }
        });

    }

    @Override
    public Boolean checkSmsCode(String phone, String smsCode) {
        //1. 判断如果手机号或者验证码为空, 直接返回false
        if (phone == null || smsCode == null || "".equals(phone) || "".equals(smsCode)) {
            return false;
        }
        //2. 根据手机号到redis中获取我们自己的验证码
        String redisSmsCode = (String) redisTemplate.boundValueOps(phone).get();

        //3. 根据页面传入的验证码和我们自己存的验证码对比是否正确
        if (smsCode.equals(redisSmsCode)) {
            return true;
        }

        return false;
    }

    @Override
    public void add(User user) {
        userDao.insertSelective(user);
    }





    //查询用户数量,活跃用户和非活跃用户
    @Override
    public Map<String, Integer> findAll() {

        Map<String, Integer> map = new HashMap<>();


        List<User> userList = userDao.selectByExample(null);

        //用户数量
        if (userList != null) {
            int size = userList.size();
            map.put("size", size);
        }

        //活跃用户 登录大于5次  数据库记录登录次数字段:ExperienceValue
        Integer active = 1;
        Integer unactive = 1;
        if (userList != null) {
            for (User user : userList) {
                if (user.getExperienceValue() >= 5) {
                    map.put("active", active++);
                } else {
                    //非活跃用户  登录小于5次
                    map.put("unactive", unactive++);
                }
            }

        }
        return map;
    }

    //添加登录次数
    @Override
    public void logins(String username) {
        if (username != null) {
            //创建查询条件
            UserQuery query = new UserQuery();
            UserQuery.Criteria criteria = query.createCriteria();
            //根据用户名查询该条数据
            criteria.andUsernameEqualTo(username);
            List<User> userList = userDao.selectByExample(query);

            for (User user : userList) {
                if (user.getExperienceValue()==null) {
                    user.setExperienceValue(1); //初始化次数
                    userDao.updateByPrimaryKeySelective(user);
                }
                int i = user.getExperienceValue() + 1;
                user.setExperienceValue(i);   //把修改后的次数放回user对象中
                userDao.updateByPrimaryKeySelective(user);  //把user对象更新到数据库
            }

        }
    }




    //用户回显
    @Override
    public PageResult findPage(User user, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        UserQuery query1 = new UserQuery();
        UserQuery.Criteria criteria1 = query1.createCriteria();
        if (user != null) {
            if (user.getUsername()!= null && !"".equals(user.getUsername())) {
                criteria1.andUsernameLike("%"+user.getUsername()+"%");
            }
        }
        Page<User> userList = (Page<User>)userDao.selectByExample(query1);
        return new PageResult(userList.getTotal(), userList.getResult());

    }


    //根据用户名查询该条数据
    @Override
    public List<User> findUserName(String userName) {
        //创建查询对象
        UserQuery query = new UserQuery();
        //创建条件对象
        UserQuery.Criteria criteria = query.createCriteria();
        //添加条件
        criteria.andUsernameEqualTo(userName);
        //查询数据返回
        return userDao.selectByExample(query);
    }


    //修改用户状态
    @Override
    public void updateStatus(Long id, String status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        userDao.updateByPrimaryKeySelective(user);
    }


}
