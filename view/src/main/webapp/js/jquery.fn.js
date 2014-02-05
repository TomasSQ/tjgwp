(function($) {

	$.fn.toJson = function() {
		var json = {};

		$(this)
			.find('input[type="text"], input[type="checkbox"]:checked, input[type="radio"]:checked, textarea, select')
			.filter('[name]:visible:not([disabled])')
			.each(function() {
				json[$(this).attr('name')] = $(this).val();
			});
		return json;
	};

})(window.jQuery);
