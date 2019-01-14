package cn.itcast.core.service;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.user.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    public void sendCode(String phone);

    public Boolean checkSmsCode(String phone , String smsCode);

    public  void  add(User user);

    //查询用户数量,活跃用户和非活跃用户
    Map<String,Integer> findAll();



    //根据用户名查询该条数据
    public List<User> findUserName(String userName);


    //添加登录次数
    void logins(String username);


    //修改用户状态
    void updateStatus(Long id, String status);

    //用户回显并能高级查询
    PageResult findPage(User user, Integer page, Integer rows);
}
