var app=angular.module('Home', ['$strap.directives','toggle-switch']).
  config(function($routeProvider) {
    $routeProvider.
      when('/', {controller:HomeCtrl, templateUrl:'register.html'}).
      otherwise({redirectTo:'/'});
  });
  
  function HomeCtrl($scope, Registeration,alertService) {
  }