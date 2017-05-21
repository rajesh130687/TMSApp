var taskMgmtApp = angular.module('taskMgmtApp',['ngRoute','ngResource','ui.grid','ngAnimate', 'ngSanitize', 'ui.bootstrap']);


taskMgmtApp.config(function($routeProvider) {
    $routeProvider
    .when('/', {
        templateUrl: 'pages/taskList.html',
        controller: 'homeController'
    })
    .when('/detail', {
        templateUrl: 'pages/taskDetail.html',
        controller: 'detailController'
    })
});

taskMgmtApp.service('dataService', function() {
   this.taskData = {}; 
});

taskMgmtApp.controller('detailController',['$scope','$filter','$http','$location','dataService',function($scope,$filter,$http,$location,dataService) {
    $scope.statusList = ["Pending", "Open", "Resolved"];
    $scope.priorityList = [1,2,3];
    $scope.disableLogic = false;
    $scope.taskData = dataService.taskData.entity;
    
    if ($scope.taskData.status == 'Resolved') {
        $scope.disableLogic = true;
    }
    
    $scope.onSave = function() {
        $scope.taskData.remindDate =  $filter('date')($scope.dt, "MM/dd/yyyy");
        
        $http.post("http://localhost:8080/TMSApp/updTaskDtl/",$scope.taskData,{})
            .then(successCallback, errorCallback);

        function successCallback(response){
            alert('Task Details saved Succesfully');
            window.location = "/TMSApp/#!/";
        }
        
        function errorCallback(error){
            console.log('Serivce Call Failed');
            console.log(error);
        }
    }
    
    $scope.today = function() {
        $scope.dt = new Date();
    };
    $scope.dt = new Date($scope.taskData.remindDate);

    $scope.clear = function() {
        $scope.dt = null;
    };

    $scope.inlineOptions = {
        customClass: getDayClass,
        minDate: new Date(),
        showWeeks: true
    };

    $scope.dateOptions = {
        dateDisabled: disabled,
        formatYear: 'yy',
        maxDate: new Date(2020, 5, 22),
        minDate: new Date(),
        startingDay: 1
    };

    // Disable weekend selection
    function disabled(data) {
        var date = data.date, mode = data.mode;
        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    $scope.toggleMin = function() {
        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
    };

    $scope.toggleMin();

    $scope.open1 = function() {
        $scope.popup1.opened = true;
    };

    $scope.open2 = function() {
        $scope.popup2.opened = true;
    };

    $scope.setDate = function(year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    $scope.altInputFormats = ['M!/d!/yyyy'];

    $scope.popup1 = {
        opened: false
    };

    $scope.popup2 = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 1);
    $scope.events = [
        {
          date: tomorrow,
          status: 'full'
        },
        {
          date: afterTomorrow,
          status: 'partially'
        }
    ];

    function getDayClass(data) {
        var date = data.date, mode = data.mode;
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0,0,0,0);

            for (var i = 0; i < $scope.events.length; i++) {  
                var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }
        return '';
    }    
}]);


taskMgmtApp.controller('homeController',['$scope','$timeout','$http','dataService',function($scope,$timeout,$http,dataService) {
    
    $scope.setIdFunc = function (taskObj) {
        dataService.taskData = taskObj;
    };
    
    $scope.gridOptions = {
        enableColumnMenus: false,
        enableSorting: false, 
        columnDefs: [
            { field: 'title', displayName: 'Title', width: "15%", resizable: false, headerCellClass: 'ui-hdr', cellTemplate: '<div class="ui-grid-cell-contents"><a href="/TMSApp/#!/detail" ng-click="grid.appScope.setIdFunc(row)">{{grid.getCellValue(row, col)}}</a></div>' },
            { field: 'description', displayName: 'Description', width: "35%", resizable: false, headerCellClass: 'ui-hdr' },
            { field: 'status', displayName: 'Status', width: "10%", resizable: false, headerCellClass: 'ui-hdr', cellClass: 'ui-cell' },
            { field: 'priority', displayName: 'Priority', width: "10%", resizable: false, headerCellClass: 'ui-hdr', cellClass: 'ui-cell' },
            { field: 'dueDate', displayName: 'Due Date', width: "15%", resizable: false, headerCellClass: 'ui-hdr', cellClass: 'ui-cell' },
            { field: 'remindDate', displayName: 'Remind Date', width: "15%", resizable: false, headerCellClass: 'ui-hdr', cellClass: 'ui-cell' }
        ]
    }
    
    $scope.reload = function () {
        $http.get("http://localhost:8080/TMSApp/getTaskList/").then(successCallback, errorCallback);

        function successCallback(response){
            $scope.gridOptions.data = response.data.taskList;
        }
        
        function errorCallback(error){
            console.log('Serivce Call Failed');
            console.log(error);
        }

        $timeout(function(){
            $scope.reload();
        },30000)
    
    };
   
    $scope.reload();
}]);