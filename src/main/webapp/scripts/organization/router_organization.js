'use strict';

promoteApp
    .config(function ($routeProvider, $httpProvider, $translateProvider, USER_ROLES) {
            $routeProvider
                .when('/organization', {
                    templateUrl: 'views/organizations.html',
                    controller: 'OrganizationController',
                    resolve:{
                        resolvedOrganization: ['Organization', function (Organization) {
                            return Organization.query().$promise;
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
