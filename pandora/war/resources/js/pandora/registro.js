$(document).on("pageshow", "#registro-page", function(){
	$('#registro-usuario').focus(); 
});



$(document).on("pageinit", "#registro-page", function(event){  
	$('a[data-rel="back"]').attr("data-ajax", "false").attr("href",domain()).removeAttr('data-rel');  //TODO Para que el botón del diálogo de cerrar refresque página 
});



$(document).on("tap", "#registro-submit", function(){
	var errores = registroUsuarioValidar();
	
	if(errores.length==0){
		$.mobile.loading('show');  
		registrarDeveloper();	
		
	}else{
		//incorrecto
		var mensajeError = '';
		$.each(errores, function(index, value) { 
			mensajeError = mensajeError + '<p style=\"color: red; font-weight: bold;\">*'+value+'</p>';
		 });

		$('#registro-error').html(mensajeError); 
	}

    return false;  
}); 

$(document).on("tap", "#registro-cancel-submit", function(){
	history.back(); 
	return false; 
});


$(document).on("tap", "#volver", function(){
	window.location.href=domain()+'/index'; 
});

function registrarDeveloper(){
	var formData = $("#registro-form").serialize(); 
	 $.ajax({
		 type: "POST",
		 url: domain()+"/open/developer/",
		 cache: false,
		 data: formData,
		 success: function(data) {
			 if(data == 1){ 
				$.mobile.changePage(domain()+'/validation'); 
				$.mobile.loading('hide');  
			 }
		 	},
		 error: onRegistrationError
	 });
}

function onRegistrationError(data, status)
{ 
	$.mobile.loading('hide');  
	var msgerr="";
	msgerr = '¡¡Upps!! ¡Error en el registro!';
	
	$('#registro-error').html(msgerr);
}  
 

function registroUsuarioValidar(){  
	var errores = new Array();  
	var i = 0;      
	var registroNombre = $("#registro-usuario").val();
	var email = $("#email").val();
	var msgerr="";
	
	if(registroNombre.length<5){
		msgerr = 'Username has to be longer than 5 characters';
		errores[i] = msgerr; i++;
	} else if (registroNombre.indexOf(' ') != -1) {
	
			msgerr = 'Username cannot have blank spaces';
	
		errores[i]= msgerr; i++;
	} else{//comprobar que no exista
		 $.ajax({
			 type: "GET",
			 url: domain()+"/open/developer/exists?nombre="+registroNombre+"&email="+email,
			 cache: false,
			 success: function(data) { 
					 if(data == 1){ 
						 msgerr = 'Username and/or email already existed'; 
						 errores[i]=msgerr; i++;
					 }
				}, 
			 async:   false
		 }); 
	} 
	if($("#registro-password").val().length<7){ 
		msgerr = 'Password has to be longer than 7 characters'; 
		errores[i]=msgerr; i++;
	} 
	if($("#registro-password").val()!=$("#registro-repite-password").val()){
		msgerr = 'Different passwords!';
	
		errores[i]=msgerr; i++;
	} 
//	var email = $("#registro-email").val();
//	var filter_email = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
//	if (!filter_email.test(email)) {
//		errores[i]="Email no válido"; i++;
//	}
	
	
	return errores;
}

 