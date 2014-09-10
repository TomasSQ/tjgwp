(function($) {

	book = {};

	book.load = function(userId, complete) {
		if (!complete && typeof userId == 'function') {
			complete = userId;
			userId = undefined;
		}

		$.getJSON('s/book' + (userId ? '/fromUser/' + userId : ''), function(books) {
			$.load('.main-content', {books : books}, {componentName : 'bookList', componentPath : 'book', complete : complete});
		});
	};

})(window.jQuery);
