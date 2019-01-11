//控制层
app.controller('orderController' ,function($scope,$controller,orderService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        orderService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    /*$scope.findPage=function(page,rows){
        orderService.findPage(page,rows).success(
            function(response){
                $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }*/
    $scope.searchEntity={};

    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        orderService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }

    //批量删除
    $scope.dele=function(){
        //获取选中的复选框
        orderService.dele( $scope.selectIds ).success(
            function(response){
                if(response.success){
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }
});