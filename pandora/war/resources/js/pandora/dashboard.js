var map;
var distanceWidget;
var idfence; //id de fence seleccionada en cada momento
var fencesCircle;
var circle;
var places= [];
var fenceWidget= null;
//$(document).on('pageinit', '.ui-page', function () {
//    $(this).find('.ui-slider').width('100%');
//    $('#status_fence').slider();
//	$('#status_fence').slider('refresh');
//});


 

$(document).on("change", "#fenceType", function(){
	
	 var myslider = $(this);
 
	 if(myslider[0].selectedIndex==0){
		 $("#ibeaconid").show(500);
	 }else{
		 $("#ibeaconid").hide(500);
	 }
});

 


function loadSearchBox(){
	var input = /** @type {HTMLInputElement} */(
		      document.getElementById('target'));
		 //map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

		  var searchBox = new google.maps.places.SearchBox(
		    /** @type {HTMLInputElement} */(input));
		  var markers = [];
		  // Listen for the event fired when the user selects an item from the
		  // pick list. Retrieve the matching places for that item.
		  google.maps.event.addListener(searchBox, 'places_changed', function() {
		    places = searchBox.getPlaces();
		    for (var i = 0, marker; marker = markers[i]; i++) {
		      marker.setMap(null);
		    }

		    // For each place, get the icon, place name, and location.
		    
		    var bounds = new google.maps.LatLngBounds();
		    for (var i = 0, place; place = places[i]; i++) {
		      var image = {
		        url: place.icon,
		        size: new google.maps.Size(71, 71),
		        origin: new google.maps.Point(0, 0),
		        anchor: new google.maps.Point(17, 34),
		        scaledSize: new google.maps.Size(25, 25)
		      };

//		      // Create a marker for each place.
//		      var marker = new google.maps.Marker({
//		        map: map,
//		        icon: image,
//		        title: place.name,
//		        position: place.geometry.location
//		      });
//
//		      markers.push(marker);

		      bounds.extend(place.geometry.location);
		    }

		    map.fitBounds(bounds);
		    map.setZoom(12); 
		  });

		  // Bias the SearchBox results towards places that are within the bounds of the
		  // current map's viewport.
		  google.maps.event.addListener(map, 'bounds_changed', function() {
		    var bounds = map.getBounds();
		    searchBox.setBounds(bounds);
		  });

	
}
$(document).on("pageshow", "#dashboard-page", function(){
	
	$(this).find('.ui-slider').width('100%');
	
	if(localStorage.getItem("refreshPage") != '-1'){
		
		setTimeout(crearMapa, 700);
		
	}
	else{
		localStorage.removeItem("refreshPage");		
	}
	fencesCircle=new Array();
	
	if(localStorage.getItem("tutorial")!='1'){
		$('body').chardinJs('start');
		localStorage.setItem("tutorial", "1");
	} 
	
}); 

$(document).on("pagebeforeshow", "#dashboard-page", function(){
	$('#guardarFence').hide();
	$('#eliminarFence').hide();
	$('#botones').hide(); 
}); 


$(document).on("tap", "#confirmacion-eliminacion-no", function(){
	//window.location.href=domain()+'/private/dashboard'; 
});

$(document).on("tap", "#pinmap", function(){
	
	localStorage.setItem("pinmaplat",map.getCenter().lat());
	localStorage.setItem("pinmaplng",map.getCenter().lng()); 
});


$(document).on("click", "#help", function(){
	$( "#leftmenu" ).panel( "close" );
	$('body').chardinJs('start');	
}); 


function crearMapa(){
	var mapDiv = document.getElementById('map_canvas');
	var customCenter; 
	if(localStorage.getItem("pinmaplat")!=undefined){
		customCenter = new google.maps.LatLng(parseFloat( localStorage.getItem("pinmaplat")),parseFloat( localStorage.getItem("pinmaplng"))  );  
	}else{
		customCenter = new google.maps.LatLng(43.318266, -1.980645);
	}
	
	map = new google.maps.Map(mapDiv, {
	  center: customCenter,
	  zoom: 13,
	  mapTypeId: google.maps.MapTypeId.ROADMAP
	});

 
	// Create the search box and link it to the UI element.
    var input = /** @type {HTMLInputElement} */(
      document.getElementById('target'));
    
    var searchBox = new google.maps.places.SearchBox(
    	    /** @type {HTMLInputElement} */(input));

    	  // Listen for the event fired when the user selects an item from the
    	  // pick list. Retrieve the matching places for that item.
    	  google.maps.event.addListener(searchBox, 'places_changed', function() {
    	    var places = searchBox.getPlaces();
    	 
    	  });
	loadSearchBox(); 
 
}

