app.service("contentService",function($http){

	this.findByCategoryId = function(categoryId){
        return $http.get("content/findByCategoryId.do?categoryId="+categoryId);
    }

    this.findItemCatByParaentId = function(parentId){
        return $http.get("itemCat/findByParentId.do?parentId="+parentId);
    }

    this.findPortolCategory = function(){
        return $http.get("itemCat/findPortolCategory.do");
    }
});