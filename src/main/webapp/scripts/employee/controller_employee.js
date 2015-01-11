'use strict';

promoteApp.controller('EmployeeController', function ($scope, resolvedEmployee, Employee, resolvedUnit) {

        $scope.employees = resolvedEmployee;
        $scope.units = resolvedUnit;

        $scope.create = function () {
            Employee.save($scope.employee,
                function () {
                    $scope.employees = Employee.query();
                    $('#saveEmployeeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.employee = Employee.get({id: id});
            $('#saveEmployeeModal').modal('show');
        };

        $scope.delete = function (id) {
            Employee.delete({id: id},
                function () {
                    $scope.employees = Employee.query();
                });
        };

        $scope.clear = function () {
            $scope.employee = {name: null, nip: null, birthDate: null, birthPlace: null, title: null, id: null};
        };
    });
