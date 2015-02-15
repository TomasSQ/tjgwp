(function($) {

	book = {};

	book.load = function(userId, complete) {
		if (!complete && typeof userId == 'function') {
			complete = userId;
			userId = undefined;
		}

		$.getJSON('s/book' + (userId ? '/fromUser/' + userId : ''), function(books) {
			if (userId)
				$.getJSON('s/user/' + userId, function(otherUser) {
					$.load('.main-content', {otherUser : otherUser, books : books, userId : userId}, {componentName : 'bookList', componentPath : 'book', complete : complete});
				});
			else
				$.load('.main-content', {books : books}, {componentName : 'bookList', componentPath : 'book', complete : complete});
		});
	};

	book.read = function(userId, bookId, chapterId) {
		if ($('.book-read').length == 1)
			book.readChapter(chapterId);
		else
			$.getJSON('s/book/' + (userHelper.myId() == userId ? '' : 'fromUser/' + userId + '/') + bookId, function(aBook) {
				$.getJSON('s/user/' + userId, function(otherUser) {
					$.load('.main-content', 
						{activeChapter : chapterId, otherUser : otherUser, book : aBook, userId : userId},
						{componentName : 'bookRead', componentPath : 'book'}
					);
				});
			});
	};

	book.readChapter = function(chapterId) {
		$('.book-read .sidebar .chapter-list a').removeClass('active');
		var chapter = $('.book-read .chapter-list a[data-chapter-id="' + chapterId + '"]').addClass('active').data('chapter');

		$('.chapter-read')
			.find('h1').text(chapter.title).end()
			.find('p').html(chapter.textEntry.replace(/\n/g, '<br>'));
		if (chapter.capeImageUrl)
			$('.chapter-read .chapter-cape').removeClass('hidden').children('img').attr('src', chapter.capeImageUrl);
		else
			$('.chapter-read .chapter-cape').addClass('hidden').children('img').attr('src', '');

		var bread = $('.breadcrumb span:last');
		bread.data('original', bread.data('original') || bread.text());
		bread.text(bread.data('original') + ' - ' + chapter.title);
	};

})(window.jQuery);
