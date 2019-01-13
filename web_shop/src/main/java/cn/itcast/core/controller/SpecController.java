package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.entity.SpecEntity;
import cn.itcast.core.pojo.specification.Specification;
import cn.itcast.core.pojo.specification.SpecificationOption;
import cn.itcast.core.service.SpecService;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/spec")
public class SpecController {
    @Reference
    SpecService specService;

    //分页，搜索，查询
    @RequestMapping("/findPage")
    public PageResult findAll(@RequestBody Specification specification, Integer page, Integer rows) {
        PageResult pageResult = specService.findPage(specification, page, rows);

        System.out.println(pageResult.getRows());
        return pageResult;
    }

    @RequestMapping("/update")
    public Result update(@RequestBody SpecEntity specEntity) {
        Specification specification = specEntity.getSpecification();
        specification.setSpecName(specification.getSpecName() + Constants.CART_SHENHE);
        specEntity.setSpecification(specification);
        try {
            specService.update(specEntity);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    @RequestMapping("/findOne")
    public SpecEntity findById(Long id) {
        SpecEntity one = specService.findOne(id);
        return one;

    }

    @RequestMapping("/add")
    public Result add(@RequestBody SpecEntity specEntity) {
        Specification specification = specEntity.getSpecification();
        String specName = specification.getSpecName();
        specification.setSpecName(specName + Constants.CART_SHENHE);
        specEntity.setSpecification(specification);
        try {
            specService.add(specEntity);
            return new Result(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "添加失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            specService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList() {
        List<Map> maps = specService.selectOptionList();
        return maps;
    }

}
