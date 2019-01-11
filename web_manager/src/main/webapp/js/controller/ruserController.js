app.controller("ruserController",function($scope,$controller,$http,ruserService) {

    $controller('baseController',{$scope:$scope});//继承

    //查询用户数量 活跃用户和非活跃用户
        $scope.findAll = function(){
            ruserService.findAll().success(function(response){
                $scope.list=response;
            });
    };

    //用户回显并高级查询
    $scope.searchEntity={};
    // 假设定义一个查询的实体：searchEntity
    $scope.search = function(page,rows){
        // 向后台发送请求获取数据:
        ruserService.search(page,rows,$scope.searchEntity).success(function(response){
            $scope.paginationConf.totalItems = response.total;
            $scope.list = response.rows;
        });
    }

    // 修改用户状态:
    $scope.updateStatus = function(status){

        ruserService.updateStatus($scope.selectIds,status).success(function(response){
            if(response.success){
                $scope.reloadList();//刷新列表
                $scope.selectIds = [];
            }else{
                alert(response.message);
            }
        });
    }
});


