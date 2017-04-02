'use strict';

App.factory('ItemService', ['$http', '$q', function($http, $q){

    return {

        fetchAllItems: function() {
            console.info('ItemService: Start fetchAllItems');
            var requestURL="item/";
            return $http.get(requestURL)
                                      .then(
                    function(response){
                        console.info('ItemService: fetchAllItems Called URL ', requestURL);
                        console.info('ItemService: Finish fetchAllItems');
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching items for All channels');
                        return $q.reject(errResponse);
                    }
                );
        }
        ,

        fetchAllItemsForChannel: function(channelID) {
                        console.info('ChannelService: Start fetchAllItemsForChannel for channelID ',channelID);
                        var requestURL="itemsForChannel?channelID="+channelID;
                        console.info('ChannelService: fetchAllItemsForChannel Redirecting to URL ',requestURL);
                        //var requestURL="item/channel="+channelID;
                        //return $http.get(requestURL)
                        return this.redirectTo(requestURL)
                                                  .then(
                                function(response){
                                    console.info('ChannelService: Finish fetchAllItemsForChannel for channelID ', channelID);
                                    return response.data;
                                },
                                function(errResponse){
                                    console.error('Error while fetching items for channel', channelID);
                                    return $q.reject(errResponse);
                                }
                            );
        }
        ,

        redirectTo: function(url) {
            window.location.href=url;
            return true;
        }
        ,

        fetchItemsNumber: function(channelID) {
            console.info('ItemService: Start fetchItemsNumber');
            if (!channelID) {
                channelID=-1;
            }
            var requestURL="item/count=10?channelID="+channelID;
            return $http.get(requestURL)
                                      .then(
                    function(response){
                        console.info('ItemService: fetchItemsNumber Called URL ', requestURL);
                        console.info('ItemService: Finish fetchItemsNumber');
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching Last 10 items');
                        return $q.reject(errResponse);
                    }
                );
        }


    };

}]);