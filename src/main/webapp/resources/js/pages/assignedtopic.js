function assignedTopicController($scope, $http) {
    $scope.pageToGet = 0;
    $scope.assignedPageToGet = 0;

    $scope.state = 'busy';

    $scope.lastAction = '';
    $scope.assignedTopicUrl = "/elkadyplom/protected/assignedTopic/"

    $scope.errorOnSubmit = false;
    $scope.errorIllegalAccess = false;
    $scope.displayMessageToUser = false;
    $scope.displayValidationError = false;
    $scope.displaySearchMessage = false;
    $scope.displaySearchButton = false;
    $scope.displayCreateTopicButton = false;

    $scope.topic = {};


    $scope.getAssignedTopicList = function () {
        var url = $scope.assignedTopicUrl;
        $scope.lastAction = 'list';

        $scope.startDialogAjaxRequest();

        var config = {params: {page: $scope.assignedPageToGet}};

        $http.get(url, config)
            .success(function (data) {
                $scope.finishAjaxCallOnSuccessAssigned(data, null, false);
            })
            .error(function () {
                $scope.state = 'error';
                $scope.displayCreateTopicButton = false;
            });
    };

    $scope.populateAssignedTable = function (data) {
        if (data.pagesCount > 0) {
            $scope.state = 'list';

            $scope.page = {source: data.topics, currentPage: $scope.assignedPageToGet, pagesCount: data.pagesCount, totalTopics : data.totalTopics};

            if($scope.page.pagesCount <= $scope.page.currentPage){
                $scope.assignedPageToGet = $scope.page.pagesCount - 1;
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
            $scope.getAssignedTopicList();
        }
    };

    $scope.exit = function (modalId) {
        $(modalId).modal('hide');

        $scope.topic = {};
        $scope.errorOnSubmit = false;
        $scope.errorIllegalAccess = false;
        $scope.displayValidationError = false;
    };

    $scope.finishAjaxCallOnSuccessAssigned = function (data, modalId, isPagination) {
        $scope.populateAssignedTable(data);
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


    $scope.getAssignedTopicList();
}
