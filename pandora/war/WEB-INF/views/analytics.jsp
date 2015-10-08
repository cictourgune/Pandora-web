<!DOCTYPE html> 
<html> 	
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>	
		<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
		<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %> 
	</head> 

	<body> 
		<div id="analytics-page" data-role="page"> 
			<link rel="stylesheet" href="<%=domain%>/resources/css/bootstrap.min.css" />
			<link href='http://fonts.googleapis.com/css?family=Quicksand' rel='stylesheet' type='text/css'>
			<div data-role="header">
				<div class="row-fluid">
					<div class="span2">
						<div style="margin-top:12px;margin-left:10px;">
							<div class="ui-grid-a">
								<div class="ui-block-a" style="margin-top: -9px;width:20%">
									<a href="#leftmenu" class="ui-btn ui-shadow ui-corner-all ui-icon-bars ui-btn-icon-notext">menu</a>
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
									<a id="salir" href="<%=domain%>/j_spring_security_logout" data-role="button" class="ui-btn-active" >Logout</a> 
								</div>
							</div> 
						</div>
					</div>
				</div> 
			</div>  
			  
			<div data-role="content"  style="margin-bottom:40px;" >	
				<div data-role="popup" id="mypopup">
					<h2 id="mnsPopup"></h2>
				</div>		
				<div class="row-fluid">
					<div class="span3"><!-- Listado de fences -->
						<ul id="areas_creadas" data-role="listview" class="aplicaciones_list" data-split-icon="gear" data-inset="true" data-divider-theme="a" data-scroll="true"
						 data-intro="Select the fences for which you want to see the data" data-position="right">
							<li data-role="list-divider">Fences</li> 
							<li id="li_botones"> 
								<div align="center">  
									<a href="#" id="selectAllFences" data-role="button"  data-mini="true" data-corners="false" action='selectchk'>Select all</a>
								</div> 
							</li> 
							<c:forEach items="${listaFences}" var="fence">
								<%-- <li id="li_${fence.id}" data-fenceid="${fence.id}"> --%>
								
								<label>
							        <input id="${fence.id}"  type="checkbox" name="checkbox-0 " class="checkbox">${fence.name}
							    </label>
								
									<%-- <a id="${fence.id}" action='getFence' href="#">
										<h3>${fence.name}</h3>  
									</a> --%>
								<!-- </li> -->
							</c:forEach> 
						</ul> 
					</div> 
					<div id="panelAnaliticas" class="span6" style="padding-top:14px;height: 400px;">
		
					</div>  
					
			
					<div class="span3">  
						<ul data-role="listview"  data-inset="true" data-divider-theme="a" > 
							<li data-role="list-divider">date range</li>
							<li  data-intro="Select a date range in order to see the available analytics" data-position="left">
								<div data-role="fieldcontain">
									<label for="fecha_desde" style='padding-top: 5px;'><b>From:</b>
									</label> <input type='date' name="date_from" id="date_from"
										data-role='datebox' data-tipo='3'
										placeholder="Ejemplo: 2013-02-27"> </input>
								</div>
	
								<div data-role="fieldcontain">
									<label for="fecha_hasta" style='padding-top: 5px;'><b>To:</b>
									</label> <input type='date' name="date_until" id="date_until"
										data-role='datebox' data-tipo='3'
										placeholder="Ejemplo: 2013-02-27"> </input>
								</div>
							</li>  
						</ul>
						<ul data-role="listview"  data-inset="true" data-divider-theme="a" > 
							<li data-role="list-divider">Analytics</li>
							<li>
								 <fieldset data-role="controlgroup">
								 	<!-- <input type="radio" name="radio-choice" id="radio_shipments" value="shipments" checked="checked" class="radio" />
								    <label for="radio_shipments" id="radio_shipments"><b>Shipments</b></label> -->
								 	<input type="radio" name="radio-choice" id="radio_shipmentAreas" value="shipmentsAreas"  checked="checked" class="radio"  />
								    <label for="radio_shipmentAreas" id="radio_shipmentAreas" data-intro="Number of notifications to users within selected fences" data-position="left"><b>Notifications per Fence</b></label>
								    <input type="radio" name="radio-choice" id="radio_shipmentUsers" value="shipmentsUsers"  class="radio" />
								    <label for="radio_shipmentUsers" id="radio_shipmentUsers" data-intro="Number of notifications to all users within selected fences" data-position="left"><b>Notifications per Users</b></label>
							      	
								    <!--  <input type="radio" name="radio-choice" id="radio_muestras" value="muestras" class="radio" namevar="muestras" tipovar="tipo_muestras" data-theme="a"/>
								    <label for="radio_muestras" id="radio_muestras"><b>Resumen muestras</b></label>-->
							      </fieldset> 
							      <div>
										<a href='#' data-role='button' data-icon='search' action='get-analytics' data-theme="b">Search</a>
								  </div>
								  <div id='filter-analytics-error' data-role="content" style="text-align: center;"></div>
							</li>
						</ul> 
					</div>
					
				  
				</div>
			</div>

		
			<!-- leftmenu  -->
				<div data-role="panel" id="leftmenu" data-position="left" data-display="overlay"  data-theme="a">
					 
				     	<ul data-role="listview" style="margin-top:37px">
				     		<li data-icon="false"><a href="<%=domain%>/private/dashboard">Management</a></li>
						    <li data-icon="false"  data-theme="b"><a href="#">Analytics</a></li> 
						    <li data-icon="false"><a id='help' href="#">Help</a></li> 
						    <li data-icon="false"><a href="<%=domain%>/developers" target="_blank">Developers</a></li> 
						    <li data-icon="false" ><a  href="<%=domain%>/j_spring_security_logout">Logout</a></li> 
						</ul>
				     
				</div> 
		</div>
	 
	</body>
</html>