$(document).on("tap", "#crearFence", function(){	

	if (fenceWidget!= null){
		deleteCurrentMarkers();
		newCircle(fenceWidget);
	}

	$('#nombreFence').val('');
	$('#idIbeacon').val('');
	
	//$('#status_fence').val('1').selectmenu('refresh');
	$('#status_fence').slider();
	$('#status_fence').slider('refresh');
	
	$('#period_fence').slider();
	$('#period_fence').slider('refresh');

	$('#fecha-desde').val('');
	$('#fecha-hasta').val('');
	$('#data').val('');
	
	$('#nombreFence').focus();
	
	$('#fenceType').val('0');
	$('#fenceType').slider();
	$('#fenceType').slider('refresh');
	$('#ibeaconid').show();
	
	if($('#fenceType').val('0')){
		distanceWidget = new DistanceWidget(map,null,0.07); 
	}else{
		distanceWidget = new DistanceWidget(map,null,null); 
	}
	
	
	getFence(null);
	//controles
	$('#eliminarFence').hide();
	$('#guardarFence').hide();
	$('#insertarFence').show();
	$('#cancelarInsertarFence').show();
	//limpiar lista
	$('#areas_creadas li').removeClass('ui-bar-b'); 
	$('#areas_creadas').listview('refresh');
	$('#botones').show();
	//Eliminar posibles mensajes de error
	$('#error-fence').empty();
});  



$(document).on('tap', '[action="getFence"]', function(event){

	//Eliminar posibles mensajes de error
	$('#error-fence').empty(); 
	$(this).closest('ul').find('li').removeClass('ui-bar-b'); 
	$('#areas_creadas').listview('refresh'); 

	$("#li_"+ this.id).addClass('ui-bar-b'); 
	$('#areas_creadas').listview('refresh');
	
	idfence = this.id;
	getFence(this.id);
});

function getFence(idfence){

	this.idfence=idfence;
	$.ajax({
        url:domain()+"/api/fence/fences",
        type: "GET", 
        contentType: "application/json", 
        success: function(fences){
    
        	var fencesArray = fences;
        	var arrayLength = fencesArray.length;
        	clearMap(fencesCircle);
        	if (idfence!=null){
        		//borrar fences de mapa
        		deleteCurrentMarkers();
        	}
        	for (var i = 0; i < arrayLength; i++) {

        		if(fencesArray[i].id==idfence){
        			distanceWidget = new DistanceWidget(map, new google.maps.LatLng(fencesArray[i].centreLatitude, fencesArray[i].centreLongitude), fencesArray[i].radius); 
        			fenceWidget=fencesArray[i];// lo guardo en una variable para despues cuando se cree un nuevo area se pueda convertir en Circle
        			updateData(fencesArray[i]);
        			
        		}else{
        			
        			newCircle(fencesArray[i]);
        		} 		
        	}
        },
        error: onErrorGetFence 
    });
}
 
function newCircle(fence){
	var populationOptions = {
		      strokeColor: '#848484',
		      strokeOpacity: 0.8,
		      strokeWeight: 2,
		      fillColor: '#848484',
		      fillOpacity: 0.20,
		      map: map,
		      center: new google.maps.LatLng(fence.centreLatitude, fence.centreLongitude),
		      radius: fence.radius*1000
		    };

	    
	 //Add the circle for this city to the map.
	 circle= new google.maps.Circle(populationOptions);
	 circle.objInfo = fence;
	 fencesCircle.push(circle);
	 google.maps.event.addListener(circle, 'click', function(){
		 updateData(this.objInfo);
		 getFence(this.objInfo.id);
	 });
	 google.maps.event.addListener(circle, 'mouseover', function(){
		 this.getMap().getDiv().setAttribute('title',this.objInfo.name);
	 });
	 google.maps.event.addListener(circle,'mouseout',function(){
     this.getMap().getDiv().removeAttribute('title');});
}
function clearMap(fencesCircle){
	while(fencesCircle[0]){
		fencesCircle.pop().setMap(null);
	}
}


