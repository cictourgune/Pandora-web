<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE HTML>
<html>
 
<head> 
 
<%@ include file="/WEB-INF/views/header.jsp"%>  
 
</head> 
 
<body>
  
    <link rel="stylesheet" href="<%=domain%>/resources/landing/bootstrap.css" />  
    <script src="<%=domain%>/resources/landing/bootstrap.js"></script>  
 	<link href="<%=domain%>/resources/landing/main.css" rel="stylesheet">  
 	
     <%@include file="/WEB-INF/views/cookiesChoices.jsp" %> 
 
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
          	<a id="nombreweb" class="brand scroller" href="#">pandora</a>
		  	<div class="nav-collapse collapse">
                 <ul class="nav pull-right">
                    <li><a href="<%=domain%>/index">Home</a></li>
                    <li><a href="#" class="scroller menu-item" data-section="#about">Description</a></li>
                     <li><a href="<%=domain%>/developers">Developers</a></li> 
                    <li><a href="<%=domain%>/index" class="scroller" data-section="#contact">Contact</a></li>  
                </ul>
	        </div>
        </div>
      </div>
    </div>  
    <!-- end Navbar -->
    
    
    <!-- #intro -->
	<div class="main-container" style="margin-top:-17px">
		 
    
     
	<div class="container-fluid" id="about">
			
		<div class="container" style="margin-top: 150px">
			 
			<ul class="thumbnails">
			  <li class="span6">
			    <div class="thumbnail">
			      <img alt="" src="<%=domain%>/resources/img/android-logo.png">
			      <h3>Android SDK (4.x)</h3>
			     	<div align="left" style="padding-left: 97px;">
			     	 	<ul>
						  <li><a href="https://www.dropbox.com/s/jfj3hwy2npylo0z/pandora-android-sdk-v1.1.zip?dl=0" target="_blank"> Download v1.1 (Android app sample included) </a></li>
						  <li><a href="#" id="startedlinkAndroid">Getting started</a></li>
						  <li><a href="#" id="pushlink">Sending push notifications to an endpoint</a></li>
						   <li><a href="#" id="apilink">Using the pandora API</a></li>
						</ul>
			     	</div> 
			    </div>
			  </li>
			
			  <li class="span6">
			    <div class="thumbnail">
			      <img alt="" src="<%=domain%>/resources/img/ios-logo.jpg">
			        <h3>iOS SDK</h3>
			      <div align="left" style="padding-left: 97px;">
			     	 	<ul>
						  <li><a href="https://www.dropbox.com/s/t1wdy1j1vt2gzwz/pandora-iOS-sdk-v1.zip?dl=0" target="_blank"> Download v1 (iOS app sample included) </a></li>
						  <li><a href="#" id="startedlinkiOS">Getting started</a></li>
						  <li><a href="#" id="apnsiOS">Apple push Notification Services</a></li>
						   <li><a href="#" id="pushlink">Sending push notifications to an endpoint</a></li>
						    <li><a href="#" id="apilink">Using the pandora API</a></li>
						</ul>
			     	</div> 
		         </div>
			  </li>
			</ul> 

			<div style="font-size:15px; ">
			
				
				<div data-role="content" id="startedsectionAndroid" style="padding-top:100px"> 
					<h2>Getting started with pandora for Android</h2> 
					<br>
					<ol>
						<li>
							<div>Sign up or login to pandora.</div>
						</li>
						<li>
							<div>
								<p>Create your fences, configuring the conditions (time range, date range,...) to push data to the mobile device or endpoint. You can set the iBeacon ID if the fence is indoors.</p> 
								<img alt="" src="<%=domain%>/resources/img/ins1.jpg" width="75%">
							</div>	
						</li>
						<li>
							<div>Download the <a href="https://www.dropbox.com/s/jfj3hwy2npylo0z/pandora-android-sdk-v1.1.zip?dl=0" target="_blank">pandora SDK.</a></div>
						</li>
						<li>
							<div>
							<p>Import the sample project included in the pandora SDK to your Eclipse workspace. Use File - New - Android project from existing code.</p>
							<p>The needed libraries are already included in the sample project (gcm.jar, estimote-sdk-preview.jar, otto-1.3.4.jar and pandora-android-sdk-1.0.jar)</p></div>
							 
						</li>
						<li>
							<div>Setup google play services in order to be used by the sample project (More info: <a href="http://developer.android.com/google/play-services/setup.html#Install" target="_blank">http://developer.android.com/google/play-services/setup.html#Install</a>)</div>
						</li>
						<li>
							<div>
							Configure the Android sample project:
							<ul> 
								<li>
									<div>
										Configure pandora programmatically. If you activate the iBeacon scanning, the user must start the bluetooth scanning in the mobile device. 
									</div>
									<img alt="" src="<%=domain%>/resources/img/code1.jpg">
									<img alt="" src="<%=domain%>/resources/img/code2.jpg">
								</li>
								
								<li>
									<div>Extend GCMProcessor and implement 'processMessage' method to manage received data</div>
									<img alt="" src="<%=domain%>/resources/img/code3.jpg">
								</li>
									 
									
								<li>Configure the AndroidManifest.xml document</li>
								<ul>
									<li>
										<div>Add the needed permissions configuring them with your base package</div>
										<img alt="" src="<%=domain%>/resources/img/xml1.jpg">
									</li>
									<li>
										<div>The activity where pandora is created has to include  
										     	android:configChanges="orientation" in the configuration in order to 
										     	avoid a duplicate instances of pandora </div>
										<img alt="" src="<%=domain%>/resources/img/xml2.jpg">
									</li>
									<li>
										<div>Add the pandora configurations </div>
										<img alt="" src="<%=domain%>/resources/img/xml4.jpg">
									</li>
									<li>
										<div>Configure your pandora developer key</div>
										<img alt="" src="<%=domain%>/resources/img/xml3.jpg">
									</li>
								</ul>
	 
							</ul> 
							</div>
						</li>
						<li>
							<div>Run your app and start receiving push data!!</div>
						</li>
						<li>
							<div>Modify the sample project to use pandora with your own apps!</div>
						</li>
					</ol> 
				</div>
				 
				<div data-role="content" id="pushsection" style="padding-top:100px">
					<h3>Sending push notifications to an endpoint</h3> 
					<div>
					<ol>
						<li>
							<div>
								<p>Configure pandora with your endpoint URL and set the destination of your data accordingly</p> 
								<img alt="" src="<%=domain%>/resources/img/config.jpg"> 
							</div> 
						</li>
						<li>
							<div>
								<p>pandora will send data to your endpoint using a POST request using JSON data in the payload</p>
							</div>
						</li>
						<li>
							<div>
								<p>Then, you can do whatever you want: store data in a database, send a push message to your mobile users,...</p>
								<p>You can also send push data to a specific mobile user using the pandora API, 
									making a POST request to the following URL with any data in the payload:</p> 
								<p>http://it3labprojects.tourgune.org/gcmpandora/api/v1/device/RECEIVED_USER_GCM_ID/message?key=YOUR_DEVELOPER_KEY </p>
							</div>
						</li>
					</ol> 
					
					</div>
				</div>
				  
				<div data-role="content" id="apisection" style="padding-top:100px">
					<h3>Using the pandora API</h3>
					<p>You can also use the pandora API to manage your fences: create, delete, update, get,...</p>
					
					<ol>
						<li>
							<div>
								<p><b>Get all your fences</b></p>
								<p>GET http://it3labprojects.tourgune.org/pandora/open/sdk/fences?key=<b>YOUR_DEVELOPER_KEY</b></p>							
								<p>Response example</p>
								<img alt="" src="<%=domain%>/resources/img/getallfences.jpg"> 
							</div>
						</li>
						<li>
							<div>
								<p><b>Get a fence by ID</b></p>
								<p>GET http://it3labprojects.tourgune.org/pandora/open/sdk/fences/<b>ID</b>?key=<b>YOUR_DEVELOPER_KEY</b></p>
								<p>Response example</p>
								<img alt="" src="<%=domain%>/resources/img/getfencebyid.jpg"> 
							</div>
						</li>
						<li>
							<div>
								<p><b>Delete a fence by ID</b></p>
								<p>DELETE http://it3labprojects.tourgune.org/pandora/open/sdk/fences/<b>ID</b>?key=<b>YOUR_DEVELOPER_KEY</b></p> 
							</div>
						</li>
						<li>
							<div>
								<p><b>Create a new fence</b></p>
								<p>POST http://it3labprojects.tourgune.org/pandora/open/sdk/fences?key=<b>YOUR_DEVELOPER_KEY</b></p> 
								<p>Payload example</p>
								<ul>
								   
									<li>status_fence: 1 (arrives at) or 0 (leaves)</li>
									<li>period_fence: 1 (the first time) or 0 (every time)</li>
									<li>destination: 1 (mobile) or 0 (endpoint)</li>
								</ul>
								<img alt="" src="<%=domain%>/resources/img/createfence.jpg"> 
							</div>
						</li>
						<li>
							<div>
								<p><b>Update fence</b></p>
								<p>PUT http://it3labprojects.tourgune.org/pandora/open/sdk/fences?key=<b>YOUR_DEVELOPER_KEY</b></p> 
								<p>Payload example</p> 
								<img alt="" src="<%=domain%>/resources/img/updatefence.jpg"> 
							</div>
						</li>
					
					
					</ol>
				</div>
				 
				<div data-role="content" id="startedsectioniOS" style="padding-top:100px"> 
					<h2>Getting started with pandora for iOS</h2> 
					<br>
					<ol>
						<li>
							<div>Sign up or login to pandora.</div>
						</li>
						<li>
							<div>
								Create your fences, configuring the conditions to push data to the mobile device or endpoint.
								<img alt="" src="<%=domain%>/resources/img/ins1.jpg" width="75%">
							</div>	
						</li>
						<li>
							<div>Download the <a href="#">pandora SDK</a></div>
						</li>
						<li>
							<div>
								<p>Drag and drop pandoraSDK and EstimoteSDK folders, including *.a and *.h files, to your project.</p>
							</div>
							<img alt="" src="<%=domain%>/resources/img/pandoraEstimoteSDKFolders.png" >
						</li>
						<li>
							<div><p>Add the #import lines to any files that need to reference the pandoraSDK Class.</p></div>
						</li>
						<li>
							<div><p>Add the following native iOS frameworks to your project settings: CoreBluetooth.framework, CoreLocation.framework and SystemConfiguration.framework</p></div>
						</li>
						<li>
							<div><p>Go to the "Build Settings" section of project settings and search for "Header Search Paths". Add a line containing "$(SRCROOT)/../EstimoteSDK/Headers</p></div>
						</li>
						<li>
							<div><p>Create and intialize the instance to pandoraSDK Class and call to start method. If you want to activate the iBeacon scanning, you must change the value of the second parameter to true. For that, the user must start the bluetooth scanning in the mobile device.</p></div>
							<img alt="" src="<%=domain%>/resources/img/startPandoraiOS.png">
						</li>
						<li>
							<div>
								<p>Set up your App Delegate to receive notifications and get device token.</p>
								<img alt="" src="<%=domain%>/resources/img/appDelegate.png">
							</div>
						</li>
						<li>
							<div>
								<p>Add the following key to your Info.plist: NSLocationAlwaysUsageDescription. This key take a string which is a description of why you need location services.</p>
							</div>
						</li>
						<li>
							<div>You need to create APNS certificate to start receiving push data. Go to Apple Push Notification Services in iOS Section.</div>
						</li>
					</ol> 
				</div>
				<div data-role="content" id="apnssectioniOS" style="padding-top:100px"> 
					<h2>Apple Push Notification Services in iOS</h2> 
					<br>
					<ol>
						<li>
							<div>The steps have been simplied, if you want more information about how it works here is the oficial information: <a href="https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/Chapters/ApplePushService.html" target="_blank">https://developer.apple.com/library/ios/documentation/NetworkingInternet/Conceptual/RemoteNotificationsPG/Chapters/ApplePushService.html</a>.</div>
						</li>
						<br><br>
						<h4><ins>Creating and configuring App ID</ins></h4>
						<br><br>
						<li>
							<div>
								<p>Open KeyChain Access (Applications/Utilities) and choose "Certificate Assistant" option and then "Request a Certificate From a Certificate Authority" </p>
								<img alt="" src="<%=domain%>/resources/img/keychain.jpg" >
								<br><br>
							</div>
						</li>
						<li>
							<div>
								<p>The following window will appear, enter the name of the project for Common Name, choose saved to disk and press continue. Save the file as YOUR_PROJECT_NAME.certSigningRequest. It is recommended to create a folder to store all the files.</p>
								<img alt="" src="<%=domain%>/resources/img/certificateAssistant.png" width="40%">
								<br>
							</div>	
						</li>
						
						<li>
							<div>
								<p>Go to iOS Dev Center web page <a href="https://developer.apple.com/devcenter/ios/index.action" target="_blank">https://developer.apple.com/devcenter/ios/index.action</a> </p>
								<br>
							</div>
						</li>
						<li>
							<div> 
								<p>In the right menu, choose Certificates, Identifiers and Profiles option.</p>
								<img alt="" src="<%=domain%>/resources/img/iOSDevCenter.png" width="75%">
								<br>
							</div>
						</li>
						<li>
							<div> 
								<p>Select Certificates option in the iOS Apps section.</p>
								<img alt="" src="<%=domain%>/resources/img/certificates.png" width="75%">
								<br><br>
							</div>
						</li>
						<li>
							<div> 
								<p>Inside Identifiers select App ID and create a new one (+ button).</p>
								<img alt="" src="<%=domain%>/resources/img/certificates2.png" width="75%">
							</div>
							<div>
								<br> 
								<p>Fill the fields with the next information:</p>
								<br>
								<p><b>App ID Description</b>: name of the project</p>
								<p><b>Explicit App Id</b>: Bundle Identifier of the project</p>
								<p><b>App Services</b>: check Push Notifications</p>
								<br>
								<p>After pressing continue and submit It, an App ID will be registered.</p>
							</div>
						</li>
						<br><br>
						<h4><ins>Creating and configuring SSL Certificate</ins></h4>
						<br><br>
						<li>
							<div> 
								<p>In the list of App IDs will appear the name of your project, select it. In the service application list, push notification appears with orange lights, this means that your App ID can be used for push notification but you need to create SSL Certificate. For that, first press Edit button and then go to the Push Notification section.</p>
								<img alt="" src="<%=domain%>/resources/img/sslCertificate.png" width="50%">
								<br>
							</div>
						</li>
						<li>
							<div> 
								<p>Select Create Certificate button in the Developement SSL Certificate. A wizard will come up to create a Certificate Signing Request (CSR). You have already created the CSR file so press continue button and choose your CSR file. Select generate button and download it saving the file on your folder.</p>
							</div>
						</li>
						<li>
							<div> 
								<p>Double-click on YOUR_PROJECT_NAME.certSigningRequest file to add the certifiate to your KeyChain.</p>
							</div>
						</li>
						<li>
							<div> 
								<p>Go back to Keys section in KeyChain Access, you will see that there are two keys of your project. Export your private key, saved as <b>YOUR_PROJECT_NAME.p12</b> and enter the password that will be your <b>DEVELOPER_KEY</b>. Double-click on YOUR_PROJECT_NAME.p12 and enter your DEVELOPER_KEY as a password.</p>
							</div>
						</li>
						<li>
							<div> 
								<p>YOUR_PROJECT_NAME.p12 must be saved in a public server or site. For example, you can saved the certificate in the public folder of dropbox and then copy the public link in the section of information about iOS App Certificate, as shown in the figure below.</p>
								<img alt="" src="<%=domain%>/resources/img/urlCert.png" width="75%">
							</div>
						</li>
						<br><br>
						<h4><ins>Creating and configuring Provisioning profile</ins></h4>
						<br><br>
						<li>
							<div> 
								<p>Go to Provisioning Profiles section in iOS Dev Center, select All and press (+) button.</p>
								<img alt="" src="<%=domain%>/resources/img/iOSDevCenter2.png" width="50%">
								<br><br>
							</div>
						</li>
						<li>
							<div>
								<p>Select the provisioning profile type, in this case "iOS App Development". Press continue.</p>
								<br>
								<img alt="" src="<%=domain%>/resources/img/step1.png" width="50%">
								<br><br>
							</div>
						</li>
						<li>
							<div> 
								<p>Select your App Id. Press Continue.</p>
								<br>
								<img alt="" src="<%=domain%>/resources/img/step2.png" width="50%">
								<br><br>
							</div>
						</li>
						<li>
							<div>
								<p>Select the certificates you want to include in this provisioning profile. Press Continue.</p>
								<br>
								<img alt="" src="<%=domain%>/resources/img/step3.png" width="50%">
								<br><br>
							</div>
						</li>
						<li>
							<div>
								<p>Select the devices you want to include in this provisioning profile. Press Continue.</p>
								<br>
								<img alt="" src="<%=domain%>/resources/img/step3.png" width="50%">
								<br><br>
							</div>
						</li>
						<li>
							<div>
								<p>Set the name for this provisioning profile. Press Submit and save it.</p>
								<br>
								<img alt="" src="<%=domain%>/resources/img/step3.png" width="50%">
								<br><br>
							</div>
						</li>
						<li>
							<div> 
								<p>Finally add the provisioning profile to Xcode by double-clicking. Run your app and start receiving push data!!</p>
							</div>
						</li>
						<li>
							<div> 
								<p>NOTE: If you want the AppStore Distribution you have to repeat this process.</p>
							</div>
						</li>
						<li>
							<div> 
								<p>NOTE 2: If you want to run the App in background you need to modify "Name of your project-Info.plist". For that, right click on "Information Property List" and choose "Show Raw Keys/values". Later, select UIBackgroungModes and press (+) button and add Item0 with location value.</p>
							</div>
						</li>
					</ol> 
				</div>
			</div>

		</div>
	</div>
     <!-- ends About -->
     
   
     
 
       	
 
	</div>
	</div>
	

</body>
	
 
</html>