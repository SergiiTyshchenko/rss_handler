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
                console.info('ItemService: Start fetchAllItemsForChannel for channelID ', channelID);
                var requestURL="item/channel="+channelID;
                return $http.get(requestURL)
                                          .then(
                        function(response){
                            console.info('ItemService: fetchAllItemsForChannel Called URL ', requestURL);
                            console.info('ItemService: Finished fetchAllItemsForChannel for channelID ', channelID);
                            return response.data;
                        },
                        function(errResponse){
                            console.error('Error while fetching items');
                            return $q.reject(errResponse);
                        }
                    );
        }
        ,

        fetchItemsNumber: function() {
            console.info('ItemService: Start fetchItemsNumber');
            //window.location.href;
            var requestURL="item/count=10";
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