'use strict';

promoteApp.factory('Organization', function ($resource) {
        return $resource('app/rest/organizations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
