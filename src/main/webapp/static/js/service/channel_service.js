'use strict';

App.factory('ChannelService', ['$http', '$q', function($http, $q){

    return {

        fetchAllChannels: function() {
            return $http.get('channel/')
                                      .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching channels');
                        return $q.reject(errResponse);
                    }
                );
        },

        createChannel: function(channel){
            console.log('Creating channel...', self.channel);
            return $http.post('channel/', channel)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while creating channel');
                        return $q.reject(errResponse);
                    }
                );
        },

        updateChannel: function(channel, id){
            return $http.put('channel/'+id, channel)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while updating channel');
                        return $q.reject(errResponse);
                    }
                );
        },

        deleteChannel: function(id){
            return $http.delete('channel/'+id)
                .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while deleting channel');
                        return $q.reject(errResponse);
                    }
                );
        }

    };

}]);