function updateData(fences){
	
	//Eliminar posibles mensajes de error
	$('#error-fence').empty();
	$("#li_"+idfence).closest('ul').find('li').removeClass('ui-bar-b'); 
	$('#areas_creadas').listview('refresh'); 
	$("#li_"+ fences.id).addClass('ui-bar-b');
	$('#areas_creadas').listview('refresh'); 
		
	if(fences.ibeacon != undefined && fences.ibeacon !=null && fences.ibeacon !=''){
		$('#fenceType').val(0); 
		$('#ibeaconid').show();
	}else{
		$('#fenceType').val(1); 
		$('#ibeaconid').hide();
	}
		
    $('#fenceType').slider();
	$('#fenceType').slider('refresh');


	$('#nombreFence').val(fences.name);
	$('#idIbeacon').val(fences.ibeacon);
	$('#data').val(fences.rule.data); 
	//cargar controles
	$('#insertarFence').hide();
	$('#cancelarInsertarFence').hide();
	$('#eliminarFence').show();
	$('#guardarFence').show();
	//almacenar id 
	localStorage.setItem("idfence", idfence);
	//cargar regla
	$('#status_fence').val(fences.rule.status_fence); 
    $('#status_fence').slider();
	$('#status_fence').slider('refresh');
	
	//cargar regla
	$('#period_fence').val(fences.rule.period_fence); 
    $('#period_fence').slider();
	$('#period_fence').slider('refresh');
//	$("#status_fence").selectmenu('refresh', true);

	$('#destinatario').val(fences.rule.destination); 
	
	
	$('#destinatario').slider();
	$('#destinatario').slider('refresh');
	 
	$('#hora-desde').val(fences.rule.fromHour); 
	$('#hora-hasta').val(fences.rule.toHour);  
	$('#hora-desde').slider("refresh");
	$('#hora-hasta').slider("refresh"); 
	
	var desde = new Date(fences.rule.fromDate); 
	var hasta = new Date(fences.rule.toDate); 
	
	if(desde.getFullYear()!=200 && hasta.getFullYear()!=3000){
		$('#fecha-desde').val(fences.rule.fromDate);   
		$('#fecha-hasta').val(fences.rule.toDate); 
	}else{
		$('#fecha-desde').val('');   
		$('#fecha-hasta').val(''); 
	}
	$('#botones').show();
}


function onErrorGetFence(){
	console.log("error showing fence!");
}


$(document).on("tap", "#eliminarFence", function(){ 
//	localStorage.setItem("refreshPage", '-1');
//	$.mobile.changePage(domain()+'/private/pageConfirmacionDialog', {role: 'dialog'});  
});

$(document).on('tap', '#confirmacion-eliminacion-ok', function(event){
	$.ajax({
        url:domain()+"/api/fence/"+idfence,
        type: "DELETE",  
        success: function(data){
        	if(data==1){
        		window.location.href=domain()+'/private/dashboard'; 
        		
        		//Eliminar posibles mensajes de error
        		$('#error-fence').empty();
        		//eliminar id de fence
        		$("#li_"+idfence).remove();
        		//limpiar mapa
        		deleteCurrentMarkers();
        		clearMap(fencesCircle);
        		getFence(null);

        		localStorage.removeItem("idfence");
        		idfence = null; 
        		//limpiar campos
        		$('#nombreFence').val('');
        		$('#idIbeacon').val('');
        	
        		$('#data').val('');
        		$('#botones').hide();        		
        	} 
        },
        error: onErrorDeleteFence
    });
});
function onErrorDeleteFence(){
	console.log("Error deleting fence!");
}


