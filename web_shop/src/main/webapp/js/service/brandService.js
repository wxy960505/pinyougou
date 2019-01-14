// 定义服务层:
app.service("brandService",function($http){
	this.findAll = function(){
		return $http.get("../brandApply/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../brandApply/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../brandApply/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../brandApply/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../brandApply/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../brandApply/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../brandApply/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../brand/selectOptionList.do");
	}
});