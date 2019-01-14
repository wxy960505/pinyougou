 //控制层 
app.controller('sellerController' ,function($scope,$controller   ,sellerService){	
	
	$controller('baseController',{$scope:$scope});//继承
	
    //读取列表数据绑定到表单中  
	$scope.findAll=function(){
		sellerService.findAll().success(
			function(response){
				$scope.list=response;
			}			
		);
	}

    //商品excel导出
    $scope.expot=function(){
        alert("商品Excel导出");
        sellerService.expot().success(
            function(response){
                $scope.entity= response;
            }
        );
    }
    //订单excel导出
    $scope.oExpot=function(){
        alert("订单Excel导出");
        sellerService.oExpot().success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //品牌excel导入
    $scope.brandOut=function(){
        //alert("品牌Excel导入");
        sellerService.brandOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }
    //规格excel导入
    $scope.specOut=function(){
        //alert("规格Excel导入");
        sellerService.specOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }
    //模板excel导入
    $scope.templateOut=function(){
        //alert("模板Excel导入");
        sellerService.templateOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }
    //分类excel导入
    $scope.categoryOut=function(){
        //alert("分类Excel导入");
        sellerService.categoryOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }

    //商品excel导出
    $scope.expot=function(){
        alert("商品Excel导出");
        sellerService.expot().success(
            function(response){
                $scope.entity= response;
            }
        );
    }
    //订单excel导出
    $scope.oExpot=function(){
        alert("订单Excel导出");
        sellerService.oExpot().success(
            function(response){
                $scope.entity= response;
            }
        );
    }

    //品牌excel导入
    $scope.brandOut=function(){
        //alert("品牌Excel导入");
        sellerService.brandOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }
    //规格excel导入
    $scope.specOut=function(){
        //alert("规格Excel导入");
        sellerService.specOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }
    //模板excel导入
    $scope.templateOut=function(){
        //alert("模板Excel导入");
        sellerService.templateOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }
    //分类excel导入
    $scope.categoryOut=function(){
        //alert("分类Excel导入");
        sellerService.categoryOut().success(
            function(response){
                $scope.entity= response;
                alert(response.message);
            }
        );
    }



    //分页
	$scope.findPage=function(page,rows){			
		sellerService.findPage(page,rows).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
	
	//查询实体 
	$scope.findOne=function(id){				
		sellerService.findOne(id).success(
			function(response){
				$scope.entity= response;					
			}
		);				
	}
	
	//保存 
	$scope.save=function(){				
		var serviceObject;//服务层对象  				
		if($scope.entity.id!=null){//如果有ID
			serviceObject=sellerService.update( $scope.entity ); //修改  
		}else{
			serviceObject=sellerService.add( $scope.entity  );//增加 
		}				
		serviceObject.success(
			function(response){
				if(response.success){
					//重新查询 
		        	$scope.reloadList();//重新加载
				}else{
					alert(response.message);
				}
			}		
		);				
	}
	
	 
	//批量删除 
	$scope.dele=function(){			
		//获取选中的复选框			
		sellerService.dele( $scope.selectIds ).success(
			function(response){
				if(response.success){
					$scope.reloadList();//刷新列表
					$scope.selectIds = [];
				}						
			}		
		);				
	}
	
	$scope.searchEntity={};//定义搜索对象 
	
	//搜索
	$scope.search=function(page,rows){			
		sellerService.search(page,rows,$scope.searchEntity).success(
			function(response){
				$scope.list=response.rows;	
				$scope.paginationConf.totalItems=response.total;//更新总记录数
			}			
		);
	}
    
	$scope.updateStatus = function(sellerId,status){
		sellerService.updateStatus(sellerId,status).success(function(response){
			if(response.success){
				//重新查询 
	        	$scope.reloadList();//重新加载
			}else{
				alert(response.message);
			}
		});
	}
});	
