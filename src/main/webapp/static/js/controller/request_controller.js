'use strict';

App.controller('RequestController', ['$scope', 'RequestService', function($scope, RequestService) {
    var self = this;
    self.request={id:null,user:'',description:'',link:'',title:'',language:'',title:''};
    self.requests=[];

    self.fetchAllRequests = function(){
        RequestService.fetchAllRequests()
            .then(
                function(d) {
                    self.requests = d;
                },
                function(errResponse){
                    console.error('Error while fetching Requests');
                }
            );
    };

    self.createRequest = function(request){
        RequestService.createRequest(request)
            .then(
                self.fetchAllRequests,
                function(errResponse){
                    console.error('Error while creating Request.');
                }
            );
    };

    self.updateRequest = function(request, id){
        RequestService.updateRequest(request, id)
            .then(
                self.fetchAllRequests,
                function(errResponse){
                    console.error('Error while updating Request.');
                }
            );
    };

    self.deleteRequest = function(id){
        RequestService.deleteRequest(id)
            .then(
                self.fetchAllRequests,
                function(errResponse){
                    console.error('Error while deleting Request.');
                }
            );
    };

    self.fetchAllRequests();

    self.submit = function() {
        if(self.request.id===null){
            console.log('Saving New Request', self.request);
            self.createRequest(self.request);
        }else{
            self.updateRequest(self.request, self.request.id);
            console.log('Request updated with id ', self.request.id);
        }
        self.reset();
    };

    self.edit = function(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.requests.length; i++){
            if(self.requests[i].id === id) {
                self.request = angular.copy(self.requests[i]);
                break;
            }
        }
    };

    self.remove = function(id){
        console.log('id to be deleted', id);
        if(self.request.id === id) {//clean form if the request to be deleted is shown there.
            self.reset();
        }
        self.deleteRequest(id);
    };


    self.reset = function(){
    self.request={id:null,user:'',description:'',link:'',title:'',language:'',title:''};
        $scope.myForm.$setPristine(); //reset Form
    };

}]);