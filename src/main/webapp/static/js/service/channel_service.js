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

/**** ITEMS ****/

        fetchAllItems: function() {
                        console.info('ChannelService: Start fetchAllItems');
                        var requestURL="itemsForChannel?channelID=1000";
                        return $http.get(requestURL)//
                                                  .then(
                                function(response){
                                    console.info('ChannelService: fetchAllItems Redirecting to URL ',requestURL);
                                    window.location = requestURL;
                                    console.info('ChannelService: Finish fetchAllItems');
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while fetching All items');
                                    return $q.reject(errResponse);
                                }
                            );
        }
        ,

        fetchAllItemsForChannel: function(channelID) {
                        console.info('ChannelService: Start fetchAllItemsForChannel for channelID ',channelID);
                        var requestURL="itemsForChannel?channelID="+channelID;
                        return $http.get(requestURL)//
                                                  .then(
                                function(response){
                                    console.info('ChannelService: fetchAllItemsForChannel Redirecting to URL ',requestURL);
                                    window.location = requestURL;
                                    console.info('ChannelService: Finish fetchAllItemsForChannel for channelID ', channelID);
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