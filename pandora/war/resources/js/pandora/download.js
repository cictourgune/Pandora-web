$(document).on("tap", "#startedlinkAndroid", function(event){ 
	 var target1 = $.mobile.activePage.find('#startedsectionAndroid').get(0).offsetTop;
	 $.mobile.silentScroll(target1); 
});
$(document).on("tap", "#pushlink", function(event){ 
	 var target1 = $.mobile.activePage.find('#pushsection').get(0).offsetTop;
	 $.mobile.silentScroll(target1); 
});

$(document).on("tap", "#apilink", function(event){ 
	 var target1 = $.mobile.activePage.find('#apisection').get(0).offsetTop;
	 $.mobile.silentScroll(target1); 
});
$(document).on("tap", "#startedlinkiOS", function(event){ 
	 var target1 = $.mobile.activePage.find('#startedsectioniOS').get(0).offsetTop;
	 $.mobile.silentScroll(target1); 
});
$(document).on("tap", "#apnsiOS", function(event){ 
	 var target1 = $.mobile.activePage.find('#apnssectioniOS').get(0).offsetTop;
	 $.mobile.silentScroll(target1); 
});



 