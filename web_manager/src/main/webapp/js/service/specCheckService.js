// 定义服务层:
app.service("specCheckService",function($http){
	this.findAll = function(){
		return $http.get("../specCheck/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../specCheck/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../specCheck/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../specCheck/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../specCheck/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../specCheck/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../specCheck/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../specCheck/selectOptionList.do");
	}
    this.updateStatus = function(ids,status){
        return $http.get('../specCheck/updateStatus.do?ids='+ids+"&status="+status);
    }
});