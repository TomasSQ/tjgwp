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
			if (params.length <= 1)
				book.load(params[0]);
			else
				book.read(params[0], params[1], params[2]);
			break;
		case 'user' :
			if (userHelper.myId() == params[0])
				window.location.hash = '#';
			else
				$.getJSON('s/user/' + params[0], function(user) {
					$.load('.main-content', {user : user, me : false}, {componentName : 'home'});
				});
		default :
			$.load('.main-content', {me : true}, {componentName : 'home'});
		}

		return false;
	};
})(window.jQuery);
