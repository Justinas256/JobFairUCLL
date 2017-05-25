
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Canvas Area Draw</title>
<link href="http://www.jqueryscript.net/css/jquerysctipttop.css" rel="stylesheet" type="text/css">
<link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/css/bootstrap.no-icons.min.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/3.0/css/font-awesome.css" rel="stylesheet">
<link href="http://netdna.bootstrapcdn.com/font-awesome/3.0/css/font-awesome-ie7.css" rel="stylesheet">

</head>
<body>

<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.maphilight.js"></script>
<script type="text/javascript" src="js/imageMapResizer.js"></script>

<div class="row">
<div class="span6">
<h2> Image 1 </h2>

<form method="POST" action="try" id="form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <input type="hidden" name="location" value="location"/>

            <input type="text" rows=3 id="coords" name="coords" class="canvas-area img-responsive respmap"
	        placeholder="Shape Coordinates" 
	        data-image-url="img/standplaatsen-crop.png"> </input>
            
            <input type="submit" value="add"/>
</form>

</div>
</div>

<script language="javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script> 
<script src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.2.2/js/bootstrap.min.js"></script> 
<script language="javascript" src="js/jquery.canvasAreaDraw.min.js"></script>

</body>
</html>
