'use strict'

angular.module('app').controller('appController', [ '$scope','Upload', function($scope, Upload) {

	$scope.pixelSize = 5;
	
	$scope.upload = function(file){
		console.log(file);
		Upload.upload({
			url:'app/upload',
			resumeChunkSize:'20MB',
            data: {file: file, 'pixelSize': $scope.pixelSize}
		});
		
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
			var val = parseInt(values[handle]);
			$scope.pixelSize = val;
			pixelSizeValueElement.innerHTML = val;
		});
	}
	
	init();
	
} ]);