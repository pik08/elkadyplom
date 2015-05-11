'use strict';

var AngularSpringApp = {};

var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters', 'AngularSpringApp.services', 'AngularSpringApp.directives']);

// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/student', {
        templateUrl: 'student/layout',
        controller: StudentController
    });

    $routeProvider.when('/admin', {
        templateUrl: 'admin/layout',
        controller: AdminController
    });
    
    $routeProvider.when('/promoter', {
        templateUrl: 'promoter/layout',
        controller: PromoterController
    });

    $routeProvider.otherwise({redirectTo: '/student'});
}]);
