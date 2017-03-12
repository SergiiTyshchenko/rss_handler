'use strict';

var App = angular.module('myApp',[]);
App.config(function($httpProvider) {
  $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
});