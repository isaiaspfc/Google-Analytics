(function() {
    'use strict';
    angular.module("exercicioAngularJS").factory("FuncionalidadeApi", function($http, config) {

        var _FuncionalidadeBaseUrl = config.baseUrl + "/funcionalidades";

        var _obterTodos = function() {
            return $http.get(_FuncionalidadeBaseUrl);
        };
        var _obter = function(valorTamanhoDaPagina, valorPaginaAtual) {
            return $http.get(_FuncionalidadeBaseUrl + "?tamanhoDaPagina=" + valorTamanhoDaPagina + "&numeroDaPagina=" + valorPaginaAtual);
        };
        return {
            obterTodos: _obterTodos,
            obter: _obter
        };
    });
})();