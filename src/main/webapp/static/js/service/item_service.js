'use strict';

App.factory('ItemService', ['$http', '$q', function($http, $q){

    return {

        fetchAllItems: function() {
            return $http.get('http://localhost:8888/rss_handler_sb/item/')
                                      .then(
                    function(response){
                        return response.data;
                    },
                    function(errResponse){
                        console.error('Error while fetching items');
                        return $q.reject(errResponse);
                    }
                );
        }
        ,

        fetchAllItemsForChannel: function(channelID) {
                var requestURL="item/channel="+channelID;
                return $http.get(requestURL)//channel='+channelID
                                          .then(
                        function(response){
                            console.info('You are redirected to', requestURL);
                            return response.data;
                        },
                        function(errResponse){
                            console.error('Error while fetching items');
                            return $q.reject(errResponse);
                        }
                    );
        }

    };

}]);