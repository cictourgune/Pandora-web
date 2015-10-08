<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
 
<head> 
 
<%@ include file="/WEB-INF/views/header.jsp"%>  
 
</head>

<body> 
	 
	<div id="index-page" data-role="page">  
		
		<div data-role="content">
		  <%@include file="/WEB-INF/views/cookiesChoices.jsp" %> 
		<link rel="stylesheet"
		href="<%=domain%>/resources/landing/bootstrap.css" />
	<script src="<%=domain%>/resources/landing/bootstrap.js"></script>
	<link href="<%=domain%>/resources/landing/main.css" rel="stylesheet">


			<div class=background>

				<!-- Navbar -->
  				<div class="navbar navbar-fixed-top">
					<div class="navbar-inner">
						<div class="container">
						
						<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				            <span class="icon-bar"></span>
				            <span class="icon-bar"></span>
				            <span class="icon-bar"></span>
			          	</a>
          	
          	
							<img class="brand scroller" alt=""
								src="/pandora/resources/img/pandora.png"
								style="height:35px;margin-top:-1px">
							<div class="nav-collapse collapse">
								<ul class="nav pull-right">
									<li><a href="<%=domain%>/index">Home</a></li>
									<li><a href="#" class="scroller menu-item"
										data-section="#about">Description</a></li>
									<li><a href="<%=domain%>/developers">Developers</a></li>
									<li><a href="#" class="scroller" data-section="#contact">Contact</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>  
				<!-- end Navbar -->
				


				<!-- #intro -->
				<div class="main-container" style="margin-top:-17px">
					<div class="container-fluid" id="intro">

						<div class="row span6">
							<h1 id="texto-promo1">Push data to your mobile users based
								on context information in real time</h1>
							<p id="texto-promo2">The power of iBeacons and geofencing to give your users on the move what they want when they want it</p>
							<div class="row-fluid">
								<div class="span6">
									<a href="<%=domain%>/private/dashboard" id="entrar"
										data-role="button" data-corners="false">Login</a>
								</div>
								<div class="span6">
									<a id="registrar" class="ui-btn-active" data-role="button"
										data-corners="false">Sign up</a>
								</div>
							</div>
						</div>




					</div>
					<!-- ends Intro -->

					<div class="container-fluid" id="about">
						<div class="container">

							<ul class="thumbnails">
								<li class="span3">
									<div class="thumbnail">
										<img alt="" src="<%=domain%>/resources/img/integration.png">
										<h3>Mobile SDK</h3>
										<p>Easily integrate pandora in any mobile application:
											Android or iOS</p>
									</div>
								</li>

								<li class="span3">
									<div class="thumbnail">
										<img alt="" src="<%=domain%>/resources/img/context.png">
										<h3>Context-awareness</h3>
										<p>Personalize your mobile app based on your user
											location, date and hour range, virtual fences,...</p>
									</div>
								</li>

								<li class="span3">
									<div class="thumbnail">
										<img alt="" src="<%=domain%>/resources/img/push2.jpg"
											style="height:136px">
										<h3>Push notifications</h3>
										<p>Tourism information, customized offers, event alerts,
											traffic information,...</p>
									</div>
								</li>
								<li class="span3">
									<div class="thumbnail">
										<img alt="" src="<%=domain%>/resources/img/estimote.png"
											style="height:136px">
										<h3>iBeacon Technology</h3>
										<p>
											Indoor location powered by <a href="http://estimote.com/"
												target="_blank">Estimote iBeacons</a>
										</p>
									</div>
								</li>
							</ul>
						</div>
					</div>


					<!-- #contact -->
					<div class="container-fluid" id="contact">
						<div class="container">

							<div class="row-fluid">

								<div class="span8">
									<div id="map-canvas" style="height: 280px"></div>
								</div>

								<div class="span4">
									<h2>it3LAB</h2>

									<p>Laboratory for Innovation in Travel & Tourism
										Technologies</p>

									<address>
										CICtourGUNE<br> Paseo Mikeletegi, 71, 3ª planta<br>
										Donostia-San Sebastián, Gipuzkoa, Spain<br> <abbr
											title="Phone">Tlf:</abbr>+[34] 943 010 885
									</address>

									<address>
										Web: <a href="http://it3lab.tourgune.org"
											style="font-weight:normal" class="ui-link">http://it3lab.tourgune.org</a><br>
										Email: <a href="mailto:it3lab@tourgune.org"
											style="font-weight:normal" class="ui-link">it3lab@tourgune.org</a>
									</address>
									<p>
										<a href="<%=domain%>/privacidad"
											 style="font-weight:normal;" class="ui-link">Terms,
											conditions and privacy</a>
									</p>
								</div>
							</div>
						</div>
					</div>
					<!-- End Contact -->
				</div>
			</div>



		</div>
	</div>

</body>


</html>