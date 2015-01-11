'use strict';

promoteApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/employee', {
                    templateUrl: 'views/employees.html',
                    controller: 'EmployeeController',
                    resolve:{
                        resolvedEmployee: ['Employee', function (Employee) {
                            return Employee.query().$promise;
                        }],
                        resolvedUnit: ['Unit', function (Unit) {
                            return Unit.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
