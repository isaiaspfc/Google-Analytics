angular.module("exercicioAngularJS").directive("Alerta", function() {
    return {
        templateUrl: "view/alert.html",
        replace: true,
        restrict: "AE",
        scope: {
            title: "@"
        },
        transclude: true
    };
});