var app = angular.module('app', ['ngRoute']);
app.controller('ctrl', function($scope, $http) {
    $scope.items = [];
    $scope.load_all = function() {
        var url = `/allProduct`;
        $http.get(url).then(resp => {
            $scope.items = resp.data;
        }).catch(error => {
            console.log("Error", error);
        });
    }
    $scope.load_all();
});
