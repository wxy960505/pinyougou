package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.service.FollowService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Reference
    private FollowService followService;

    @RequestMapping("/addFollow")
        public Result addFollow(Long id){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            followService.addFollow(id,userName);
            return new Result(true,"添加关注成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"添加关注失败!");
        }
    }
}
