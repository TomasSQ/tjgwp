(function() {

	var bundles_pt = {};

	$.getJSON('i18n/pt-BR.json', function(bundles) {
		bundles_pt = bundles;
	});

	i18n = function(key) {
		return bundles_pt[key] || key.replace(/\-/g, ' ');
	};

})();
