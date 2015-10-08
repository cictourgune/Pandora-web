<%
String domain="/pandora"; 
String version="1.3";
%>
  
<title>pandora</title> 

<meta http-equiv="Content-Type" content="text/html" charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
 

<link rel="stylesheet" href="<%=domain%>/resources/css/styles.css" />
 
 

  
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,300' rel='stylesheet' type='text/css'>

<%-- <link rel="stylesheet" href="<%=domain%>/resources/css/pandora.css" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile.structure-1.3.1.min.css" /> 
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script> 
<script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script> --%>


<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.css" />
<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.2/jquery.mobile-1.4.2.min.js"></script>

<!-- plugin -->
<script src="<%=domain%>/resources/js/plugin/jquery.json-2.2.min.js?v=<%=version%>"></script> 
<script type="text/javascript" src="https://maps.google.com/maps/api/js?v=3.exp&libraries=places&sensor=false"></script> 


<link rel="stylesheet" href="<%=domain%>/resources/css/chardinjs.css" />
<script charset="UTF-8" src="<%=domain%>/resources/js/plugin/chardinjs.min.js?v=<%=version%>"></script>  
 

<!-- PRODUCTION
 <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCqc2iIoJMk8AuvMzHKpvZFyZ4jYUEbOik&sensor=true">
</script>

<script type="text/javascript"> 
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-36625862-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })(); 
</script>
-->


<!-- pandora --> 
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/dashboard.js?v=<%=version%>"></script>  
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/utils.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/registro.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/index.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/login.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/logout.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/download.js?v=<%=version%>"></script>
<script charset="UTF-8" src="<%=domain%>/resources/js/pandora/analytics.js?v=<%=version%>"></script>

<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">
	  google.load('visualization', '1.0', {'packages':['corechart']});
	  google.load('visualization', '1', {'packages':['annotatedtimeline']});

</script>
<style>
  .ui-page { background: white;}
</style>
 
