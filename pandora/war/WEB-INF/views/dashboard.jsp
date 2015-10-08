<!DOCTYPE html> 
<html> 	
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>	
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
	</head> 

	<body> 
		<div id="dashboard-page" data-role="page"> 
		   <%@include file="/WEB-INF/views/cookiesChoices.jsp" %> 
			<link rel="stylesheet" href="<%=domain%>/resources/css/bootstrap.min.css" />
			<link href='http://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>
			<div data-role="header">
				<div class="row-fluid">
					<div class="span2">
						<div style="margin-top:12px;margin-left:10px;">
							<div class="ui-grid-a">
								<div class="ui-block-a" style="margin-top: -9px;width:20%">
									<a href="#leftmenu" class="ui-btn ui-shadow ui-corner-all ui-icon-bars ui-btn-icon-notext"
									data-intro="Menu button" data-position="bottom">menu</a>
								</div>
								<div class="ui-block-b" style="width:80%">
									 <img alt="" src="<%=domain%>/resources/img/pandora.png" style="height:35px;margin-top:-1px">
								 	<%-- <a href="<%=domain%>"><font size="5" color="black"  style="margin-right:15px;">pandora</font></a>    --%>
								</div> 
							</div> 
						</div>
					</div>
					<div class="offset7 span3">
						<div class="ui-grid-a">
							<div class="ui-block-a" style="margin-top: 15px;">
								<span>Welcome <sec:authentication property="principal.username"/></span>
							</div>
							<div class="ui-block-b">
								<div data-role="controlgroup" data-type="horizontal" >
									<a id="salir" href="<%=domain%>/j_spring_security_logout"data-role="button" class="ui-btn-active" >Logout</a> 
								</div>
							</div> 
						</div>
					</div>
					
					<%-- <div class="span5 offset6">
						<div class="ui-grid-a">
							<div class="ui-block-a" style="margin-top: 15px;">
								<span style="float: right;">Welcome <sec:authentication property="principal.username"/></span>
							</div>
							<div class="ui-block-b">
								<div data-role="controlgroup" data-type="horizontal" style="float: right">
									<a id="salir" href="<%=domain%>/j_spring_security_logout"data-role="button" data-theme="b">Logout</a> 
								</div>
							</div> 
						</div>
					</div> --%>
				</div> 
			</div>  
			  
			<div data-role="content"  style="margin-bottom:40px;" >	
				<div data-role="popup" id="mypopup">
					<h2 id="mnsPopup"></h2>
				</div>		
				<div class="row-fluid">
					<div class="span3"><!-- Listado de fences -->
						<ul id="areas_creadas" data-role="listview" class="aplicaciones_list" data-split-icon="gear" data-inset="true" data-divider-theme="a" data-scroll="true"
						 style="overflow:auto; max-height:750px;" >
							<li data-role="list-divider">Fences</li> 
							<li id="li_botones"> 
								<div align="center">  
									<a href="#" id="crearFence" data-role="button"data-icon="plus" data-iconpos="left"  data-mini="true" data-corners="false" 
									data-intro="You can create circular areas where users can be detected by Pandora" data-position="right">Create</a>
								</div> 
							</li> 
							<c:forEach items="${listaFences}" var="fence">
								<li id="li_${fence.id}" data-fenceid="${fence.id}">
									<a id="${fence.id}" action='getFence' href="#">
										<h3>${fence.name}</h3>  
									</a>
								</li>
							</c:forEach> 
						</ul> 
					</div> 
					<div id="panelEdicion" class="span6" style="padding-top:14px"><!-- Detalle de fence --> 
			 

						<div class="ui-bar ui-bar-a"  style="padding:10px; min-height:540px" >
							
							
							<div class="ui-grid-a">
								<div class="ui-block-a" style="width:80%;padding-left:3px">
									 	<input id="target" type="text" placeholder="City, street, restaurant, hotel,..." data-mini="true" class="controls">
								</div>
								<div class="ui-block-b" style="width:20%"> 
									 <a id="pinmap" data-role="button" data-theme="b" data-corners="false"  data-mini="true">Pin map</a>
								</div>
							</div> 
							
							<div id="map_canvas" style="height:350px"></div> 
							<div style="margin-top:10px">
							
								<div class="ui-grid-a">
									<div class="ui-block-a" style="padding-left:3px; padding-right:3px">
										 	<label for="name" style="padding-right:20px"><strong><font size="3">Name of fence</font></strong></label>
											<input type="text" name="name" id="nombreFence" data-mini="true" style="width: 100% !important"/>
									</div>
									
									<div class="ui-block-b"> 
										<label for="name" style="padding-right:20px"><strong><font size="3">Type of fence</font></strong></label>
										<div  style="margin-top:-5px; padding-right:5px">
											<select name="fenceType" id="fenceType" data-role="slider" data-mini="true">
											        <option value="0" id="indoors" selected>indoors</option>
											        <option value="1" id="outdoors">outdoors</option>
											</select> 
										</div>
									</div>   
								</div>  
							</div> 
							
							<div id="ibeaconid"  style="padding-left:3px; padding-right:3px">
								<label for="ibeacon" style="padding-right:50px"><strong><font size="3">iBeacon ID (UUID, major and minor)</font></strong></label>
								<input type="text" name="ibeacon" id="idIbeacon" data-mini="true"
								 data-intro="If you are creating an indoor area, you have to specify the UUID, major and minor values of the beacon that is representing such area" data-position="left" />
							</div> 
						</div>
					 
						<div class="ui-body ui-body-a"  style="padding:10px" data-role="collapsible" data-content-theme="false"> 
							<h4  data-intro="Here you can find the available configuration parameters in order to push data to your mobile users" data-position="right">Configuration</h4>
							<div id="rule" > 
							
								<div class="ui-grid-c">
									<div class="ui-block-a" style="width:155px"> 
										<select name="period_fence" id="period_fence" data-mini="true" data-role="slider" data-theme="a" >
										   <option value="1">The first time</option>
										   <option value="0">Every time</option> 
										</select>
										
									</div>
									<div class="ui-block-b" style="margin-left:20px;width:100px;">
										<p>someone</p>
									</div>
									<div class="ui-block-c">
										<select name="status_fence" id="status_fence" data-mini="true" data-role="slider">
										   <option value="1">arrives at</option>
										   <option value="0">leaves</option> 
										</select>
									</div>
									<div class="ui-block-d" style="margin-left:20px;">
										<p>this fence</p>
									</div>
								</div>  
								
								<div class="ui-grid-c">
									<div class="ui-block-a" style="width:160px">
										<p>date between</p>
									</div>
									<div class="ui-block-b" style="width:150px">
										 <input type='date' name="fecha-desde" id="fecha-desde"  data-mini="true" 
												data-role='datebox' placeholder="Ejemplo: 2013-02-27">  
									</div>
									<div class="ui-block-c" align="center" style="width:90px">
										<p>and</p>
									</div>
									<div class="ui-block-d" style="width:140px">
										 <input type='date' name="fecha-hasta" id="fecha-hasta"  data-mini="true" 
												data-role='datebox' placeholder="Ejemplo: 2013-02-27">  
									</div>
								</div>  
								
								<div class="ui-grid-a">
									<div class="ui-block-a" style="width:160px">
										<p>hour between</p>
									</div>
									<div class="ui-block-b"> 
										<div id="hourSlider" data-role="rangeslider"> 
									        <input type="range" name="hora-desde" id="hora-desde" min="0" max="23" step="1" value="0"> 
									        <input type="range" name="hora-hasta" id="hora-hasta" min="1" max="24" step="1" value="24">
									    </div>
									</div>
								</div>
								
								<div class="ui-grid-a">
									<div class="ui-block-a" style="width:160px">
										<p>send text, url, json,...</p>
									</div>
									<div class="ui-block-b" style="width:400px">
										 <input type="text" name="data" id="data" value="" data-mini="true" />
									</div> 
								</div> 
								<div class="ui-grid-a">
									<div class="ui-block-a" style="width:160px">
										<p>to destination</p>
									</div>
									
									<div class="ui-block-b"> 
										<select name="destinatario" id="destinatario" data-role="slider" data-mini="true">
										        <option value="0" id="endpoint" >endpoint</option>
										        <option value="1" id="movil" selected>mobile</option>
										</select> 
									</div>   
								</div> 
								  
							</div>
							
						</div> 
						<div id="error-fence"></div> 
						<div id="botones" class="ui-bar"  data-theme="c"  style="margin-top:-10px; margin-left:-18px; margin-right:-18px">
						
							<div class="ui-grid-a">
								<div class="ui-block-a">
									 	<a href="#" id="guardarFence" data-role="button" class="ui-btn-active" data-corners="false">Save</a>  
								</div>
								<div class="ui-block-b"> 
									 <a id="eliminarFence" href="#popupDeleteFence" data-rel="popup" data-position-to="window"  data-role="button" data-theme="c" data-corners="false">Delete</a>
								</div>
							</div>
							
							<div class="ui-grid-a">
								<div class="ui-block-a">
									 	<a href="#" id="insertarFence" data-role="button" class="ui-btn-active" data-corners="false">Create</a>
								</div>
								<div class="ui-block-b"> 
									 <a href="#" id="cancelarInsertarFence" data-role="button" data-theme="c" data-corners="false">Cancel</a>
								</div>
							</div>
						
						
						
							
						
							
						</div>  
					</div>  
					
			
					<div class="span3">  
						<ul id="userInfo" data-role="listview" data-split-icon="gear" data-inset="true" data-divider-theme="a" data-scroll="true">
							<li data-role="list-divider">Information</li> 
							<li>
								<label for="name"><strong>developer key</strong></label>
								<input type="text"id="developerKey" data-mini="true" value="${developer.key}" style="text-align:center;font-size:13.5px;" readonly
								data-intro="This key is used to configure your mobile SDK" data-position="left">
							</li>
							 <li>
								<label for="name"><strong>endpoint</strong></label>
 							    <input type="text" id="endpointUrl" data-mini="true" value="${developer.endpoint}" style="font-size:13.5px;margin-bottom:10px;background-color:white"
 							    data-intro="When a rule is triggered, the associated data can be sent to an endpoint. This has to be reachable by a GET request" data-position="left"> 
								 
								<div id="error-endpoint"></div> 
								<div align="center">  
									<a href="#" id="guardarEndpoint" data-role="button" data-icon="" data-iconpos=""  data-mini="true" data-corners="false">Save</a>
								</div>
							</li> 	
						</ul> 
						<ul id="certInfo" data-role="listview" data-split-icon="gear" data-inset="true" data-divider-theme="a" data-scroll="true">
							<li data-role="list-divider">iOS App Certificate</li> 
							<li>
								<label for="name"><strong>URL</strong></label>
								<input type="text"id="certURL" data-mini="true" style="font-size:13.5px;margin-bottom:10px;background-color:white"
								 data-intro="If you are developing an iOS app, we need a certificate in order to send push data to your mobile users. You have to specify the URL where the certificate is located" data-position="left">
								<div id="error-certURL"></div> 
								<div align="center">  
									<a href="#" id="guardarCertURL" data-role="button" data-icon="" data-iconpos=""  data-mini="true" data-corners="false">Save</a>
								</div>
							</li>
						</ul> 
					</div> 
				  
				</div>
			</div>
 			<%-- <div data-role="footer" align="center" data-position="fixed" data-tap-toggle="false"  data-theme="c" > 
				<a href="http://it3lab.tourgune.org" target="_blank" data-role="none" ><img src="<%=domain%>/resources/images/it3lab_RGB_transparente_ajustada.png" width="124" height="35"></a> -
				<a href="http://www.tourgune.org" target="_blank" data-role="none" ><img src="<%=domain%>/resources/images/cictourgune_rgb_peque.png" width="199" height="35" style="margin-bottom:12px;"></a>
			</div>   --%>
		
			<!-- leftmenu  -->
				<div data-role="panel" id="leftmenu" data-position="left" data-display="overlay" data-theme="a">
				     	<ul data-role="listview" style="margin-top:37px">
				     		<li data-icon="false"  data-theme="b"><a href="#">Management</a></li>
						    <li data-icon="false"><a href="<%=domain%>/private/analytics">Analytics</a></li> 
						    <li data-icon="false"><a id='help' href="#">Help</a></li> 
						    <li data-icon="false"><a href="<%=domain%>/developers" target="_blank">Developers</a></li> 
						    <li data-icon="false"><a href="<%=domain%>/j_spring_security_logout">Logout</a></li> 
						</ul>
				     
				</div> 
				
				
				
		<!-- Dialogo de borrado de fence -->
		<div data-role="popup" id="popupDeleteFence"  data-overlay-theme="b"  data-dismissible="false" >
			<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a>
			<div data-role="header">
				<h1>Delete fence</h1>
			</div>
			<div data-role="content">
				<div style="text-align: center">
					<h2>You are going to delete the selected fence. Are you sure?</h2>
				</div>
				<div data-role="controlgroup" data-type="horizontal" align="center">
				<a id="confirmacion-eliminacion-ok" data-role="button" class="ui-btn-active">Yes</a>       
				<a id="confirmacion-eliminacion-no" data-role="button" data-rel="back">No</a> 
				</div>
			</div>
		
		
		
<!-- 			<a href="#" data-rel="back" class="ui-btn ui-corner-all ui-shadow ui-btn-a ui-icon-delete ui-btn-icon-notext ui-btn-right">Close</a>
		    <div data-role="header" data-theme="a">
		    <h1>Eliminar fence</h1>
		    </div>
		    <div role="main" class="ui-content"> 
		        
			    <p>¿Quieres continuar?</p>  
			    
				<div class="row margin_top_35"> 
					<div id="nombreapp" class="col6"> 
						<a  data-role="button" data-rel="back" data-corners="false"><spring:message code="no"/></a>  
					</div>
					<div class="col6">
						   <a id="confirmacion-eliminacion-app-ok" data-role="button" data-rel="back" data-corners="false" class="btn-blue no-shadow"><spring:message code="yes"/></a>       
					</div>		
					
				</div>  
		    </div> -->
		</div>
				
				
				
				
		</div>
		
		
	
	 
	</body>
</html>