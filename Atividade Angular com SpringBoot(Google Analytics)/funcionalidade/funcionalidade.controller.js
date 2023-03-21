(function() {
    'use strict';
    angular.module("exercicioAngularJS").controller("FuncionalidadeController", function($scope, FuncionalidadeApi, $location) {
        $scope.Funcionalidades = [];
        $scope.tamanhoDaPagina = 5;
        $scope.numeroDaPaginaAtual = 0;

        $scope.mudarExibicao = function(numero) {
            $scope.tamanhoDaPagina = numero;
            obterTodos();
        }

        $scope.proximaPagina = function() {
            if ($scope.numeroDaPaginaAtual + 1 < $scope.Funcionalidades.totalDePaginas) {
                $scope.numeroDaPaginaAtual = ($scope.Funcionalidades.paginaAtual + 1);
                obterTodos();
            } else window.alert("Não há mais páginas para navegar! Limite máximo de páginas é: " + $scope.Funcionalidades.totalDePaginas);

        }

        $scope.paginaAnterior = function() {
            if ($scope.numeroDaPaginaAtual != 0) {
                $scope.numeroDaPaginaAtual = ($scope.Funcionalidades.paginaAtual - 1);
                obterTodos();
            } else window.alert("Não há mais páginas para navegar! Limite máximo de páginas é: " + 1);

        }

        var obterTodos = function() {
            FuncionalidadeApi.obter($scope.tamanhoDaPagina, $scope.numeroDaPaginaAtual).then(function(Funcionalidadess) {
                $scope.Funcionalidades = Funcionalidadess.data;
            });
        }
        $scope.ordenarPor = function(campo) {
            $scope.criterioDeOrdenacao = campo;
            $scope.direcaoDaOrdenacao = !$scope.direcaoDaOrdenacao;
        };
        obterTodos();

    });
})();