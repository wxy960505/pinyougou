
var app = angular.module("pinyougou",[]);
app.controller('lineCtrl', function($scope,$http) {

    $scope.findAll=function(){
        $scope.legend = ["最近七年销量","最近七年的访问量"];
        $scope.item = ['2013', '2014', '2015', '2016', '2017','2018','2019'];

        //Berlin
        $http.get('http://localhost:8082/line/findAllTime.do').success(
                function(response) {
                    $scope.data=response;
                });

        $scope.data =[['0', '0', '0', '4', '45', '4', '9'],
            ['0', '0', '0', '70', '243', '24', '56']];
    };

});


app.directive('line', function() {
    return {
        scope: {
            id: "@",
            legend: "=",
            item: "=",
            data: "="
        },
        restrict: 'E',
        template: '<div style="height:400px;"></div>',
        replace: true,
        link: function($scope, element, attrs, controller) {
            var option = {
                // 提示框，鼠标悬浮交互时的信息提示
                tooltip: {
                    show: true,
                    trigger: 'item'
                },
                // 图例
                legend: {
                    data: $scope.legend
                },
                // 横轴坐标轴
                xAxis: [{
                    type: 'category',
                    data: $scope.item
                }],
                // 纵轴坐标轴
                yAxis: [{
                    type: 'value'
                }],
                // 数据内容数组
                series: function(){
                    var serie=[];

                    for (var i = 0; i < $scope.legend.length; i++) {
                        var item = {
                            name: $scope.legend[i],
                            type: 'line',
                            data: $scope.data[i]
                        };
                        serie.push(item);
                    }
                    return serie;
                }()
            };
            var myChart = echarts.init(document.getElementById($scope.id),'macarons');
            myChart.setOption(option);
        }
    };
});
