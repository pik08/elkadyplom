function adminAssignTopicsController($scope, $http) {
    $scope.url = '/elkadyplom/protected/assign/'

    $scope.assignments = [];
    $scope.state = "";

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

    $scope.acceptAssignments = function() {
        var url = $scope.url + "doAssign"

        $http.post(url, $scope.assignments)
            .success( function(data) {
                $scope.state = "ok";
            })
            .error( function() {
                $scope.state = 'error';
            });
    }
}