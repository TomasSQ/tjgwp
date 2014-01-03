(function($) {

	var doTCache = {};

	var render = function(templateFunction, template, selector, context, complete) {
		var $template = $(templateFunction(context));

		$(selector).empty().append($template);

		template.children('script').each(function() {
			eval($(this).html());
		});

		if (typeof complete == 'function')
			complete(context, template);
	};

	$.load = function(selector, context, complete) {
		var componentName = $(selector).data('component');
		var componentPath = $(selector).data('path') || componentName;
		if (doTCache[componentPath + componentName])
			render(doTCache[componentPath + componentName], selector, context, complete);
		else
			$.ajax({
				type: 'GET',
				url: 'component/' + componentPath + '/' + componentName + '.xml',
				dataType: 'text',
				success: function(template) {
					template = $(template);
					doTCache[componentPath + componentName] = doT.template(template.children('dot').html());
					render(doTCache[componentPath + componentName], template, selector, context, complete);
				}
			});
	};

	index = {};

	index.init = function() {
		$.getJSON('s/user/', function(user) {
			$.load('body > div.header', user);
		});
	}
})(jQuery);
