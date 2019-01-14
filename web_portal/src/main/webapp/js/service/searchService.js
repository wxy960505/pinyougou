app.service('searchService',function($http){
	
	
	this.search=function(searchMap){
		return $http.post('itemsearch/search.do',searchMap);
	}

    this.addFollow = function(id){
        return $http.get('follow/addFollow.do?id='+id);
    }
	
});