'use strict';

promoteApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/unit', {
                    templateUrl: 'views/units.html',
                    controller: 'UnitController',
                    resolve:{
                        resolvedUnit: ['Unit', function (Unit) {
                            return Unit.query().$promise;
                        }],
                        resolvedOrganization: ['Organization', function (Organization) {
                            return Organization.query().$promise;
                        }],
                        resolvedEmployee: ['Employee', function (Employee) {
                            return Employee.query().$promise;
                        }]
                    },
                    access: {
                        authorizedRoles: [USER_ROLES.all]
                    }
                })
        });
