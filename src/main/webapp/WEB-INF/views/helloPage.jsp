<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.1/jquery.min.js"></script>

<html>
<body>
<h1>Hello at Sergii RSS Parser site!</h1>


	<div class="span1">
		<input id="button-continue" type="button" onclick="closeAndSubmit();"
			class="btn-submit"
			value="Login to the site"/>
	</div>

</body>
</html>

<script type="text/javascript">
    var contents= [{"id":1,"user":"Me"}];
    //var jsonfile={json:JSON.stringify(contents)};
    var myURL="login";
	function closeAndSubmit(urlTo) {
	window.location = myURL;
    /*	$.ajax({
        type: 'GET',
        url: myURL,
        //data: jsonfile,
        dataType: "json",
        success: function(data) {
        location.href = myURL;
        },
            failure: function(errMsg) {
             alert("FAILURE");
        }
    });*/
    }
</script>