(function($) {

	var doTCache = {};

	var render = function(templateFunction, selector, context, complete) {
		var template = $(templateFunction(context)).children();

		$(selector).empty().append(template);

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
					doTCache[componentPath + componentName] = doT.template(template);
					render(doTCache[componentPath + componentName], selector, context, complete);
				}
			});
	};

	$.load('body > div.header');
})(jQuery);
