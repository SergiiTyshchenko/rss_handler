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

        getAllItemsForChannel: function(channelID, itemsCount) {
                        if (!channelID) {
                            channelID=-1;
                        }
                        console.info('ItemService: Start getAllItemsForChannel for channelID ',channelID);
                        var requestURL="item/channel="+channelID + "?itemsCount="+itemsCount;
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

        redirectTo: function(url) {
            window.location.href=url;
            return true;
        }

    };

}]);