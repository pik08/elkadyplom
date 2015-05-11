'use strict';

/**
 * TrainController
 * @constructor
 */
var AdminController = function($scope, $http) {
    $scope.train = {};
    $scope.editMode = false;

    $scope.fetchTrainsList = function() {
        $http.get('admin/adminlist.json').success(function(trainList){
            $scope.trains = trainList;
        });
    };

    $scope.addNewTrain = function(train) {
        $scope.resetError();

        $http.post('admin/addTrain', train).success(function() {
            $scope.fetchTrainsList();
            $scope.train.name = '';
            $scope.train.speed = '';
            $scope.train.diesel = false;
        }).error(function() {
            $scope.setError('Could not add a new train');
        });
    };

    $scope.updateTrain = function(train) {
        $scope.resetError();

        $http.put('admin/updateTrain', train).success(function() {
            $scope.fetchTrainsList();
            $scope.train.name = '';
            $scope.train.speed = '';
            $scope.train.diesel = false;
            $scope.editMode = false;
        }).error(function() {
            $scope.setError('Could not update the train');
        });
    };

    $scope.editTrain = function(train) {
        $scope.resetError();
        $scope.train = train;
        $scope.editMode = true;
    };

    $scope.removeTrain = function(id) {
        $scope.resetError();

        $http.delete('admin/removeTrain/' + id).success(function() {
            $scope.fetchTrainsList();
        }).error(function() {
            $scope.setError('Could not remove train');
        });
        $scope.train.name = '';
        $scope.train.speed = '';
    };

    $scope.removeAllTrains = function() {
        $scope.resetError();

        $http.delete('admin/removeAllTrains').success(function() {
            $scope.fetchTrainsList();
        }).error(function() {
            $scope.setError('Could not remove all trains');
        });

    };

    $scope.resetTrainForm = function() {
        $scope.resetError();
        $scope.train = {};
        $scope.editMode = false;
    };

    $scope.resetError = function() {
        $scope.error = false;
        $scope.errorMessage = '';
    };

    $scope.setError = function(message) {
        $scope.error = true;
        $scope.errorMessage = message;
    };

    $scope.fetchTrainsList();

    $scope.predicate = 'id';
};