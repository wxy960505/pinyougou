//控制层
app.controller('seckillappLicationController' ,function($scope,$controller,$location ,seckillappLicationService){

    $controller('baseController',{$scope:$scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        seckillappLicationService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage=function(page,rows){
        seckillappLicationService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne=function(){
        var id = $location.search()['id'];
        // alert(id);
        seckillappLicationService.findOne(id).success(
            function(response){
            }
        );
    }

    //保存
    $scope.save=function(){
        // 再添加之前，获得富文本编辑器中的内容。
        $scope.entity.goodsDesc.introduction=editor.html();
        var serviceObject;//服务层对象
        if($scope.entity.goods.id!=null){//如果有ID
            serviceObject=seckillappLicationService.update( $scope.entity ); //修改
        }else{
            serviceObject=seckillappLicationService.add( $scope.entity  );//增加
        }
        serviceObject.success(
            function(response){
                if(response.success){
                    //重新查询
                    alert(response.message);
                    location.href="seckill.html";
                }else{
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        seckillappLicationService.dele( $scope.selectIds ).success(
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

        seckillappLicationService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }


    $scope.itemCatList = [];
// 显示秒杀商品信息:
    $scope.findItemCatList = function(){
        sellerController.findAll().success(function(response){
            for(var i=0;i<response.length;i++){
                $scope.itemCatList[response[i].sellerId] = response[i].name;
            }
        });
    }



    $scope.SellerList={data:[]}
    // 查询关联的品牌信息:
    $scope.findSellerList = function(){
        sellerService.selectOptionList().success(function(response){
            $scope.SellerList = {data:response};
        });
    }

});
