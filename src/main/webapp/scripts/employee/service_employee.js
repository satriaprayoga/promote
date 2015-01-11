'use strict';

promoteApp.factory('Employee', function ($resource) {
        return $resource('app/rest/employees/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
