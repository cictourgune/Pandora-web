<!DOCTYPE html> 
<html> 
	<head>  
		<%@ include file="/WEB-INF/views/header.jsp"%>		
	</head> 

	<body>  
 		<div id="registro-page" data-role="page" data-dialog="true">  
			<div data-role="header" >
				<h3>Sign up</h3> 
			</div>
			 
			<div data-role="content">
				<form id="registro-form" method="post" data-ajax="false">
					<label for="registro-usuario">Username</label> 
					<input type="text" name="name" id="registro-usuario" placeholder="min. 5 characters"/> 
					<label for="registro-password">Password</label> 
					<input type="password" name="password" id="registro-password" placeholder="min. 7 characters"/>
					<label for="registro-repite-password">Repeat password</label> 
					<input type="password" name="repite-password" id="registro-repite-password"/> 
				    <label for="email">Email</label>
		            <input type="email" name="email" id="email" value="" placeholder="You will receive a confirmation email"/>
		            
					<fieldset class="ui-grid-a">
						<div class="ui-block-a"> 
							<a href="#" id="registro-submit" data-role="button" data-transition="none" data-icon="check" data-iconpos="right">OK</a> 
						</div>
						<div class="ui-block-b">
							<a href="#" id="registro-cancel-submit" data-role="button" data-transition="none" data-icon="delete" data-iconpos="right">Cancel</a>  
						</div>
		   			</fieldset>
				</form>	
			</div>	
			<div id='registro-error' data-role="content" style="text-align: center;">  
			</div> 	 
		</div>	 
	</body>	
</html>