angular.module("exercicioAngularJS").config(function($routeProvider) {
    $routeProvider.when("/pagina-bem-vindo", {
        templateUrl: "pagina-bem-vindo.html",
    });

    $routeProvider.otherwise({ redirectTo: "/pagina-bem-vindo" });

});