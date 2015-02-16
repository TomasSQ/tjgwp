(function($) {

	search = {};

	search.init = function() {
		$('.header .search.search-field').unbind().keypress(function(e) {
			if (e.keyCode == 13) {
				$('.header .search.search-icon').click();
				return false;
			}
			
		});

		$('.header .search.search-icon').unbind().click(function() {
			var word = $('.header .search.search-field').val();
			if (!word)
				return false;

			window.location.hash = '#search/' + word;
		});
	};

	search.doIt = function() {
		
	};

})(window.jQuery);
