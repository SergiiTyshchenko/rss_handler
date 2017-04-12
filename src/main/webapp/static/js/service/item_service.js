'use strict';

App.factory('ItemService', ['$http', '$q', function($http, $q){

    return {

        fetchAllItemsForChannel: function(channelID) {
                        console.info('ItemService: Start fetchAllItemsForChannel for channelID ',channelID);
                        var requestURL="itemsForChannel?channelID="+channelID;
                        console.info('ItemService: fetchAllItemsForChannel Redirecting to URL ',requestURL);
                        return this.redirectTo(requestURL)
                                                  .then(
                                function(response){
                                    console.info('ItemService: Finish fetchAllItemsForChannel for channelID ', channelID);
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while fetching items for channel', channelID);
                                    return $q.reject(errResponse);
                                }
                            );
        },

        fetchAllItemsForAllChannels: function() {
                        console.info('ItemService: Start fetchAllItemsForAllChannels for All channels ');
                        var requestURL="itemsForChannel/allChannels";
                        console.info('ItemService: fetchAllItemsForAllChannels Redirecting to URL ',requestURL);
                        return this.redirectTo(requestURL)
                                                  .then(
                                function(response){
                                    console.info('ItemService: Finish fetchAllItemsForAllChannels for All channels');
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while fetching items for All channels');
                                    return $q.reject(errResponse);
                                }
                            );
        },

        getAllItemsForChannel: function(channelID) {
                        if (!channelID) {
                            channelID=-1;
                        }
                        console.info('ItemService: Start getAllItemsForChannel for channelID ',channelID);
                        var requestURL="item/channel="+channelID;
                        console.info('ItemService: getAllItemsForChannel Redirecting to URL ',requestURL);
                        return $http.get(requestURL)
                                                  .then(
                                function(response){
                                    console.info('ItemService: Finish getAllItemsForChannel for channelID ', channelID);
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while getting items for channel', channelID);
                                    return $q.reject(errResponse);
                                }
                            );
        },

        getLimitedItemsForChannel: function(channelID, itemsCount) {
                        if (!channelID) {
                            var requestURL="/rss_handler_sb/item/allChannels?itemsCount="+itemsCount;
                        } else {
                            var requestURL="item/channel="+channelID + "?itemsCount="+itemsCount;
                        }
                        console.info('ItemService: Start getAllItemsForChannel for channelID ',channelID);
                        console.info('ItemService: getAllItemsForChannel Redirecting to URL ',requestURL);
                        return $http.get(requestURL)
                                                  .then(
                                function(response){
                                    console.info('ItemService: Finish getAllItemsForChannel for channelID ', channelID);
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while getting items for channel', channelID);
                                    return $q.reject(errResponse);
                                }
                            );
        },

        getAllItemsForAllChannels: function() {
                                console.info('ItemService: Start getAllItemsForAllChannels for All channels ');
                                var requestURL="/rss_handler_sb/item/allChannels";
                                console.info('ItemService: getAllItemsForAllChannels Redirecting to URL ',requestURL);
                                return $http.get(requestURL)
                                                          .then(
                                        function(response){
                                            console.info('ItemService: Finish getAllItemsForAllChannels for All channels ');
                                            return response.data;
                                        },
                                        function(errResponse){
                                            console.error('Error while getting items for All channels');
                                            return $q.reject(errResponse);
                                        }
                                    );
        },

        getLimitedItemsForAllChannel: function(itemsCount) {
                                console.info('ItemService: Start getLimitedItemsForAllChannel for All channels ',itemsCount);
                                var requestURL="/rss_handler_sb/item/allChannels?itemsCount="+itemsCount;
                                console.info('ItemService: getLimitedItemsForAllChannel Redirecting to URL ',requestURL);
                                return $http.get(requestURL)
                                                          .then(
                                        function(response){
                                            console.info('ItemService: Finish getLimitedItemsForAllChannel for All channels ', itemsCount);
                                            return response.data;
                                        },
                                        function(errResponse){
                                            console.error('Error while getting items for All channels', itemsCount);
                                            return $q.reject(errResponse);
                                        }
                                    );
        },

        redirectTo: function(url) {
            window.location.href=url;
            return true;
        }

    };

}]);