(function() {
    'use strict';
    angular.module("exercicioAngularJS").config(function($routeProvider) {
        $routeProvider.when("/funcionalidades", {

            templateUrl: "/funcionalidade/funcionalidade.html",
            controller: "FuncionalidadeController",
        });
    });
})();