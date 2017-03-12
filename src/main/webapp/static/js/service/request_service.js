'use strict';

App.factory('RequestService', ['$http', '$q', function($http, $q){

    return {

        fetchAllRequests: function() {
            return $http.get('http://localhost:8888/rss_handler_sb/db/request/')
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching requests');
                        return $q.reject(errResponse);
                    }
                );
        },

        createRequest: function(request){
            console.log('Creating request...', self.request);
            return $http.post('http://localhost:8888/rss_handler_sb/db/request/', request)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while creating request');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateRequest: function(request, id){
            return $http.put('http://localhost:8888/rss_handler_sb/db/request/'+id, request)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while updating request');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteRequest: function(id){
            return $http.delete('http://localhost:8888/rss_handler_sb/db/request/'+id)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while deleting request');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);