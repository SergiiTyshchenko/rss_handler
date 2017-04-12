'use strict';

App.factory('ItemService', ['$http', '$q', function($http, $q){

    return {

        //function redirects to /itemsForChannel and show JSP
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
                                    console.error('Error for fetchAllItemsForChannel while fetching items for channel', channelID, requestURL);
                                    return $q.reject(errResponse);
                                }
                            );
        },

        //function redirects to /itemsForChannel/allChannels and show JSP
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
                                    console.error('Error for fetchAllItemsForAllChannels while fetching items for All channels', requestURL);
                                    return $q.reject(errResponse);
                                }
                            );
        },

        getAllItemsForChannel: function(channelID) {
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
                                    console.error('Error for getAllItemsForChannel', channelID, requestURL);
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
                        console.info('ItemService: Start getLimitedItemsForChannel for channelID ',channelID, itemsCount);
                        console.info('ItemService: getLimitedItemsForChannel Redirecting to URL ',requestURL);
                        return $http.get(requestURL)
                                                  .then(
                                function(response){
                                    console.info('ItemService: Finish getLimitedItemsForChannel for channelID ', channelID, itemsCount);
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error for getLimitedItemsForChannel', channelID, itemsCount, requestURL);
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
                                            console.error('Error for getAllItemsForAllChannels', requestURL);
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
                                            console.error('Error for getLimitedItemsForAllChannel', itemsCount, requestURL);
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