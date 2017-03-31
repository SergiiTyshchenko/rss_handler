'use strict';

App.controller('ItemController', ['$scope', 'ItemService', function($scope, ItemService) {
    var self = this;
    self.item={id:null,channelID:'',title:'',description:'',link:'',pubDate:'',channelTitle:''};
    self.items=[];


    self.newItem = {};

    self.getNewItemData = function(newItemJson){
    console.info('Input JSON',newItemJson);
    self.newItem = JSON.parse(newItemJson);
    //console.log("Original JSON: " + JSON.stringify(self.newItem))
    var keys = [];
       for(var k in self.newItem) keys.push(k);
    console.debug('Parsed JSON keys are:',keys);
    console.info('Parsed JSON ' + keys[0] + ' field is:',self.newItem.channelID);
    return self.newItem.channelID;
    };

   self.test = function(){
    console.info('TESTING TEST');
    };

   self.fetchAllItems = function(){
        console.info('TEST Fetching All Items');
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

     self.fetchAllItemsForChannel = function(channelIDJson){
        var channelID =  self.getNewItemData(channelIDJson);
        console.info('Start Fetching Items for channel ID ' + channelID);
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
    //self.fetchAllItems();

}]);