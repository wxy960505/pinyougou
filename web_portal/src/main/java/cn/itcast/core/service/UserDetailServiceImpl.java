package cn.itcast.core.service;

import cn.itcast.core.util.Constants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义认证类:
 * springSecurity和cas集成后, 用户名, 密码的判断认证工作交给cas来完成
 * springSecurity只负责cas验证完后, 给用户赋权工作
 * 如果能进入到这个实现类, 说明cas已经认证通过, 这里只做赋权操作
 */
public class UserDetailServiceImpl implements UserDetailsService {


    private UserService userService;

    public void setUserServic(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用userservice业务层得到数据  根据用户名获取整条数据
        List<cn.itcast.core.pojo.user.User> userList = userService.findUserName(username);
        String status = "";
        if (userList != null) {
            for (cn.itcast.core.pojo.user.User user : userList) {
                status = user.getStatus();
            }
        }
        //如果用户状态为n   不让登录
        if (status.equals( Constants.USER_STATUS2)) {
            //定义权限集合
            List<GrantedAuthority> authList = new ArrayList<>();
            authList.add(new SimpleGrantedAuthority("no_ROLE_USER"));
            return new User(username, "", authList);
        }else {
            userService.logins(username);
            //定义权限集合
            List<GrantedAuthority> authList = new ArrayList<>();


            //向权限集合中加入访问权限
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(username, "", authList);

        }


    }
}
