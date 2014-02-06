(function($) {

	utils = {};

	utils.text = function(opts) {
		if (!opts.text) {
			var noValue = opts.noValue || '';
			return opts.i18n ? i18n(noValue) : noValue;
		}

		if (opts.plural && Math.abs(opts.number || opts.text) >= 2) {
			if (opts.sufix)
				return opts.text + ' ' + (opts.i18n ? i18n(opts.sufix + 's') : opts.sufix + 's');

			return opts.i18n ? i18n(opts.text + 's') : opts.text + 's';
		}

		if (opts.sufix)
			return opts.text + ' ' + (opts.i18n ? i18n(opts.sufix) : opts.sufix);

		return opts.text;
	};

})(window.jQuery);
