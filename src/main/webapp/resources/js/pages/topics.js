function topicsController($scope, $http) {
    $scope.pageToGet = 0;

    $scope.state = 'busy';

    $scope.lastAction = '';

    $scope.url = "/elkadyplom/protected/topics/";

    $scope.errorOnSubmit = false;
    $scope.errorIllegalAccess = false;
    $scope.displayMessageToUser = false;
    $scope.displayValidationError = false;
    $scope.displaySearchMessage = false;
    $scope.displaySearchButton = false;
    $scope.displayCreateTopicButton = false;

    $scope.supervisors = [];

    $scope.topic = {};

    $scope.searchFor = "";

    $scope.declarations = [];
    $scope.declarationList = [];

    $scope.getTopicList = function () {
        var url = $scope.url;
        $scope.lastAction = 'list';

        $scope.startDialogAjaxRequest();

        var config = {params: {page: $scope.pageToGet}};

        $http.get(url, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, null, false);
            })
            .error(function () {
                $scope.state = 'error';
                $scope.displayCreateTopicButton = false;
            });
    };

    $scope.populateTable = function (data) {
        if (data.pagesCount > 0) {
            $scope.state = 'list';

            $scope.page = {source: data.topics, currentPage: $scope.pageToGet, pagesCount: data.pagesCount, totalTopics : data.totalTopics};

            if($scope.page.pagesCount <= $scope.page.currentPage){
                $scope.pageToGet = $scope.page.pagesCount - 1;
                $scope.page.currentPage = $scope.page.pagesCount - 1;
            }

            $scope.displayCreateTopicButton = true;
            $scope.displaySearchButton = true;
        } else {
            $scope.state = 'noresult';
            $scope.displayCreateTopicButton = true;

            if(!$scope.searchFor){
                $scope.displaySearchButton = false;
            }
        }

        if (data.actionMessage || data.searchMessage) {
            $scope.displayMessageToUser = $scope.lastAction != 'search';

            $scope.page.actionMessage = data.actionMessage;
            $scope.page.searchMessage = data.searchMessage;
        } else {
            $scope.displayMessageToUser = false;
        }
    };

    $scope.changePage = function (page) {
        $scope.pageToGet = page;

        if($scope.searchFor){
            $scope.searchTopic($scope.searchFor, true);
        } else{
            $scope.getTopicList();
        }
    };

    $scope.exit = function (modalId) {
        $(modalId).modal('hide');

        $scope.topic = {};
        $scope.errorOnSubmit = false;
        $scope.errorIllegalAccess = false;
        $scope.displayValidationError = false;
    };

    $scope.finishAjaxCallOnSuccess = function (data, modalId, isPagination) {
        $scope.populateTable(data);
        $("#loadingModal").modal('hide');

        if(!isPagination){
            if(modalId){
                $scope.exit(modalId);
            }
        }

        $scope.lastAction = '';
    };

    $scope.startDialogAjaxRequest = function () {
        $scope.displayValidationError = false;
        $("#loadingModal").modal('show');
        $scope.previousState = $scope.state;
        $scope.state = 'busy';
    };

    $scope.handleErrorInDialogs = function (status) {
        $("#loadingModal").modal('hide');
        $scope.state = $scope.previousState;

        // illegal access
        if(status == 403){
            $scope.errorIllegalAccess = true;
            return;
        }

        $scope.errorOnSubmit = true;
        $scope.lastAction = '';
    };

    $scope.addSearchParametersIfNeeded = function(config, isPagination) {
        if(!config.params){
            config.params = {};
        }

        config.params.page = $scope.pageToGet;

        if($scope.searchFor){
            config.params.searchFor = $scope.searchFor;
        }
    };

    $scope.resetTopic = function(){
        $scope.topic = {};
    };

    $scope.createTopic = function (newTopicForm) {
        if (!newTopicForm.$valid) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'create';

        var url = $scope.url;

        var config = {headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}};

        $scope.addSearchParametersIfNeeded(config, false);

        $scope.startDialogAjaxRequest();

        $http.post(url, $.param($scope.topic), config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#addTopicsModal", false);
            })
            .error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.selectedTopic = function (topic) {
        var selectedTopic = angular.copy(topic);
        $scope.topic = selectedTopic;
    };

    $scope.updateTopic = function (updateTopicForm) {
        if (!updateTopicForm.$valid) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'update';

        var url = $scope.url + $scope.topic.id;

        $scope.startDialogAjaxRequest();

        var config = {};

        $scope.addSearchParametersIfNeeded(config, false);

        $http.put(url, $scope.topic, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#updateTopicsModal", false);
            })
            .error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.searchTopic = function (searchTopicForm, isPagination) {
        if (!($scope.searchFor) && (!searchTopicForm.$valid)) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'search';

        var url = $scope.url +  $scope.searchFor;

        $scope.startDialogAjaxRequest();

        var config = {};

        if($scope.searchFor){
            $scope.addSearchParametersIfNeeded(config, isPagination);
        }

        $http.get(url, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#searchTopicsModal", isPagination);
                $scope.displaySearchMessage = true;
            })
            .error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    };

    $scope.deleteTopic = function () {
        $scope.lastAction = 'delete';

        var url = $scope.url + $scope.topic.id;

        $scope.startDialogAjaxRequest();

        var params = {searchFor: $scope.searchFor, page: $scope.pageToGet};

        $http({
            method: 'DELETE',
            url: url,
            params: params
        }).success(function (data) {
            $scope.resetTopic();
            $scope.finishAjaxCallOnSuccess(data, "#deleteTopicsModal", false);
        }).error(function(data, status, headers, config) {
            $scope.handleErrorInDialogs(status);
        });
    };

    $scope.resetSearch = function(){
        $scope.searchFor = "";
        $scope.pageToGet = 0;
        $scope.getTopicList();
        $scope.displaySearchMessage = false;
    };

    $scope.getSupervisorList = function(){
        var url = $scope.url + "supervisors";
        var config = {};

        $http.get(url, config)
            .success(function (data) {
                $scope.supervisors = data;
            })
            .error(function () {
                $scope.state = 'error';
                $scope.displayCreateTopicButton = false;
            });
    }

    $scope.getStudentList = function(){
        var url = $scope.url + "students";
        var config = {};

        $http.get(url, config)
            .success(function (data) {
                $scope.students = data;
            })
            .error(function () {
                $scope.state = 'error';
                $scope.displayCreateTopicButton = false;
            });
    }

    $scope.addTopicToDeclared = function(topic) {
        // if ($scope.declarations.indexOf(topic) == -1) FIXME
        $scope.declarations.push({
            topicId : topic.id,
            topicTitle : topic.title,
            topicSupervisorName : topic.supervisorName,
            declarationId : 0,
            rank : 1
        });
    }

    $scope.resetTopicsSelectedToDeclare = function() {
        $scope.declarations = [];
    }

    $scope.removeTopicSelectedToDeclare = function(topic) {
        var idx = $scope.declarations.indexOf(topic);
        if (idx > -1)
            $scope.declarations.splice(idx, 1);
    }

    $scope.getDeclarations = function() {
        var url = $scope.url + "declare";
        $scope.lastAction = 'getDeclarations';

        $scope.startDialogAjaxRequest();

        var config = {}

        $http.get(url, config)
            .success(function (data) {
                $scope.declarations = data;
            })
            .error(function () {
                $scope.state = 'error';
                $scope.displayCreateTopicButton = false;
            });
    }

    $scope.declareTopics = function(declareTopicsForm) {
        if (!declareTopicsForm.$valid) {
            $scope.displayValidationError = true;
            return;
        }

        $scope.lastAction = 'declare';

        var url = $scope.url + 'declare';

        $scope.startDialogAjaxRequest();

        var config = {headers: {'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'}};

        $scope.addSearchParametersIfNeeded(config, false);

        $http.post(url, $scope.declarations)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccess(data, "#declareTopicsModal", false);
            })
            .error(function(data, status, headers, config) {
                $scope.handleErrorInDialogs(status);
            });
    }

    $scope.getTopicList();
    $scope.getStudentList();
    $scope.getSupervisorList();
    $scope.getDeclarations();
}
