function adminAssignTopicsController($scope, $http) {
    $scope.url = '/elkadyplom/protected/assign/'

    $scope.assignments = [];

    $scope.doAssign = function() {
        var url = $scope.url + "doAssign"

        $http.get(url)
            .success( function(data) {
                $scope.assignments = data;
            })
            .error( function() {
                $scope.state = 'error';
            });
    }
}