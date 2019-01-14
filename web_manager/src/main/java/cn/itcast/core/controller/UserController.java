package cn.itcast.core.controller;


import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.user.User;
import cn.itcast.core.service.UserService;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    //查询用户数量  活跃用户和非活跃用户
    @RequestMapping("/findAll")
    public Map<String, Integer> findAll() {
        Map<String, Integer> map = userService.findAll();

        return map;

    }

    //用户回显并能高级查询
    @RequestMapping("/search")
    public PageResult search(@RequestBody User user, Integer page, Integer rows) {
        PageResult result = userService.findPage(user, page, rows);
        return result;

    }

    //冻结或解冻用户
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] selectIds, String status) {
        try {
            if (selectIds != null) {
                for (Long id : selectIds) {
                    userService.updateStatus(id,status);
                }
                return new Result(true,"修改状态成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return new Result(false,"修改状态失败!");
    }


}
