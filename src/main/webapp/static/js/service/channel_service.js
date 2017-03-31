'use strict';

App.factory('ChannelService', ['$http', '$q', function($http, $q){

    return {

        fetchAllChannels: function() {
            return $http.get('http://localhost:8888/rss_handler_sb/channel/')
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
            return $http.post('http://localhost:8888/rss_handler_sb/channel/', channel)
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
            return $http.put('http://localhost:8888/rss_handler_sb/channel/'+id, channel)
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
            return $http.delete('http://localhost:8888/rss_handler_sb/channel/'+id)
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
        ,

        fetchAllItemsForChannel: function(channelID) {
                        console.info('Test redirect');
                        var requestURL="itemsForChannel?channelID="+channelID;
                        return $http.get(requestURL)//
                                                  .then(
                                function(response){
                                    window.location = requestURL;
                                    console.info('Fetching All items for channel', channelID);
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while fetching items for channel', channelID);
                                    return $q.reject(errResponse);
                                }
                            );
        }

    };

}]);