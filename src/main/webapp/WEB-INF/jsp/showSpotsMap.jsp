<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Verwijder Bedrijf" name="title" />
	<jsp:param value="${map.name} - show spots" name="h2" />
</jsp:include>

<style type="text/css">
        .respmap{
            height: auto;
        }

</style>
	
	
	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.maphilight.js"></script>
<script type="text/javascript" src="js/imageMapResizer.js"></script>

	
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <img src="img/standplaatsen-crop.png" usemap="#image-map-hemis" id="hemis" class="img-responsive respmap">
        <map name="image-map-hemis" id="hemis-map"  class="img-responsive">
            <area  alt="bla" id="1" coords="203,364,235,405,292,358,260,318" shape="poly">
        	<c:forEach var="spot" items="${map.spotsData}">
                    <area  alt="${spot.id}" id="${spot.id}" coords="${spot.coords}" shape="poly"
                        <c:choose>
                            <c:when test="${h.id==mine}">
                                    title="Mijn plaats"
                                    href="Controller?action=myspot"
                                    class="mine"
                            </c:when>
                            <c:when test="${empty bezet}">
                                    href="Controller?action=showopt&id=${h.id}"
                            </c:when>
                            <c:otherwise>
                                <c:forEach var="b" items="${bezet}">
                                    <c:if test="${h.id==b.spotID}">
                                        title="${b.user.companyName}"
                                        class="bezet"
                                    </c:if>
                                </c:forEach>
                                href="Controller?action=showopt&id=${h.id}"
                            </c:otherwise>
                        </c:choose>
                        >
        	</c:forEach>
        </map>
    </div>
</div>

 <script type="text/javascript">

    var ImageMap = function (map, img, width) {
            var n,
                areas = map.getElementsByTagName('area'),
                len = areas.length,
                coords = [],
                previousWidth = width;
            for (n = 0; n < len; n++) {
                coords[n] = areas[n].coords.split(',');
            }
            this.resize = function () {
                var n, m, clen,
                    x = img.offsetWidth / previousWidth;
                for (n = 0; n < len; n++) {
                    clen = coords[n].length;
                    for (m = 0; m < clen; m++) {
                        coords[n][m] *= x;
                    }
                    areas[n].coords = coords[n].join(',');
                }
                previousWidth = img.offsetWidth;     
                $(function () {
                    var data = {};
                    $('.respmap').maphilight();
                    data.alwaysOn = true;
                    data.fillColor = 'ff0000';
                    data.fillOpacity = '0.6';
                    $('.bezet').data('maphilight', data).trigger('alwaysOn.maphilight');  
                });

                $(function () {
                    var data = {};
                    $('.respmap').maphilight();
                    data.alwaysOn = true;
                    data.fillColor = '00ff00';
                    data.fillOpacity = '0.6';
                    $('.mine').data('maphilight', data).trigger('alwaysOn.maphilight'); 
                });

                return true;
            };
            
        },
    imageMap = new ImageMap(document.getElementById('hemis-map'), document.getElementById('hemis'), 1715);
    imageMap.resize();


window.onresize = function(){
setTimeout(function(){
    imageMap.resize();
    imageMap1.resize();  }
    , 500);
}


/*

window.setInterval(function(){
  imageMap.resize();
    imageMap1.resize();
}, 5000);
*/
    
     jQuery(function()
                            {
             jQuery('#hemis').maphilight();
             jQuery('#atrium').maphilight();
                            });

     $(function () {
                var data = {};
                $('.respmap').maphilight();
                data.alwaysOn = true;
                data.fillColor = 'ff0000';
                data.fillOpacity = '0.6';
                $('.bezet').data('maphilight', data).trigger('alwaysOn.maphilight');  
            });

            $(function () {
                var data = {};
                $('.respmap').maphilight();
                data.alwaysOn = true;
                data.fillColor = '00ff00';
                data.fillOpacity = '0.6';
                $('.mine').data('maphilight', data).trigger('alwaysOn.maphilight'); 
            });
</script>

<script>
$(document).ready(function() {
    $("map").on("click", function(event) {
        var x = event.pageX - this.offsetLeft;
        var y = event.pageY - this.offsetTop;
        alert("X Coordinate: " + x + " Y Coordinate: " + y);
    });
    $("img").on("click", function(event) {
        var x = event.pageX - this.offsetLeft;
        var y = event.pageY - this.offsetTop;
        alert("X Coordinate: " + x + " Y Coordinate: " + y);
    });
    });
</script>

		