'use strict';

App.controller('ItemController', ['$scope', 'ItemService', function($scope, ItemService) {
    var self = this;
    self.item={id:null,channelID:'',title:'',description:'',link:'',pubDate:'',channelTitle:''};
    self.items=[];
    self.newItem = {};

    self.getNewItemData = function(newItemJson){
        console.info('Input JSON',newItemJson);
                 if (newItemJson) {
                     self.newItem = JSON.parse(newItemJson);
                     //console.log("Original JSON: " + JSON.stringify(self.newItem))
                     var keys = [];
                        for(var k in self.newItem) keys.push(k);
                     console.debug('Parsed JSON keys are:',keys);
                     console.info('Parsed JSON ' + keys[0] + ' field is:',self.newItem.channelID);
                     return self.getAllItemsForChannel(self.newItem.channelID, null);
                  } else {
                     return self.getAllItemsForAllChannels();
                  }
    };

    self.fetchAllItemsForChannel = function(channelID){
        ItemService.fetchAllItemsForChannel(channelID)
            .then(
                function(d) {
                    self.items = d;
                },
                function(errResponse){
                    console.error('Error while fetching Items for Channel');
                }
            );
    };

    self.fetchAllItemsForAllChannels = function(){
                ItemService.fetchAllItemsForAllChannels()
                    .then(
                        function(d) {
                            self.items = d;
                        },
                        function(errResponse){
                            console.error('Error while fetching Items for Channel');
                        }
                    );
    };

    self.getLimitedItemsForChannel = function(channelID, itemsCount){
            ItemService.getLimitedItemsForChannel(channelID, itemsCount)
            .then(
                function(d) {
                    self.items = d;
                },
                function(errResponse){
                    console.error('Error while fetching Items for Channel');
                }
            );
    };

    self.getAllItemsForChannel = function(channelID, itemsCount){
        if (itemsCount){
            ItemService.getLimitedItemsForAllChannel(itemsCount)
            .then(
                function(d) {
                    self.items = d;
                },
                function(errResponse){
                    console.error('Error while fetching Items for Channel');
                }
            );
        } else {
            ItemService.getAllItemsForChannel(channelID, null)
            .then(
                function(d) {
                    self.items = d;
                },
                function(errResponse){
                    console.error('Error while fetching Items for Channel');
                }
            );
        }
    };

        self.getAllItemsForAllChannels = function(){
            ItemService.getAllItemsForAllChannels()
                .then(
                    function(d) {
                        self.items = d;
                    },
                    function(errResponse){
                        console.error('Error while fetching Items for Channel');
                    }
                );
        };

}]);