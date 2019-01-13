package cn.itcast.core.controller;

import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.entity.Result;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.service.TemplateApplyService;
import cn.itcast.core.service.TemplateService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TemplateController {

    @Reference
    private TemplateService templateService;

    @Reference
    private TemplateApplyService templateApplyService;

    @RequestMapping("/findOne")
    public TypeTemplate findOne(Long id) {
        TypeTemplate one = templateService.findOne(id);
        return one;
    }

    /**
     * 根据模板id, 获取规格和对应的规格选项集合数据
     * @param id
     * @return
     */
    @RequestMapping("/findBySpecList")
    public List<Map> findBySpecList(Long id) {
        List<Map> list = templateService.findBySpecList(id);
        return list;
    }


    @RequestMapping("/add")
    public Result add(@RequestBody TypeTemplate template){
        try {
            templateApplyService.add(template);
            return new Result(true,"提交申请成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"提交申请失败");
        }
    }

    @RequestMapping("/search")
    public PageResult search(@RequestBody TypeTemplate template, Integer page, Integer rows) {
        PageResult result = templateApplyService.findPage(template, page, rows);
        return result;
    }

}
