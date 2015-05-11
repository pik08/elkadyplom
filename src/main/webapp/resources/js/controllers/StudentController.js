'use strict';

/**
 * CarController
 * @constructor
 */
var StudentController = function($scope, $http) {
    $scope.fetchCarsList = function() {
        $http.get('student/studentlist.json').success(function(carList){
            $scope.cars = carList;
        });
    };

    $scope.addNewCar = function(newCar) {
        $http.post('student/addCar/' + newCar).success(function() {
            $scope.fetchCarsList();
        });
        $scope.carName = '';
    };

    $scope.removeCar = function(car) {
        $http.delete('student/removeCar/' + car).success(function() {
            $scope.fetchCarsList();
        });
    };

    $scope.removeAllCars = function() {
        $http.delete('student/removeAllCars').success(function() {
            $scope.fetchCarsList();
        });

    };

    $scope.fetchCarsList();
};