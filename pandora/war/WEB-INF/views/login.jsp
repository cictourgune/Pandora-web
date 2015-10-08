<!DOCTYPE html> 
<html> 
	<head>   
		<%@include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
		<div id="login-page" data-role="page"> 
		<link rel="stylesheet" href="<%=domain%>/resources/css/bootstrap.min.css" /> 
			<div data-role="content">  
				<div class="row-fluid" align="center" >
					 
					<div class="span6 offset3">
					<form id="login-form" class="ui-body ui-body-d ui-corner-all" method="post"  data-ajax="false" action="<%=domain%>/j_spring_security_check"> 
					  <h2>Login</h2>
					  
			          <input type="text" name="j_username" id="j_username" value="" placeholder="Username"/>
			          <input type="password" name="j_password" id="j_password" value="" placeholder="Password" />
			          
	 				  <label class="forCheckbox" for='_spring_security_remember_me'>
                      Remember me
                      <input type='checkbox' checked="checked" name='_spring_security_remember_me'/>
                      </label>
			    	  <div class="ui-grid-a">
				    	  <div class="ui-block-a"><button id="submit">Ok</button></div>
				    	  <div class="ui-block-b"><a id="cancel" href="./" data-role="button" data-rel="back">Cancel</a></div>
				   	  </div>
			    	  <div id="errorlogin">
					  </div>
					  
					</form> 
					</div> 
				 
					
					
				</div>
			</div> 
		</div> 
	</body>	
</html>