'use strict'

angular.module('app', [ 'flow' ]).config(['flowFactoryProvider', function(flowFactoryProvider) {
		flowFactoryProvider.defaults = {
			target : 'upload.php',
			permanentErrors : [ 404, 500, 501 ],
			maxChunkRetries : 1,
			chunkRetryInterval : 5000,
			simultaneousUploads : 4,
			singleFile : true
		};
		flowFactoryProvider.on('catchAll', function(event) {
			console.log('catchAll', arguments);
		});}]).controller('appController', [ '$scope', function($scope) {

	var init = function(){
		var slider = document.getElementById('pixelSizeSlider');
		noUiSlider.create(slider,{
			start: 5,
			orientation: 'vertical',
			connect: "lower",
			range: { 
				'min': 1,
				'max': 10
			},
		});
	}
	
	init();
			
	$scope.request = {
		yoreImage : {
			image : null,
			name : null
		},
		pixelSize : 5
	}

} ]);