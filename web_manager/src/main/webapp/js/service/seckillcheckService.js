// 定义服务层:
app.service("seckillcheckService",function($http){
	this.findAll = function(){
		return $http.get("../seckillcheck/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../seckillcheck/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../seckillcheck/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../seckillcheck/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../seckillcheck/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../seckillcheck/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../seckillcheck/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../seckillcheck/selectOptionList.do");
	}
    this.updateStatus = function(ids,status){
        return $http.get('../seckillcheck/updateStatus.do?ids='+ids+"&status="+status);
    }
});