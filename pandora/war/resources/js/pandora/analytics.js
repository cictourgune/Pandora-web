var query='';
$(document).on("pageshow", "#analytics-page", function(){
	drawChartFake();
});

$(document).on('tap','[action="selectchk"]',	function(event) {
	var x=document.getElementById("selectAllFences");
	if (x.innerHTML=="Select all"){
		document.getElementById("selectAllFences").innerHTML="Deselect all";
		var checkboxes = new Array(); 
		checkboxes = document.getElementsByClassName('checkbox');
		for (var i=0; i<checkboxes.length; i++)  {
		  if (checkboxes[i].type == 'checkbox')   {
		    $(checkboxes[i]).prop("checked", true).checkboxradio("refresh");
		    
		  }
		}
	}else{
		document.getElementById("selectAllFences").innerHTML="Select all";
		var checkboxes = new Array(); 
		checkboxes = document.getElementsByClassName('checkbox');
		for (var i=0; i<checkboxes.length; i++)  {
		  if (checkboxes[i].type == 'checkbox')   {
		    $(checkboxes[i]).prop("checked", false).checkboxradio("refresh");
		    
		  }
		}
	}
	  
});


$(document).on('tap','[action="get-analytics"]',	function(event) {

	//Comprobar que algun area este seleccionado
	var checkboxes = new Array(); 
	checkboxes = document.getElementsByClassName('checkbox');
	var chkchecked=false; 
	for (var i=0; i<checkboxes.length; i++)  {
		if ($(checkboxes[i]).is(":checked")){
			chkchecked=true;
		}
	}
	//Si no hay ningun área seleccionado mostrar error
	if (chkchecked==false){
		$('#filter-analytics-error').html('<font color="#FF0000">You must select minimum one area</font>');
	}else{
		var fecha_desde = $("#date_from").val();
		var fecha_hasta = $("#date_until").val();
		if (fecha_desde == "" || fecha_hasta == "") {
			if (fecha_desde == "" && fecha_hasta == ""){
				var today = new Date();
				var lastYear= new Date();
				var dd = today.getDate();
				var mm = today.getMonth()+1; //January is 0!
				var yyyy = today.getFullYear();

				if(dd<10) {
				    dd='0'+dd;
				} 

				if(mm<10) {
				    mm='0'+mm;
				} 

				today = yyyy+'-'+mm+'-'+dd;
				yyyy=yyyy-1;
				lastYear=yyyy+'-'+mm+'-'+dd;
				
				fecha_desde=lastYear;
				fecha_hasta=today;
			}else{
				//si la fecha desde es nulo y fecha hasta contiene fecha
				if (fecha_desde == ""){
					var today = new Date();
					var lastYear= new Date();
					var dd = today.getDate();
					var mm = today.getMonth()+1; //January is 0!
					var yyyy = today.getFullYear();

					if(dd<10) {
					    dd='0'+dd;
					} 

					if(mm<10) {
					    mm='0'+mm;
					} 

					today = yyyy+'-'+mm+'-'+dd;
					yyyy=yyyy-1;
					lastYear=yyyy+'-'+mm+'-'+dd;
					
					fecha_desde=lastYear;
				}else{// si fecha desde contiene fecha y fecha hasta es nulo
					var today = new Date();
					var dd = today.getDate();
					var mm = today.getMonth()+1; //January is 0!
					var yyyy = today.getFullYear();

					if(dd<10) {
					    dd='0'+dd;
					} 

					if(mm<10) {
					    mm='0'+mm;
					} 

					today = yyyy+'-'+mm+'-'+dd;
					fecha_hasta=today;
				}
			}
			
		}
		//Comprobar que las fechas tengan buen formato
		var f_desde=esFecha(fecha_desde);
		var f_hasta=esFecha(fecha_hasta);
		
		if (f_desde==true && f_hasta==true){

			
			var desde = Date.parse(fecha_desde);
			var hasta = Date.parse(fecha_hasta);
			if (desde > hasta) {
				$('#filter-analytics-error').html('<font color="#FF0000">Date from must be prior</font>');
			}
			else {
				$('#filter-analytics-error').html('');
				query='';
				//Una query con todas las áreas seleccionadas
				$(".checkbox").each(function(index){
					if ($(this).is(":checked")){
						
						query=query+$(this).attr('id')+",";	
						
					}
				});
				//Quitar la última coma
				query=query.substring(0,query.length-1);
				
				if ($(radio_shipmentAreas).is(":checked")){
					$.ajax({type : "GET",
						url : domain()+ "/api/envios/enviosAreasFecha?query="+query + 
															"&date_from='" + fecha_desde
														 + "'&date_until='"+ fecha_hasta+"'",
							  contentType : "application/json",
							  success : function(data) 
							  {
								  drawChartAreas(data);
								 
							  }});
				}else{
					if ($(radio_shipmentUsers).is(":checked")){
						$.ajax({type : "GET",
							url : domain()+ "/api/envios/enviosUsuariosFecha?query="+query + 
																"&date_from='" + fecha_desde
															 + "'&date_until='"+ fecha_hasta+"'",
								  contentType : "application/json",
								  success : function(data) 
								  {
									  drawChartUsers(data);
									 
								  }});
					}
				}
				

			}
		}else{
			$('#filter-analytics-error').html('<font color="#FF0000">Invalid date</font>');
		}
	}
	
});

