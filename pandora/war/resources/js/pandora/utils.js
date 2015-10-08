function domain(){
	 return "/pandora";
}



//-------------------------- fence utils ---------------------------------------------------------------------
var radMetrosAnterior = 0; 

var marker = null;
var isNew=false;

var sizer = null;

function deleteCurrentMarkers(){
	if(marker!=null){
		marker.setMap(null);
	} 
}
/**
 * A distance widget that will display a circle that can be resized and will
 * provide the radius in km.
 *
 * @param {google.maps.Map} map The map to attach to.
 *
 * @constructor
 */
function DistanceWidget(map, center, radius) {
  this.set('map', map);
  
  _center =  map.getCenter();
  _radius = 1; //default 1km
  
  if(center !=null && center!=undefined){
	  _center  = center;
	  map.setCenter(center); 
	  isNew = false; //es fence de BD
  }else{
	  isNew = true;
  }
  
  this.set('position', _center);

  marker = new google.maps.Marker({
    draggable: true,
    title: 'Move me!'
  });

  // Bind the marker map property to the DistanceWidget map property
  marker.bindTo('map', this);

  // Bind the marker position property to the DistanceWidget position
  // property
  marker.bindTo('position', this);
 
  if(radius !=null && radius!=undefined){
	  _radius  = radius; 
  }
  var radiusWidget = new RadiusWidget(_radius);

  // Bind the radiusWidget map to the DistanceWidget map
  radiusWidget.bindTo('map', this);

  // Bind the radiusWidget center to the DistanceWidget position
  radiusWidget.bindTo('center', this, 'position');

  // Bind to the radiusWidgets' distance property
  this.bindTo('distance', radiusWidget);

  // Bind to the radiusWidgets' bounds property
  this.bindTo('bounds', radiusWidget);
}
DistanceWidget.prototype = new google.maps.MVCObject();


/**
 * A radius widget that add a circle to a map and centers on a marker.
 *
 * @constructor
 */
function RadiusWidget(distance) {
  var circle = new google.maps.Circle({
    strokeWeight: 2
  });
	
	google.maps.event.addListener(circle, 'click', function(){
//		if(isNew) {
//			circle.setMap(null);
//		}		
	});  
	
  this.set('distance', distance);

  // Bind the RadiusWidget bounds property to the circle bounds property.
  this.bindTo('bounds', circle);

  // Bind the circle center to the RadiusWidget center property
  circle.bindTo('center', this);

  // Bind the circle map to the RadiusWidget map
  circle.bindTo('map', this);

  // Bind the circle radius property to the RadiusWidget radius property
  circle.bindTo('radius', this);

  if(distance != 0){ //if indoors, no radius
	  // Add the sizer marker
	  this.addSizer_();
  }
 
}
RadiusWidget.prototype = new google.maps.MVCObject();


/**
 * Update the radius when the distance has changed.
 */
RadiusWidget.prototype.distance_changed = function() {
  this.set('radius', this.get('distance') * 1000);
  var radMetros = this.get('distance') * 1000; 

  if(sizer!= null){
  	 
	  if($('#fenceType').val()==1){
		  //si está el usuario haciendo menor el círculo, sacar la alerta si llega el caso
		  if((radMetros - radMetrosAnterior)<0){
			  if(radMetros<100){ 
				  sizer.setDraggable(false);
				  setTimeout(function(){ 
					  if($('#fenceType').val()==1){
							sizer.setDraggable(true);
							  
						  	$('#mnsPopup').text('Radius has to be higher than 100m.!');
			      			$('#mypopup').popup('open');
							setTimeout(function(){$('#mypopup').popup('close')},2000);
					  }  
				  },500);
			  } 
		  } 
	  }else  if($('#fenceType').val()==0){ 
		  if((radMetros - radMetrosAnterior)>0){ //si está el usuario haciendo mayor el círculo
			  if(radMetros>70){ 
				  sizer.setDraggable(false);
				  setTimeout(function(){ 
					  if($('#fenceType').val()==0){
							sizer.setDraggable(true); 
						  	$('#mnsPopup').text('Radius has to be lower than 70m.!');
			      			$('#mypopup').popup('open');
							setTimeout(function(){$('#mypopup').popup('close')},2000);
					  } 
				  
				  },500);
			  } 
		  } 
	  } 
	  
	  radMetrosAnterior = radMetros;
  }  
};


/**
 * Add the sizer marker to the map.
 *
 * @private
 */
RadiusWidget.prototype.addSizer_ = function() {
  sizer = new google.maps.Marker({
    draggable: true,
    title: 'Drag me!',
    icon: domain()+'/resources/images/slider.png'
  });

  sizer.bindTo('map', this);
  sizer.bindTo('position', this, 'sizer_position');

  var me = this;
  google.maps.event.addListener(sizer, 'drag', function() {
    // Set the circle distance (radius)
    me.setDistance();
  });
 
};


/**
 * Update the center of the circle and position the sizer back on the line.
 *
 * Position is bound to the DistanceWidget so this is expected to change when
 * the position of the distance widget is changed.
 */
RadiusWidget.prototype.center_changed = function() {
  var bounds = this.get('bounds');

  // Bounds might not always be set so check that it exists first.
  if (bounds) {
    var lng = bounds.getNorthEast().lng();

    // Put the sizer at center, right on the circle.
    var position = new google.maps.LatLng(this.get('center').lat(), lng);
    this.set('sizer_position', position);
  }
};


/**
 * Calculates the distance between two latlng points in km.
 * @see http://www.movable-type.co.uk/scripts/latlong.html
 *
 * @param {google.maps.LatLng} p1 The first lat lng point.
 * @param {google.maps.LatLng} p2 The second lat lng point.
 * @return {number} The distance between the two points in km.
 * @private
 */
RadiusWidget.prototype.distanceBetweenPoints_ = function(p1, p2) {
  if (!p1 || !p2) {
    return 0;
  }

  var R = 6371; // Radius of the Earth in km
  var dLat = (p2.lat() - p1.lat()) * Math.PI / 180;
  var dLon = (p2.lng() - p1.lng()) * Math.PI / 180;
  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(p1.lat() * Math.PI / 180) * Math.cos(p2.lat() * Math.PI / 180) *
    Math.sin(dLon / 2) * Math.sin(dLon / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var d = R * c;
  return d;
};


/**
 * Set the distance of the circle based on the position of the sizer.
 */
RadiusWidget.prototype.setDistance = function() {
  // As the sizer is being dragged, its position changes.  Because the
  // RadiusWidget's sizer_position is bound to the sizer's position, it will
  // change as well.
  var pos = this.get('sizer_position');
  var center = this.get('center');
  var distance = this.distanceBetweenPoints_(center, pos);

  // Set the distance property for any objects that are bound to it
  this.set('distance', distance);
};





//---------------------------------------------------------

// Evento de cambiar el idioma
$(document).on("change", "#select-language", function(event){
	location = this.options[this.selectedIndex].value;
	$.mobile.changePage(location);
});
$(document).on("pageshow", "[data-role=page]", function(event){
	$('#select-language option[lang=' + $('#hdnLang').val() + ']').attr('selected','selected');
	$('#select-language').selectmenu('refresh');
});


function validarEmail(email) {
	var filter_email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (!filter_email.test(email)) {
		return false;
	} else {
		return true;
	}
}