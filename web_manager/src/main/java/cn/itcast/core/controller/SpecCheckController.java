package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.service.SpecCheckService;
import cn.itcast.core.service.SpecService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 规格管理
 */
@RestController
@RequestMapping("/specCheck")
public class SpecCheckController {

    @Reference
    private SpecCheckService specCheckService;

    /**
     * 修改商品状态
     * @param ids       规格id
     * @param修改规格上的马甲，脱掉，重新存入
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(Long[] ids, String status) {
        try {
            if (ids != null) {
                for (Long id : ids) {
                    // 更改数据库中商品的审核状态
                    specCheckService.updateStatus(id, status);

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
    public PageResult search(@RequestBody Specification  spec, Integer page, Integer rows) {
        PageResult result = specCheckService.findPage(spec, page, rows);
        return result;
    }
}