function esFecha (strValue)
{

	var valido=false;
	var re=/^[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]$/;
	//si alguna de las fechas es nula siginifica que deben de tener una fecha
	if(strValue=='')
	{
		valido=true;
		alert(valido);
	}
	else{
	    var arrayDate = strValue.split("-");
	    var arrayLookup = { '01' : 31,'03' : 31,
			    		    '04' : 30,'05' : 31,
			    		    '06' : 30,'07' : 31,
			    		    '08' : 31,'09' : 30,
			    		    '10' : 31,'11' : 30,'12' : 31
    		    		  };

	    var intDay = parseInt(arrayDate[2],10);
	    var intMonth = parseInt(arrayDate[1],10);
	    var intYear = parseInt(arrayDate[0],10);
	    
	    //check if month value and day value agree

	    if (arrayLookup[arrayDate[1]] != null) {
	      if (intDay <= arrayLookup[arrayDate[1]] && intDay != 0
	        && intYear > 1975 && intYear < 2050)
	    	  valido=true;     //found in lookup table, good date
	    }

	    if (intMonth == 2) {
	      var intYear = parseInt(arrayDate[0]);

	      if (intDay > 0 && intDay < 29) {
	    	  valido=true;
	      }
	      else if (intDay == 29) {
	        if ((intYear % 4 == 0) && (intYear % 100 != 0) ||
	            (intYear % 400 == 0)) {
	          // year div by 4 and ((not div by 100) or div by 400) ->ok
	        	valido=true;
	        }
	      }
	    }
	}
    return valido;
}



function drawChartAreas (listaAreas)
{
	var data;
	var dataFecha = new google.visualization.DataTable();
	dataFecha.addColumn('date', 'Date');

	var cantidadMedia=0;
	var arrayfences=[];
	var arrayfechas=[];


	
	$.each(listaAreas, function(indice, envios) 
	{
		
		//alert("envios.fenceid "+envios.fencename);
		var existefence=$.inArray(envios.fencename, arrayfences);
		//alert("existefence "+existefence);
		if (existefence==-1){
			//alert("barruan");
			arrayfences.push(envios.fencename);
			dataFecha.addColumn('number',envios.fencename);
			
		}
	});
				
	dataFecha.addColumn('number', 'Average');
	$.each(listaAreas, function(indice, envios) 
	{
		cantidadMedia=cantidadMedia + envios.cantidad;
	});
			
	var media = cantidadMedia/listaAreas.length;
	
	var ind=0;
	var fechaant=listaAreas[0].fecha;
	var indfence=1;
	var cantidadfences=1+(arrayfences.length);

	arrayfencescantidad=new Array (listaAreas.length);
	arrayfencescantidad [0]=new Array (cantidadfences);
	arrayfencescantidad[0][0]=new Date(fechaant);
	// para pasar de 
	//2014-06-02 234 10
	//2014-06-02 240 20
	//a
	//2014-06-02 234 10 240 20
	
	$.each(listaAreas, function(indice, envios) {
		
		if (envios.fecha!=fechaant){
			ind=ind+1;
			fechaant=envios.fecha;
			indfence=1;
			arrayfencescantidad [ind]=new Array (cantidadfences);
			arrayfencescantidad[ind][0]=new Date(fechaant);			
		}	
		arrayfencescantidad[ind][indfence]=envios.cantidad;
		//alert("arrayfencescantidad["+ind+"]["+indfence+"] " + arrayfencescantidad[ind][indfence]);
		indfence=indfence+1;
		
	});

	ind=ind +1;
	//para añadir la media a todas las tuplas
	for (i=0;i<ind;i++){
		arrayfencescantidad[i][cantidadfences]=media;
	}
	
	for (i=0;i<ind;i++){
		dataFecha.addRow(arrayfencescantidad[i]);
	}
	
	var options = {title : 'Notifications per Areas'};
	var chart;
	chart = new google.visualization.AnnotatedTimeLine(document.getElementById('panelAnaliticas'));
	
	chart.draw(dataFecha, options);

}
function drawChartUsers (listaUsers)
{
	var data;
	var dataFecha = new google.visualization.DataTable();
	dataFecha.addColumn('date', 'Date');
	dataFecha.addColumn('number', 'Users');
	dataFecha.addColumn('number', 'Shipments');

	for (i=0;i<listaUsers.length;i++){
		dataFecha.addRow([new Date(listaUsers[i].fecha),listaUsers[i].diffusers,listaUsers[i].cantidad]);
	}
	
	var options = {title : 'Notifications per users'};
	var chart;
	chart = new google.visualization.AnnotatedTimeLine(document.getElementById('panelAnaliticas'));
	
	chart.draw(dataFecha, options);
}

function drawChartFake ()
{
	var data;
	var dataFecha = new google.visualization.DataTable();
	dataFecha.addColumn('date', 'Date');
	dataFecha.addColumn('number', 'Shipments');

	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();

	if(dd<10) {
	    dd='0'+dd;
	} 

	if(mm<10) {
	    mm='0'+mm;
	} 

	today = mm+'/'+dd+'/'+yyyy;
	dataFecha.addRow([new Date(today),0]);
	
	var options = {title : 'Notifications'};
	var chart;
	chart = new google.visualization.AnnotatedTimeLine(document.getElementById('panelAnaliticas'));
	
	chart.draw(dataFecha, options);
}
 