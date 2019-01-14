// 定义服务层:
app.service("brandcheckService",function($http){
	this.findAll = function(){
		return $http.get("../brandcheck/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../brandcheck/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../brandcheck/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../brandcheck/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../brandcheck/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../brandcheck/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../brandcheck/search.do?page="+page+"&rows="+rows,searchEntity);
	}
	
	this.selectOptionList = function(){
		return $http.get("../brandcheck/selectOptionList.do");
	}
    this.updateStatus = function(ids,status){
        return $http.get('../brandcheck/updateStatus.do?ids='+ids+"&status="+status);
    }
});