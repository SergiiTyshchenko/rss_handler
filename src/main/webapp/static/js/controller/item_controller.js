'use strict';

App.controller('ItemController', ['$scope', 'ItemService', function($scope, ItemService) {
    var self = this;
    self.item={id:null,channelID:'',title:'',description:'',link:'',pubDate:''};
    self.items=[];


    self.newItem = {};
    self.getNewItemData = function(newItemJson){
    console.info('Input JSON',newItemJson);
    self.newItem = JSON.parse(newItemJson);
    console.info('Parsed JSON',self.newItem);
    };


   self.fetchAllItems = function(){
        ItemService.fetchAllItems()
            .then(
                function(d) {
                    self.items = d;
                },
                function(errResponse){
                    console.error('Error while fetching Items');
                }
            );
    };

     self.fetchAllItemsForChannel = function(channelID){
        ItemService.fetchAllItemsForChannel(channelID)
            .then(
                function(d) {
                    console.info('Fetching Items for channel ID ' + channelID);
                    self.items = d;
                },
                function(errResponse){
                    console.error('Error while fetching Items for Channel');
                }
            );
    };

    //self.getNewItemData(self.newItem);
    //self.fetchAllItemsForChannel(self.channelID);
    self.fetchAllItems();

}]);