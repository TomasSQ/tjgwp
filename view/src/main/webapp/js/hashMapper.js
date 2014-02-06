(function($) {

	var paramsToJson = function(params) {
		var json = {};

		for (var i = 0; i < params.length; i++)
			json[params[i]] = params[++i];

		return json;
	};

	window.onhashchange = function(e) {
		var hash = location.hash.substring(1).split('/');
		var params = hash.splice(1);
		hash = hash[0];

		switch (hash) {
		case 'configuration' :
			$.getJSON('s/image/uploadUrls?' + $.param({callbacksUrl : ['/s/user/profilePic', '/s/user/backgroundPic'] }), function(uploadUrls) {
				$.load('.main-content', {uploadUrls : uploadUrls}, {componentName : 'configuration'});
			});
			break;
		case 'write' :
			$.load('.main-content', {}, {componentName : 'textInclude', componentPath : 'text'});
			break;
		case 'text' :
			$.getJSON('s/user/' + params[0] + '/texts', function(uploadUrls) {
				$.load('.main-content', {uploadUrls : uploadUrls}, {componentName : 'configuration'});
			});
		default :
			$.load('.main-content', {}, {componentName : 'home'});
		}

		return false;
	}
})(window.jQuery);