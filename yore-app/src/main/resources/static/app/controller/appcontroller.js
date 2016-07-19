'use strict'

angular.module('app').controller('appController', [ '$scope',  function($scope) {

	$scope.pixelSize = 5;

	$scope.yoreUpload = function($flow){
		$flow.opts.query.pixelSize=$scope.pixelSize;
		$flow.upload();
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
	
	$scope.$on('flow::fileSuccess', function (event, $flow, flowFile, $file, $message) {
		console.log(arguments);
		 var a         = document.createElement('a');
		 a.href        = 'data:jpg/image,' +  encodeURI($file);
		 a.target      = '_blank';
		 a.download    = 'huehue.jpg';
		 document.body.appendChild(a);
		 a.click();
	});
	
} ]);