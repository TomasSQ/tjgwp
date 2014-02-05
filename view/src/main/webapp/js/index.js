(function($) {

	var doTCache = {};

	var render = function(templateObj, selector, context, opts) {
		context = $.extend({user : $('body').data('user')}, context);
		var $template = $(templateObj.templateFunc(context));

		$(selector).empty().append($template);

		templateObj.script.each(function() {
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
					doTCache[componentPath + componentName] = {
						templateFunc : doT.template(template.children('dot').html()),
						script : template.children('script')
					}
					render(doTCache[componentPath + componentName], selector, context, opts);
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
