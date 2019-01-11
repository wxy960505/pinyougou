package cn.itcast.core.service;

import cn.itcast.core.dao.good.BrandDao;
import cn.itcast.core.dao.log.PayLogDao;
import cn.itcast.core.pojo.entity.PageResult;
import cn.itcast.core.pojo.good.Brand;
import cn.itcast.core.pojo.good.BrandQuery;
import cn.itcast.core.pojo.log.PayLog;
import cn.itcast.core.pojo.log.PayLogQuery;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import sun.net.www.http.Hurryable;

import java.util.List;
import java.util.Map;

@Service
public class OrderListServiceImpl implements OrderListService {

    @Autowired
    private PayLogDao payLogDao;

    @Override
    public List<PayLog> findAll() {
        List<PayLog> payLogs = payLogDao.selectByExample(null);
        return payLogs;
    }

    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<PayLog> payLogList = (Page<PayLog>)payLogDao.selectByExample(null);
        return new PageResult(payLogList.getTotal(), payLogList.getResult());
    }

    /*@Override
    public PageResult findPage(PayLog payLog,Integer page, Integer rows) {
        //使用分页助手, 传入当前页和每页查询多少条数据
        PageHelper.startPage(page, rows);
        //创建查询对象
        PayLogQuery payLogQuery = new PayLogQuery();
        //创建where查询条件
        PayLogQuery.Criteria criteria = payLogQuery.createCriteria();
        if (payLog != null) {
            if (payLog.getUserId() != null && !"".equals(payLog.getUserId())){
                criteria.andUserIdEqualTo("%"+payLog.getUserId()+"%");
            }
        }
        //使用分页助手的page对象接收查询到的数据, page对象继承了ArrayList所以可以接收查询到的结果集数据.
        Page<PayLog> payLogList = (Page<PayLog>)payLogDao.selectByExample(payLogQuery);
        return new PageResult(payLogList.getTotal(), payLogList.getResult());
    }*/


    @Override
    public void delete(String[] outTradeNos) {
        if (outTradeNos != null) {
            for(String outTradeNo : outTradeNos){
                payLogDao.deleteByPrimaryKey(outTradeNo);
            }
        }

    }
}
