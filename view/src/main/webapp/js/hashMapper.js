(function($) {
	window.onhashchange = function(e) {
		var hash = location.hash.substring(1);
		if (!hash && (!e || !e.oldURL || e.oldURL.indexOf('/#me') == -1)) {
			location.hash = 'me';
			return false;
		}

		switch (hash) {
		case 'me' :
			$.load('.main-content', {user : $('body').data('user')}, {componentName : 'home'});
			break;
		case 'configuration' :
			$.ajax({
				type : 'GET',
				url : 's/image/uploadUrl',
				data : {callback : '/s/user/profilePic' },
				success : function(uploadUrl) {
					$.load('.main-content', {uploadUrl : uploadUrl, user : $('body').data('user')}, {componentName : 'configuration'});
				}
			});
			break;
		}
		
		return false;
	}
})(window.jQuery);