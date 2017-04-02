'use strict';

App.controller('ChannelController', ['$scope', 'ChannelService', function($scope, ChannelService) {
    var self = this;
    self.channel={id:null,shortid:'',user:'',title:'',description:'',link:'',language:'',pubDate:'',itemsCount:''};
    self.channels=[];

    self.fetchAllChannels = function(){
        ChannelService.fetchAllChannels()
            .then(
                function(d) {
                    self.channels = d;
                },
                function(errResponse){
                    console.error('Error while fetching Channels');
                }
            );
    };

    self.createChannel = function(channel){
        ChannelService.createChannel(channel)
            .then(
                self.fetchAllChannels,
                function(errResponse){
                    console.error('Error while creating Channel.');
                }
            );
    };

    self.updateChannel = function(channel, id){
        ChannelService.updateChannel(channel, id)
            .then(
                self.fetchAllChannels,
                function(errResponse){
                    console.error('Error while updating Channel.');
                }
            );
    };

    self.deleteChannel = function(id){
        ChannelService.deleteChannel(id)
            .then(
                self.fetchAllChannels,
                function(errResponse){
                    console.error('Error while deleting Channel.');
                }
            );
    };

    self.fetchAllChannels();

    self.submit = function() {
        if(self.channel.id===null){
            console.log('Saving New Channel', self.channel);
            self.createChannel(self.channel);
        }else{
            self.updateChannel(self.channel, self.channel.id);
            console.log('Channel updated with id ', self.channel.id);
        }
        self.reset();
    };

    self.edit = function(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.channels.length; i++){
            if(self.channels[i].id === id) {
                self.channel = angular.copy(self.channels[i]);
                break;
            }
        }
    };

    self.remove = function(id){
        console.log('id to be deleted', id);
        if(self.channel.id === id) {//clean form if the channel to be deleted is shown there.
            self.reset();
        }
        self.deleteChannel(id);
    };

    self.reset = function(){
    self.channel={id:null,shortid:'',user:'',title:'',description:'',link:'',language:'',pubDate:'',itemsCount:''};
    $scope.form.myForm.$setPristine(); //reset Form
    };


     self.logout = function() {
        var myURL="logout";
        window.location = myURL;
     };

/**** ITEMS ****/

/*        self.item={id:null,channelID:'',title:'',description:'',link:'',pubDate:'',channelTitle:''};
        self.items=[];

             self.fetchAllItemsForChannel = function(channelID){
                ChannelService.fetchAllItemsForChannel(channelID)
                    .then(
                        function(d) {
                            self.items = d;
                        },
                        function(errResponse){
                            console.error('Error while fetching Items for Channel');
                        }
                    );
            };*/

}]);