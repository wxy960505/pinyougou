//服务层
app.service('ordersService',function($http) {
//批量发货
    this.updateStatus = function(selectIds,status){
        return $http.get('../orders/updateStatus.do?selectIds='+selectIds+"&status="+status);
    }

//搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../orders/search.do?page='+page+"&rows="+rows, searchEntity);
    }
});