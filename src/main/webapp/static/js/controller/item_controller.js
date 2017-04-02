'use strict';

App.controller('ItemController', ['$scope', 'ItemService', function($scope, ItemService) {
    var self = this;
    self.item={id:null,channelID:'',title:'',description:'',link:'',pubDate:'',channelTitle:''};
    self.items=[];


    self.newItem = {};

    self.getNewItemData = function(newItemJson){
    console.info('Input JSON',newItemJson);
            if(newItemJson){
                 self.newItem = JSON.parse(newItemJson);
                 //console.log("Original JSON: " + JSON.stringify(self.newItem))
                 var keys = [];
                    for(var k in self.newItem) keys.push(k);
                 console.debug('Parsed JSON keys are:',keys);
                 console.info('Parsed JSON ' + keys[0] + ' field is:',self.newItem.channelID);
                 return self.fetchAllItemsForChannel(self.newItem.channelID);
            }else{
                return self.fetchAllItems();
            }
    };

   self.test = function(){
    console.info('TESTING TEST');
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
        //var channelID =  self.getNewItemData(channelIDJson);
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

       self.fetchItemsNumber = function(){
            ItemService.fetchItemsNumber(self.newItem.channelID)
                .then(
                    function(d) {
                        self.items = d;
                    },
                    function(errResponse){
                        console.error('Error while fetching last 10 Items');
                    }
                );
        };

}]);