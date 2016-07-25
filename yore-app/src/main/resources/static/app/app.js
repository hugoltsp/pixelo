'use strict'

angular.module('app', [ 'flow', 'angular-loading-bar','ngAnimate','ngFileUpload']).config(['flowFactoryProvider', function(flowFactoryProvider) {
	
	flowFactoryProvider.defaults = {
			target : 'app/upload',
			permanentErrors : [ 404, 500, 501 ],
			maxChunkRetries : 0,
			chunkRetryInterval : 5000,
			simultaneousUploads : 4,
			singleFile : true,
			testChunks: false
		};
}]);