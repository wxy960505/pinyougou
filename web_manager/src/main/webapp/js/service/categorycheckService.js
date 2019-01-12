// 定义服务层:
app.service("categorycheckService",function($http){
	this.findAll = function(){
		return $http.get("../catcheck/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../catcheck/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../catcheck/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../catcheck/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../catcheck/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../catcheck/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../catcheck/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../catcheck/selectOptionList.do");
	}
    this.updateStatus = function(ids,status){
        return $http.get('../catcheck/updateStatus.do?ids='+ids+"&status="+status);
    }
});