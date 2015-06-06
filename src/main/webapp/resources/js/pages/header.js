function LocationController($scope, $location) {
    if($location.$$absUrl.lastIndexOf('/topics') > 0){
        $scope.activeURL = 'topics';
    }
    if($location.$$absUrl.lastIndexOf('/assignedTopic') > 0){
        $scope.activeURL = 'assignedTopic';
    }
    if($location.$$absUrl.lastIndexOf('/assign') > 0){
        $scope.activeURL = 'assign';
    }
    if($location.$$absUrl.lastIndexOf('/home') > 0){
        $scope.activeURL = 'home';
    }
}