<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery.parseHTML demo</title>
  <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
</head>
<body>
<h1>RSS Feed App DB Item View</h1>

<h2>${msg}</h2>
<h2 ng-repeat-start="item in items">${items}</h2>

<div id="log">
  <h3>Content:</h3>
</div>

<script>
var $log = $( "#log" ),
  str = "hello, <b>my name is</b> jQuery.",
  html = $.parseHTML( str ),
  nodeNames = [];

// Append the parsed HTML
$log.append( html );

// Gather the parsed HTML's node names
$.each( html, function( i, el ) {
  nodeNames[ i ] = "<li>" + el.nodeName + "</li>";
});

// Insert the node names
$log.append( "<h3>Node Names:</h3>" );
$( "<ol></ol>" )
  .append( nodeNames.join( "" ) )
  .appendTo( $log );
</script>

</body>
</html>





