(function($) {

	var paramsToJson = function(params) {
		var json = {};

		for (var i = 0; i < params.length; i++)
			json[params[i]] = params[++i];

		return json;
	};

	window.onhashchange = function() {
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
			$.getJSON('s/book/write', function(write) {
				$.load('.main-content', { write : write, bookId : params[0], chapterId : params[1]}, {componentName : 'chapterInclude', componentPath : 'book'});
			});
			break;
		case 'books' :
			$.getJSON('s/book' + (params[0] ? '/fromUser/' + params[0] : ''), function(books) {
				$.load('.main-content', {books : books}, {componentName : 'bookList', componentPath : 'book'});
			});
			break;
		default :
			$.load('.main-content', {me : true}, {componentName : 'home'});
		}

		return false;
	};
})(window.jQuery);
