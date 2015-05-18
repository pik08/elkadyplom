function LocationController($scope, $location) {
    if($location.$$absUrl.lastIndexOf('/topics') > 0){
        $scope.activeURL = 'topics';
    } else{
        $scope.activeURL = 'home';
    }
}