package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.BrandCheckService;
import cn.itcast.core.service.TemplateCheckService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/templatecheck")
public class TemplateCheckController {


    @Reference
    private TemplateCheckService TCService;


    /**
     * 修改商品状态
     * @param ids       品牌id
     * @param修改品牌上的马甲，脱掉，重新存入
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    // 更改数据库中品牌的审核状态
                    TCService.updateStatus(id, status);

                }
            }
            return new Result(true, "状态修改成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "状态修改失败!");
        }
    }

    /**
     * 品牌分页查询
     * @param page  当前页
     * @param rows  每页查询多少条数据
     * @return
     */


    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate template, Integer page, Integer rows) {
        PageResult result = TCService.findPage(template, page, rows);
        return result;
    }

}
