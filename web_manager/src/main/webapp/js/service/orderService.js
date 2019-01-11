//订单
app.service('orderService',function($http){
    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../orderList/findAll.do');
    }
    //分页
    this.search=function(page,rows,searchEntity){
        return $http.get('../orderList/search.do?page='+page+'&rows='+rows);
    }
    //删除
    this.dele=function(outTradeNos){
        return $http.get('../orderList/delete.do?outTradeNos='+outTradeNos);
    }
});