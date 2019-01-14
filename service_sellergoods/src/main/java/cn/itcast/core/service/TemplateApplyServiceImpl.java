package cn.itcast.core.service;

import cn.itcast.core.dao.template.TypeTemplateDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.template.TypeTemplate;
import cn.itcast.core.pojo.template.TypeTemplateQuery;
import cn.itcast.core.service.TemplateApplyService;
import cn.itcast.core.util.Constants;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TemplateApplyServiceImpl implements TemplateApplyService {

    @Autowired
    TypeTemplateDao templateDao;


    //提交模板申请
    @Override
    public void add(TypeTemplate template) {
        //拼接审核未通过字段
        template.setName(template.getName()+ Constants.template_APPLY);
        templateDao.insert(template);

    }

    @Override
    public PageResult findPage(TypeTemplate template, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        TypeTemplateQuery query = new TypeTemplateQuery();
        TypeTemplateQuery.Criteria criteria = query.createCriteria();
        criteria.andNameNotLike("%"+Constants.template_APPLY+"%");
        if (template != null) {
            if (template.getName() != null && !"".equals(template.getName())){
                criteria.andNameLike("%"+template.getName()+"%");
            }
        }

        Page<TypeTemplate> templateList = (Page<TypeTemplate>)templateDao.selectByExample(query);
        return new PageResult(templateList.getTotal(), templateList.getResult());
    }
}
