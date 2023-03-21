(function() {
    'use strict';
    angular.module("exercicioAngularJS").controller("ModuloController", function($scope, ModuloApi, $location) {
        $scope.Modulos = [];
        $scope.tamanhoDaPagina = 2;
        $scope.numeroDaPaginaAtual = 0;

        $scope.mudarExibicao = function(numero) {
            $scope.tamanhoDaPagina = numero;
            obterTodos();
        }

        $scope.proximaPagina = function() {
            if ($scope.numeroDaPaginaAtual + 1 < $scope.Modulos.totalDePaginas) {
                $scope.numeroDaPaginaAtual = ($scope.Modulos.paginaAtual + 1);
                obterTodos();
            } else window.alert("Não há mais páginas para navegar! Limite máximo de páginas é: " + $scope.Modulos.totalDePaginas);

        }

        $scope.paginaAnterior = function() {
            if ($scope.numeroDaPaginaAtual != 0) {
                $scope.numeroDaPaginaAtual = ($scope.Modulos.paginaAtual - 1);
                obterTodos();
            } else window.alert("Não há mais páginas para navegar! Limite máximo de páginas é: " + 1);

        }

        var obterTodos = function() {
            ModuloApi.obter($scope.tamanhoDaPagina, $scope.numeroDaPaginaAtual).then(function(modulos) {
                $scope.Modulos = modulos.data;
            });
        }
        $scope.ordenarPor = function(campo) {
            $scope.criterioDeOrdenacao = campo;
            $scope.direcaoDaOrdenacao = !$scope.direcaoDaOrdenacao;
        };
        obterTodos();
    });
})();