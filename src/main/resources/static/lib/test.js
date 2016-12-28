$(function() {
	var apis = [[${apis}]];
	var url = apis[0].apiValue;
	var sel = $('#input_baseUrl');
	var opt;
	$.each(apis, function(key, value) {
		opt = $('<option>').prop('value', value.apiValue).html(value.apiKey);
		sel.append(opt);
	});
	sel.change(function() {
		swaggerUi.headerView.showCustom();
	});
	hljs.configure({
		highlightSizeThreshold : 5000
	});
	// Pre load translate...
	if (window.SwaggerTranslator) {
		window.SwaggerTranslator.translate();
	}
	window.swaggerUi = new SwaggerUi({
		url : url,
		dom_id : "swagger-ui-container",
		supportedSubmitMethods : [],
		onComplete : function(swaggerApi, swaggerUi) {
			if (window.SwaggerTranslator) {
				window.SwaggerTranslator.translate();
			}
		},
		onFailure : function(data) {
			log("Unable to Load SwaggerUI");
		},
		docExpansion : "none",
		jsonEditor : false,
		defaultModelRendering : "schema",
		showRequestHeaders : false
	});
	window.swaggerUi.load();
	function log() {
		if ("console" in window) {
			console.log.apply(console, arguments);
		}
	}
});