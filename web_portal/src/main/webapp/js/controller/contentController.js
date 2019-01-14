app.controller("contentController",function($scope,contentService){
	$scope.contentList = [];

    $scope.item_cat_list = []; //所有分类
    //$scope.item_cat_list = [{一级分类ID：id,name:name sub[{二级分类ID：id,name:name,sub[{三级分类id:id,name:name}]}...]}...];
    //$scope.item_cat_all = [];
    //$scope.item_cat_greade = 0;


	// 根据分类ID查询广告的方法:
	$scope.findByCategoryId = function(categoryId){
		contentService.findByCategoryId(categoryId).success(function(response){
            $scope.contentList[categoryId] = response;
        });

        contentService.findByCategoryId(4).success(function(response){
            $scope.contentList[4] = response;
        });

        contentService.findByCategoryId(5).success(function(response){
            $scope.contentList[5] = response;
        });



	}
	
	//搜索,跳转到portal系统查询列表页面(传递参数）
	$scope.search=function(){
		location.href="http://localhost:8080/search.html#?keywords="+$scope.keywords;
	}



	//查询分类
    $scope.findPortolCategory = function(){
        contentService.findPortolCategory().success(function(response){
            $scope.item_cat_list = response;
        });

        /*alert("xxoo");
        var all = [];*/
	   /* //所有分类
        contentService.findAll().success(function(response){
            $scope.contentList[5] = response;
        });
	    */
        //一级分类
       /* contentService.findItemCatByParaentId(0).success(function (response) {
            all = response;

            /!*for (var i = 0 ;i < all.length;i++ ){
                alert( $scope.item_cat_list[i]);
            }*!/

            for(var i = 0; i < all.length;i++){
                all[i].sub = [];
                contentService.findItemCatByParaentId(all[i].id).success(function (response) {
                    all[i].sub = response;
                });
            }
            $scope.item_cat_list = all;
        });
*/

           /* alert("草拟个打野");
            //二级分类
            for(var i = 0; i<$scope.item_cat_list.length;i++){
                alert(i);
                contentService.findItemCatByParaentId($scope.item_cat_list[i].id).success(function (response) {
                    $scope.item_cat_list[i].sub = [];
                    $scope.item_cat_list[i].sub = response;
                    //三级分类
                    for(var j = 0; j<$scope.item_cat_list[i].sub.length;j++){
                        contentService.findItemCatByParaentId(0).success(function (response) {
                            $scope.item_cat_list[i].sub[j].sub = [];
                            $scope.item_cat_list[i].sub[j].sub = response;
                        });
                    }
                });
            }*/
    }


});