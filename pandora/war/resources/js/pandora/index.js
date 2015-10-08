$(document).delegate("#index-page", "pagecreate", function () {
    $(this).css('background', 'white'); 
});

$(document).on( "pageshow", "#index-page", function () {
	   setTimeout(initialize, 500);
});

$(document).on("tap", "#entrar", function(event){ 
	$.mobile.changePage(domain()+"/private/dashboard", {reloadPage: true});   
});


$(document).on("tap", "#registrar", function(event){  
	window.location.href=domain()+'/registro'; 
});


$(function () {
    $(window).scroll(function(){
    // global scroll to top button
        if ($(this).scrollTop() > 300) {
            $('.scrolltop').fadeIn();
        } else {
            $('.scrolltop').fadeOut();
        }        
    });
    
    // scroll nav
    $('.scroller').click(function(){
    	var section = $($(this).data("section"));
    	var top = section.offset().top-82;
        $("html, body").animate({ scrollTop: top }, 700);
        return false;
    }); 
            
});
 
 
function initialize() {
  var mapOptions = {
    zoom: 14,
    center: new google.maps.LatLng(43.295047, -1.986052),
    mapTypeId: google.maps.MapTypeId.ROADMAP
  }; 
  
  var map = new google.maps.Map($("#map-canvas")[0], mapOptions);
  
  var marker = new google.maps.Marker({
    position: new google.maps.LatLng(43.295047,  -1.986052),
    map: map,
    title:"CICtourGUNE"
	});
}    
 

