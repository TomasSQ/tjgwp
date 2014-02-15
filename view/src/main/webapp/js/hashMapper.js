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
			$.getJSON('s/texts/write', function(write) {
				$.load('.main-content', { write : write }, {componentName : 'chapterInclude', componentPath : 'book'});
			})
			break;
		case 'books' :
			$.getJSON('s/texts/' + params[0] + '/books', function(uploadUrls) {
				$.load('.main-content', {}, {componentName : 'bookList', componentPath : 'book'});
			});
		default :
			$.load('.main-content', {}, {componentName : 'home'});
		}

		return false;
	};
})(window.jQuery);
