//服务层
app.service('sellerService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../seller/findAll.do');		
	}
    //商品导出
    this.expot=function(){
        return $http.get('../goods/goodsExport.do');
    }
    //订单导出
    this.oExpot=function(){
        return $http.get('../order/orderExport.do');
    }
    //商品导出
    this.expot=function(){
        return $http.get('../goods/goodsExport.do');
    }
    //订单导出
    this.oExpot=function(){
        return $http.get('../order/orderExport.do');
    }
    //品牌导入
    this.brandOut=function(){
        return $http.get('../derived/brandGet.do');
    }
    //规格导入
    this.specOut=function(){
        return $http.get('../derived/specGet.do');
    }
    //模板导入
    this.templateOut=function(){
        return $http.get('../derived/templateGet.do');
    }
    //分类导入
    this.categoryOut=function(){
        return $http.get('../derived/categoryGet.do');
    }
    //品牌导入
    this.brandOut=function(){
        return $http.get('../derived/brandGet.do');
    }
    //规格导入
    this.specOut=function(){
        return $http.get('../derived/specGet.do');
    }
    //模板导入
    this.templateOut=function(){
        return $http.get('../derived/templateGet.do');
    }
    //分类导入
    this.categoryOut=function(){
        return $http.get('../derived/categoryGet.do');
    }
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../seller/findPage.do?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../seller/findOne.do?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../seller/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../seller/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../seller/delete.do?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../seller/search.do?page='+page+"&rows="+rows, searchEntity);
	}    
	
	this.updateStatus = function(sellerId,status){
		return $http.get('../seller/updateStatus.do?sellerId='+sellerId+"&status="+status);
	}
});
