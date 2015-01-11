'use strict';

promoteApp.factory('Unit', function ($resource) {
        return $resource('app/rest/units/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
