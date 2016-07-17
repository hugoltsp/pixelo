'use strict'

angular.module('app').controller('appController', [ '$scope',  function($scope) {

	$scope.req = {
		yoreImage : {
			image : null,
			name : null
		},
		pixelSize : 5
	}
		
	var init = function(){
		var pixelSizeValueElement = document.getElementById('pixelSizeValue');
		var slider = document.getElementById('pixelSizeSlider');
		
		noUiSlider.create(slider,{
			start: 5,
			orientation: 'horizontal',
			connect: "lower",
			step: 1,
			range: { 
				'min': 1,
				'max': 10
			}
		});
		
		slider.noUiSlider.on('update', function(values, handle) {
			$scope.req.pixelSize = values[handle];
			pixelSizeValueElement.innerHTML = values[handle];
		});
	}
	
	init();
	
} ]);