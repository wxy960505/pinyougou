//服务层
app.service('seckillappLicationService',function($http){

    //读取列表数据绑定到表单中
    this.findAll=function(){
        return $http.get('../seckillappLication/findAll.do');
    }
    //分页
    this.findPage=function(page,rows){
        return $http.get('../seckillappLication/findPage.do?page='+page+'&rows='+rows);
    }
    //查询实体
    this.findOne=function(id){
        return $http.get('../seckillappLication/findOne.do?id='+id);
    }
    //增加
    this.add=function(entity){
        return  $http.post('../seckillappLication/add.do',entity );
    }
    //修改
    this.update=function(entity){
        return  $http.post('../seckillappLication/update.do',entity );
    }
    //删除
    this.dele=function(ids){
        return $http.get('../seckillappLication/delete.do?ids='+ids);
    }
    //搜索
    this.search=function(page,rows,searchEntity){
        return $http.post('../seckillappLication/search.do?page='+page+"&rows="+rows, searchEntity);
    }

    this.findByTime=function(){
        return $http.get('../seckillappLication/findAllTime.do');
    }


});
