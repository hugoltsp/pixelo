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
			saveByteArray(base64ToArrayBuffer(r.data.image), r.data.name);
		});
		
	}

	function base64ToArrayBuffer(base64) {
	    var binaryString =  window.atob(base64.replace(/\s/g, ''));
	    var binaryLen = binaryString.length;
	    var bytes = new Uint8Array(binaryLen);
	    for (var i = 0; i < binaryLen; i++)        {
	        var ascii = binaryString.charCodeAt(i);
	        bytes[i] = ascii;
	    }
	    return bytes;
	}

	function saveByteArray(data, name) {
	    var a = document.createElement("a");
	    document.body.appendChild(a);
	    a.style = "display: none";
        var blob = new Blob(data, {type: "octet/stream"}),
            url = window.URL.createObjectURL(blob);
        a.href = url;
        a.download = name;
        a.click();
	};
	
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