(function($) {

	var doTCache = {};

	var render = function(templateFunction, template, selector, context, opts) {
		context = $.extend({user : $('body').data('user')}, context);
		var $template = $(templateFunction(context));

		$(selector).empty().append($template);

		template.children('script').each(function() {
			eval($(this).html());
		});

		if (typeof opts.complete == 'function')
			opts.complete(context, template);
	};

	$.load = function(selector, context, opts) {
		var opts = opts || {};
		var componentName = opts.componentName || $(selector).data('component');
		var componentPath = opts.componentPath || $(selector).data('path') || componentName;
		if (doTCache[componentPath + componentName])
			render(doTCache[componentPath + componentName], selector, context, opts);
		else
			$.ajax({
				type: 'GET',
				url: 'component/' + componentPath + '/' + componentName + '.xml?_' + new Date().getTime(),
				dataType: 'text',
				success: function(template) {
					template = $(template);
					doTCache[componentPath + componentName] = doT.template(template.children('dot').html());
					render(doTCache[componentPath + componentName], template, selector, context, opts);
				}
			});
	};

	index = {};

	index.init = function() {
		userHelper.update(function() {
			$.load('body > div.header');
		});
	};

})(jQuery);
