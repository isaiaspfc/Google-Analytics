(function() {
    'use strict';
    angular.module("exercicioAngularJS").config(function($routeProvider) {
        $routeProvider.when("/modulos", {

            templateUrl: "/modulo/modulo.html",
            controller: "ModuloController",
        });
    });
})();