$(document).on("tap", "#insertarFence", function(){   
	
	if(comprobarDatosFence()){
		//ajax post
		var fence = new Object();
		fence.name = $('#nombreFence').val(); 
		
		if($('#fenceType').val()==0){
			fence.ibeacon = $('#idIbeacon').val();  
		}else{
			fence.ibeacon ='';
		}

		fence.radius = distanceWidget.get('distance');  

		fence.centreLatitude = distanceWidget.get('position').lat();
		fence.centreLongitude = distanceWidget.get('position').lng(); 
		var rule = new Object();
		rule.status_fence = $('#status_fence').val();
		rule.period_fence = $('#period_fence').val();
		rule.fromDate = $('#fecha-desde').val();
		rule.toDate = $('#fecha-hasta').val(); 
		rule.data = $('#data').val(); 
		rule.destination = $('#destinatario').val(); 
		rule.fromHour =  $('#hora-desde').val(); 
		rule.toHour =$('#hora-hasta').val();  
		
		fence.rule = rule;
		
		var fenceJSON = JSON.stringify(fence);

		 
		$.ajax({
	        url:domain()+"/api/fence/",
	        type: "POST", 
	        contentType: "application/json", 
	        data: fenceJSON ,
	        success: function(data){
	        	$('#error-fence').html('');
	        	//añadir a la lista
	        	if(data!=-1){
	        		$("<li id='li_"+data+"' data-fenceid='"+data+"'><a id='"+data+"' action='getFence' href='#'><h3>"+fence.name+"</h3></a></li>").insertAfter($('#li_botones')); 
	        		$("#li_"+ data).addClass('ui-bar-b');
	        		$('#areas_creadas').listview('refresh'); 
	        		localStorage.setItem("idfence", data);
	        		idfence = data;       		
	        		
	        		var newfence = new Object();
	        		newfence.id=idfence;
	        		newfence.name=fence.name;
	        		fenceWidget=newfence;
	        		
	        		//Sustituir botones de creación por los de edición
	        		$('#insertarFence').hide();
	        		$('#cancelarInsertarFence').hide();
	        		$('#guardarFence').show();
	        		$('#eliminarFence').show();
	        		
	        		//mensaje de creada! 
	        		$('#mnsPopup').text('Created!');
	        		$('#mypopup').popup('open');
					setTimeout(function(){$('#mypopup').popup('close');},3000);
					
	        	}else{
	        		//mensaje de existente! 
	        		$('#mnsPopup').text('Ups! Existing fence name!');
	        		$('#mypopup').popup('open');
					setTimeout(function(){$('#mypopup').popup('close');},3000);
	        	} 
	        },
	        error: onErrorInsertarFence
	    });  
		 
	}
}); 

$(document).on("tap", "#cancelarInsertarFence", function(){  
	deleteCurrentMarkers();
	$('#botones').hide();
	//Eliminar posibles mensajes de error
	$('#error-fence').empty();
});  
function onErrorInsertarFence(){
	console.log('error!');
}

function byteCount(s) {
    return encodeURI(s).split(/%..|./).length - 1;
}


$(document).on("tap", "#guardarFence", function(){  
	if(comprobarDatosFence()){
		//ajax put
		var fence = new Object();
		fence.id = idfence;
		fence.name = $('#nombreFence').val();  
		//fence.ibeacon = $('#idIbeacon').val();  
		
		
		if($('#fenceType').val()==0){
			fence.ibeacon = $('#idIbeacon').val();  
		}else{
			fence.ibeacon = '';
		}
		
		
		fence.radius = distanceWidget.get('distance');	 		
		fence.centreLatitude = distanceWidget.get('position').lat();
		fence.centreLongitude = distanceWidget.get('position').lng(); 
		
		var rule = new Object();
		rule.status_fence = $('#status_fence').val();
		rule.period_fence = $('#period_fence').val();
		rule.fromDate = $('#fecha-desde').val();
		rule.toDate = $('#fecha-hasta').val(); 
		rule.data = $('#data').val(); 
		rule.destination = $('#destinatario').val(); 
		rule.fromHour =  $('#hora-desde').val(); 
		rule.toHour =$('#hora-hasta').val();  
		fence.rule = rule;
		
		var fenceJSON = JSON.stringify(fence);	


		$.ajax({
	        url:domain()+"/api/fence/",	//ID fence!!!
	        type: "PUT", 
	        contentType: "application/json", 
	        data: fenceJSON ,
	        success: function(data){
	        	$('#error-fence').html('');
	        	//añadir a la lista
	        	if(data!=-1){
		        	//actualizar datos de la fence editada en la lista de áreas
	        		$("#"+idfence).find("h3").html(fence.name);
	        		$('#areas_creadas').listview('refresh');
	        		//Eliminar posibles mensajes de error
	        		$('#error-fence').empty();
	        		
	        		//mensaje de actualizada! 
	        		$('#mnsPopup').text('Updated!');
	        		$('#mypopup').popup('open');
					setTimeout(function(){$('#mypopup').popup('close');},3000);
	        	}else{
	        		//mensaje de existente! 
	        		$('#mnsPopup').text('Ups! There is a fence with the same name!');
	        		$('#mypopup').popup('open');
					setTimeout(function(){$('#mypopup').popup('close');},3000);
	        	} 
	        },
	        error: onErrorGuardarFence
	    });  
		 
	}
});

function onErrorGuardarFence(){
	console.log("error al guardar el fence!");
}


