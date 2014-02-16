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

})(window.jQuery);
