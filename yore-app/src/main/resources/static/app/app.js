'use strict'

angular.module('app', [ 'flow', 'angular-loading-bar']).config(['flowFactoryProvider', function(flowFactoryProvider) {
	
	flowFactoryProvider.defaults = {
			target : 'upload.php',
			permanentErrors : [ 404, 500, 501 ],
			maxChunkRetries : 1,
			chunkRetryInterval : 5000,
			simultaneousUploads : 4,
			singleFile : true
		};
	
}]);