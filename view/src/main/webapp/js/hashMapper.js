(function($) {
	window.onhashchange = function(e) {
		var hash = location.hash.substring(1);

		switch (hash) {
		case 'configuration' :
			$.getJSON('s/image/uploadUrls?' + $.param({callbacksUrl : ['/s/user/profilePic', '/s/user/backgroundPic'] }), function(uploadUrls) {
				$.load('.main-content', {uploadUrls : uploadUrls}, {componentName : 'configuration'});
			});
			break;
		default :
			$.load('.main-content', {}, {componentName : 'home'});
		}

		return false;
	}
})(window.jQuery);