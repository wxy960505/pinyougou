
app.service("ruserService",function($http){

    //查询用户数量 活跃用户和非活跃用户
    this.findAll = function(){
        return $http.get('../user/findAll.do');
    };

    //用户回显
    this.search = function(page,rows,searchEntity){
        return $http.post("../user/search.do?page="+page+"&rows="+rows,searchEntity);
    };

    //修改状态
    this.updateStatus = function(selectIds,status){
        return $http.post("../user/updateStatus.do?selectIds="+selectIds+"&status="+status);
    }
});
