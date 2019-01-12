// 定义控制器:
app.controller("templatecheckController",function($scope,$controller,$http, brandService ,specificationService  ,templatecheckService){
	// AngularJS中的继承:伪继承
	$controller('baseController',{$scope:$scope});
	
	// 查询所有的品牌列表的方法:
	$scope.findAll = function(){
		// 向后台发送请求:
        templatecheckService.findAll().success(function(response){
			$scope.list = response;
		});
	}

	// 分页查询
	$scope.findByPage = function(page,rows){
		// 向后台发送请求获取数据:
        templatecheckService.findByPage(page,rows).success(function(response){
			$scope.paginationConf.totalItems = response.total;
			$scope.list = response.rows;
		});
	}
	
	// 保存品牌的方法:
	$scope.save = function(){
		// 区分是保存还是修改
		var object;
		if($scope.entity.id != null){
			// 更新
			object = templatecheckService.update($scope.entity);
		}else{
			// 保存
			object = templatecheckService.save($scope.entity);
		}
		object.success(function(response){
			// {success:true,message:xxx}
			// 判断保存是否成功:
			if(response.success==true){
				// 保存成功
				alert(response.message);
				$scope.reloadList();
			}else{
				// 保存失败
				alert(response.message);
			}
		});
	}
	
	// 查询一个:
	$scope.findById = function(id){
        templatecheckService.findById(id).success(function(response){
			// {id:xx,name:yy,firstChar:zz}
			$scope.entity = response;
		});
	}
	
	// 删除品牌:
	$scope.dele = function(){
        templatecheckService.dele($scope.selectIds).success(function(response){
			// 判断保存是否成功:
			if(response.success==true){
				// 保存成功
				// alert(response.message);
				$scope.reloadList();
				$scope.selectIds = [];
			}else{
				// 保存失败
				alert(response.message);
			}
		});
	}

    $scope.searchEntity={};//定义搜索对象

    //搜索
    $scope.search=function(page,rows){
        templatecheckService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //$scope.brandList={data:[{id:1,text:'联想'},{id:2,text:'华为'},{id:3,text:'小米'}]};//品牌列表
    $scope.brandList={data:[]}
    // 查询关联的品牌信息:
    $scope.findBrandList = function(){
        brandService.selectOptionList().success(function(response){
            $scope.brandList = {data:response};
        });
    }


    $scope.specList={data:[]}
    // 查询关联的品牌信息:
    $scope.findSpecList = function(){
        specificationService.selectOptionList().success(function(response){
            $scope.specList = {data:response};
        });
    }



    // 审核的方法:
    $scope.updateStatus = function(status){
        templatecheckService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.success){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }
	
});
