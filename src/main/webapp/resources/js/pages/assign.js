function adminAssignTopicsController($scope, $http) {
    $scope.url = '/elkadyplom/protected/assign/'

    $scope.doAssign = function() {
        var url = $scope.url + "doAssign"

        $http.get(url)
            .success(function (data) {
                result = data;
            });
            //.error(function () {
            //    $scope.state = 'error';
            //    $scope.displayCreateTopicButton = false;
            //});
    }
}