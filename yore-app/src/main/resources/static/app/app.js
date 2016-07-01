angular.module('app', []).controller('appController', [ '$scope', function($scope) {

	$scope.request = {
		yoreImage : {
			image : null,
			name : null
		},
		pixelSize : 5
	}

} ]);