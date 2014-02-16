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

	$.fn.message = function(opts) {
		if (!$.isPlainObject(opts))
			opts = {text : opts};

		if (!opts.text)
			return $(this);

		var messageDiv = $('<div class="message ' + (opts.type ? opts.type : 'success') + '" data-no-animation="true">' + i18n(opts.text) + '</div>').hide().click(function() {
			$(this).hide('fast');
		});

		$(this)
			.find('.message').hide('fast', function() {
				$(this).remove();
			}).end()
			.prepend(messageDiv);

		messageDiv.show('fast');
		if (!opts.noScroll)
			$(window).scrollTop($(this).find('.message').position().top - $('.header').outerHeight(), 'fast');

		return this;
	};

	$.fn.fitText = function(opts) {
		opts = opts || {};
		opts.minFontSize = opts.minFontSize || 12;
		opts.maxFontSize = opts.maxFontSize || 40;

		return $(this).each(function() {

			opts.preferedWidth = opts.preferedWidth || $(this).outerWidth();
			var fontSize = parseInt($(this).css('font-size').replace('px', ''));

			if (opts.minFontSize >= fontSize)
				$(this).css('font-size', opts.minFontSize + 'px');
			else if (opts.maxFontSize <= fontSize)
				$(this).css('font-size', opts.maxFontSize + 'px');

			var actualWidth = this.scrollWidth;
			fontSize = parseInt($(this).css('font-size').replace('px', ''));

			while (opts.minFontSize < fontSize && opts.preferedWidth < actualWidth) {
				actualWidth = this.scrollWidth;
				console.info(fontSize, actualWidth);
				$(this).css('font-size', --fontSize + 'px');
			}

			var padding = ($(this).parent().outerHeight() - $(this).outerHeight()) / 2;
			$(this).css('padding', padding + 'px' + ' 0');
		});
	};

	$.fn.inputFile = function() {

		return $(this).each(function() {
			var span = $('<span class="btn btn-fileinput"></span>').css({
				'position' : $(this).data('position') || 'relative',
				'overflow' : 'hidden',
				'display' : 'inline-block'
			});

			$(this).css({
				'position' : 'absolute',
				'top' : 0,
				'right' : 0,
				'margin' : 0,
				'opacity' : 0,
				'-ms-filter': 'alpha(opacity=0)',
				'cursor' : 'pointer'
			}).wrap(span);
			$(this).before('<span>' + $(this).data('message') + '</span>');
		});
	};

})(window.jQuery);
