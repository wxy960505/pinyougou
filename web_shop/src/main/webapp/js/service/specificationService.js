//服务层
app.service('specificationService',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../spec/findAll.do');
    }

    //查询实体
    this.findOne=function(id){
        return $http.get('../spec/findOne.do?id='+id);
    }
    //增加
    this.add=function(entity){
        return  $http.post('../spec/add.do',entity );
    }
    //修改
    this.update=function(entity){
        return  $http.post('../spec/update.do',entity );
    }
    //删除
    this.dele=function(ids){
        return $http.get('../spec/delete.do?ids='+ids);
    }
    //搜索
    this.findPage=function(page,rows,searchEntity){
        return $http.post('../spec/findPage.do?page='+page+"&rows="+rows, searchEntity);
    }

    //
    this.findById=function (id) {
        return $http.get('../spec/findById.do?id='+id);
    }
});
