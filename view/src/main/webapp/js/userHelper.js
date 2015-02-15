(function($) {

	userHelper = {};

	userHelper.update = function(callback) {
		$.getJSON('s/user/?_=' + new Date().getTime() , function(user) {
			if (user.email)
				$('body').data('user', user);

			if (typeof callback === 'function')
				callback(user);
		});
	};

	userHelper.myId = function() {
		if (!$('body').data('user'))
			updateHelper.update(userHelper.myId);
		else
			return $('body').data('user').id
	};
})(window.jQuery);