$(document).on("tap", "#guardarEndpoint", function(){
	//ajax put
	var endpoint = $("#endpointUrl").val(); 
 	
	if(endpoint.length > 0 && validateUrl(endpoint)){
		var developer = new Object();
		developer.endpoint = endpoint;

		var developerJSON = JSON.stringify(developer);

		$.ajax({
	        url:domain()+"/api/developer/",
	        type: "PUT", 
	        contentType: "application/json", 
	        data: developerJSON,
	        success: function(data){
	        	$('#error-endpoint').html('');
	        	//mensaje de confirmación 
        		$('#mnsPopup').text('Saved!');
        		$('#mypopup').popup('open');
				setTimeout(function(){$('#mypopup').popup('close');},1500);
	        },
	        error: onErrorGuardarEndpoint
	    });  
	}else{
		$('#error-endpoint').html('<div style="margin-bottom: 10px; text-align:center; color:red;">This URL is not valid!</div>');
	}
});


function validateUrl(value){
    return /^(https?|http):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
  }

function onErrorGuardarEndpoint(){
	console.log("error al guardar el endpoint URL!");
	//mensaje de existente! 
	$('#mnsPopup').text('Ups! Error saving the URL!');
	$('#mypopup').popup('open');
	setTimeout(function(){$('#mypopup').popup('close');},3000);
}


function comprobarDatosFence(){
	
	var errorMsg = "";
	var ok = true;
	 
	//Validar radio
	if($('#fenceType').val()==1 && distanceWidget.get('distance')*1000<100){
		errorMsg += '* The radio has to be at least 100m.</br>';		
		ok = false;
	}
	
	if($('#fenceType').val()==0 && distanceWidget.get('distance')*1000>70){
		errorMsg += '* The radio has to be lower than 70m.</br>';		
		ok = false;
	}
	
	//Validar nombre
	var nombreRegExp = /[a-zA-Z0-9]/;  
	if( !nombreRegExp.test($('#nombreFence').val()) ){ 
		$('#nombreFence').select();
		errorMsg += '* The name of the fence must have at least one character</br>';		
		ok = false;
	}
	
	//validar el ibeacon id si está marcada como indoors
	if($('#idIbeacon').val().trim().length<10 && $('#fenceType').val()==0){  
		errorMsg += '* The ibeacon id must be specified for indoor fences</br>';		
		ok = false;
	}
	
	//validar datos
	if(byteCount($('#data').val())>4000){
		errorMsg += '* Excesive data</br>';	
		ok = false;
	}
	
	//validar endpoint
	if($('#destinatario').val()==0){
		if($('#endpointUrl').val()==''){
			errorMsg += '* You have to configure an endpoint!.</br>';	
			ok = false;
		} 
	} 

	//Validar de fecha
	var desde = new Date($('#fecha-desde').val().split("-")[0], $('#fecha-desde').val().split("-")[1], $('#fecha-desde').val().split("-")[2]);
	var hasta = new Date($('#fecha-hasta').val().split("-")[0], $('#fecha-hasta').val().split("-")[1], $('#fecha-hasta').val().split("-")[2]);

	if(desde.getTime() > hasta.getTime()){
		errorMsg += '* Incorrect date range</br>';				
		ok = false;
	}
	if(!ok){
		$('#error-fence').empty();
		//Mostrar DIV con el mensaje de error
		$('#error-fence').html('<div style="margin-bottom: 10px; text-align:center; color:red;">' + errorMsg);
	}
	
	return ok;
}
$(document).on("tap", "#guardarCertURL", function(){
	//ajax put

	var certUrl = $("#certURL").val();
	var valido=validateUrl(certUrl);
	//Comprobar que la URL está bien si termina en .p12
	if (valido && (certUrl.substring(String(certUrl).length-4,String(certUrl).length)==".p12")){
		var developer = new Object();
		developer.certURL = certUrl;

		var developerJSON = JSON.stringify(developer);
		$.ajax({
	        url:domain()+"/api/developer/cert/",
	        type: "PUT", 
	        contentType: "application/json", 
	        data: developerJSON,
	        success: function(data){
	        	$('#mnsPopup').text('Correctly saved!');
	        	$('#mypopup').popup('open');
	        	setTimeout(function(){$('#mypopup').popup('close');},3000);
	        },
	        error: onErrorGuardarCertURL
	    });  
	}else{
		$('#error-certURL').html('<div style="margin-bottom: 10px; text-align:center; color:red;">This URL is not valid!</div>');
	}
	
});
function onErrorGuardarCertURL(){
	console.log("error al guardar al crear el certificado!"); 
	$('#mnsPopup').text('Ups! Error saving the URL of iOS APP Certificate!');
	$('#mypopup').popup('open');
	setTimeout(function(){$('#mypopup').popup('close');},3000);
}
   