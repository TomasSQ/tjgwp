(function($) {

	utils = {};

	utils.text = function(opts) {
		if (!opts.text) {
			var noValue = opts.noValue || '';
			return opts.i18n ? i18n(noValue) : noValue;
		}

		if (opts.plural && Math.abs(opts.number || opts.text) >= 2) {
			if (opts.suffix)
				return opts.text + ' ' + (opts.i18n ? i18n(opts.suffix + 's') : opts.suffix + 's');

			return opts.i18n ? i18n(opts.text + 's') : opts.text + 's';
		}

		if (opts.suffix)
			return opts.text + ' ' + (opts.i18n ? i18n(opts.suffix) : opts.suffix);

		return opts.text;
	};

	utils.breadcrumb = function(crumb, clear) {
		var crumbs = clear ? [] : $('.breadcrumb').data('crumbs') || [];
		var links = clear ? [] : $('.breadcrumb').data('links') || [];

		if (crumb) {
			if (!$.isPlainObject(crumb))
				crumb = {text : i18n(crumb), link : crumb};
	
			crumbs.push(crumb.text);
			links.push(crumb.link);
		}

		var breadcrumb = '';
		for (var i = 0; i < crumbs.length; i++) {
			var isLast = i + 1 == crumbs.length;
			breadcrumb += '<h3 class="bold title">';
			if (!isLast)
				breadcrumb += '<a href="#' + links[i] + '" data-index="' + i + '">';
			breadcrumb += '<span>' + crumbs[i] + '</span>';
			if (!isLast)
				breadcrumb += '</a>';
			breadcrumb += '</h3>';
			if (!isLast)
				breadcrumb += ' > ';
		}
		
		$('.breadcrumb').html(breadcrumb).data('crumbs', crumbs).data('links', links).show();

		$('.breadcrumb a').unbind().click(function() {
			var crumbs = $('.breadcrumb').data('crumbs');
			var links = $('.breadcrumb').data('links');

			var i = $(this).data('index') + 1;
			crumbs.splice(i);
			links.splice(i);

			$('.breadcrumb').data('crumbs', crumbs).data('links', links);
			utils.breadcrumb();
		});
	};

	utils.fileupload = function(elements, callback) {
		return elements.each(function() {
			$(this).fileupload({
				submit: function (e, data) {
					var $this = $(this);
					$.get('s/image/uploadUrl?' + $.param({callbackUrl : $this.data('callback-url')}), function(uploadUrl) {
						data.url = uploadUrl;

						$this.closest('form').attr('action', data.url);
						$this.fileupload('send', data);
					});
					return false;
				},
				done : function(e, data) {
					if (typeof callback === 'function')
						callback.apply(this, [e, data]);
				}
			});
		});
	};

	var oneSecond = 1000;
	var oneMinute = 60 * oneSecond;
	var oneHour = 60 * oneMinute;
	var oneDay = 24 * oneHour;

	utils.timestampToString = function(timestamp) {
		var delta = new Date().getTime() - timestamp;

		if (delta < oneSecond)
			return i18n('just-now');
		if (delta < oneMinute)
			return Math.floor(delta / oneSecond) + 's ';
		if (delta < oneHour)
			return Math.floor(delta / oneMinute) + 'm';
		if (delta < oneDay)
			return Math.floor(delta / oneHour) + 'h';

		return utils.timestampAsDate(timestamp);
	};

	var zero = function(n) {
		return n < 10 ? '0' : ''; 
	};

	utils.timestampAsDate = function(timestamp) {
		var date = new Date(timestamp);

		return zero(date.getDate()) + date.getDate() + '/' + zero(date.getMonth()) + date.getMonth() + '/' + date.getFullYear();
	}

})(window.jQuery);
