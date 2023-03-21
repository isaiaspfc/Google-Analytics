(function() {
    'use strict';
    angular.module("exercicioAngularJS").factory("ModuloApi", function($http, config) {

        var _ModuloBaseUrl = config.baseUrl + "/modulos";

        var _obterTodos = function() {
            return $http.get(_ModuloBaseUrl);
        };
        var _obter = function(valorTamanhoDaPagina, valorPaginaAtual) {
            return $http.get(_ModuloBaseUrl + "?tamanhoDaPagina=" + valorTamanhoDaPagina + "&numeroDaPagina=" + valorPaginaAtual);
        };
        return {
            obterTodos: _obterTodos,
            obter: _obter
        };
    });
})();