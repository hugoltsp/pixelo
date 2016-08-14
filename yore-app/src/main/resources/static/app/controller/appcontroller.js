'use strict'

angular.module('app').controller('appController', [ '$scope','Upload', function($scope, Upload) {

	$scope.pixelSize = 5;
	
	$scope.upload = function(file){
		var up = Upload.upload({
			url:'app/upload',
			resumeChunkSize:'20MB',
            data: {file: file, 'pixelSize': $scope.pixelSize}
		});
		
		up.then(function(r){
			console.log(r);
			saveFile(r.data.image, r.data.name);
		},function(r){
			$.snackbar({content:"Ops!.. =/", style:"snackbar", timeout:3000});
		});
		
	}

	function saveFile(data, name) {
    	var a         = document.createElement('a');
		a.href        = "data:image/*;base64," + data;
		a.target      = '_blank';
		a.download    = name;
		document.body.appendChild(a);
		a.click();
        
	};
	
	var init = function(){
		var pixelSizeValueElement = document.getElementById('pixelSizeValue');
		var slider = document.getElementById('pixelSizeSlider');
		
		noUiSlider.create(slider,{
			start: 8,
			orientation: 'horizontal',
			connect: "lower",
			step: 1,
			range: { 	
				'min': 1,
				'max': 15
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