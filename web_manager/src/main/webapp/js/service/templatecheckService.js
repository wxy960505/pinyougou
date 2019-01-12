// 定义服务层:
app.service("templatecheckService",function($http){
	this.findAll = function(){
		return $http.get("../templatecheck/findAll.do");
	}
	
	this.findByPage = function(page,rows){
		return $http.get("../templatecheck/findByPage.do?page="+page+"&rows="+rows);
	}
	
	this.save = function(entity){
		return $http.post("../templatecheck/add.do",entity);
	}
	
	this.update=function(entity){
		return $http.post("../templatecheck/update.do",entity);
	}
	
	this.findById=function(id){
		return $http.get("../templatecheck/findOne.do?id="+id);
	}
	
	this.dele = function(ids){
		return $http.get("../templatecheck/delete.do?ids="+ids);
	}
	
	this.search = function(page,rows,searchEntity){
		return $http.post("../templatecheck/search.do?page="+page+"&rows="+rows,searchEntity);
	}
    this.updateStatus = function(ids,status){
        return $http.get('../templatecheck/updateStatus.do?ids='+ids+"&status="+status);
    }
});