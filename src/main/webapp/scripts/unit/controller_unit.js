'use strict';

promoteApp.controller('UnitController', function ($scope, resolvedUnit, Unit, resolvedOrganization, resolvedEmployee) {

        $scope.units = resolvedUnit;
        $scope.organizations = resolvedOrganization;
        $scope.employees = resolvedEmployee;

        $scope.create = function () {
            Unit.save($scope.unit,
                function () {
                    $scope.units = Unit.query();
                    $('#saveUnitModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.unit = Unit.get({id: id});
            $('#saveUnitModal').modal('show');
        };

        $scope.delete = function (id) {
            Unit.delete({id: id},
                function () {
                    $scope.units = Unit.query();
                });
        };

        $scope.clear = function () {
            $scope.unit = {name: null, id: null};
        };
    });
