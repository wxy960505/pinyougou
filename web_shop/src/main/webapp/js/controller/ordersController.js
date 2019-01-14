app.controller('ordersController' ,function($scope,$controller,ordersService) {

    $controller('baseController', {$scope: $scope});//继承


    // 发货的方法:
    $scope.updateStatus = function(status){
        ordersService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.success){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }

    $scope.searchEntity={};//定义搜索对象

    // 显示状态
    $scope.status = ["","未付款","已付款","未发货","已发货","交易成功","交易关闭","待评价"];
    // 显示订单来源
    $scope.sourceType = ["app端","pc端","M端","微信端","手机qq端"];
    //分页搜索
    $scope.search=function(page,rows){
        ordersService.search(page,rows,$scope.searchEntity).success(
            function(response){
                 $scope.list=response.rows;
                $scope.paginationConf.totalItems=response.total;//更新总记录数
            }
        );
    }
});