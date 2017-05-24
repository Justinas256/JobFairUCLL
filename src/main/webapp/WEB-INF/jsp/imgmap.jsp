<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
        .respmap{
            height: auto;
        }

</style>
	
	
	
<script
  src="https://code.jquery.com/jquery-3.1.1.js"
  integrity="sha256-16cdPddA6VdVInumRGo6IbivbERE8p7CQR3HzTBuELA="
  crossorigin="anonymous"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.maphilight.js"></script>
<script type="text/javascript" src="js/imageMapResizer.js"></script>

	
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
        <c:forEach var="map" items="${maps}">
            <img src="${pageContext.request.contextPath}/map/image/${map.id}" usemap="#${map.id}+map" id="${map.id}+img" class="img-responsive respmap">
            <map name="${map.id}+map" id="${map.id}+map"  class="img-responsive">
                    <c:forEach var="h" items="${map.spotsData}">

                            <area  alt="${h.id}" id="${h.id}" coords="${h.coords}" shape="poly"
                                    <c:choose>
                                            <c:when test="${h.spot.id==mine}">
                                                    title="Mijn plaats"
                                                    href="myspot"
                                                    class="mine"
                                            </c:when>
                                            <c:when test="${empty bezet}">
                                                    href="showopt&id=${h.spot.id}"
                                                    class="bezet"
                                            </c:when>
                                            <c:otherwise>
                                            <c:forEach var="b" items="${bezet}">
                                                    <c:if test="${h.spot.id==b.id}">
                                                            title="${b.user.companyName}"
                                                            class="bezet"
                                                    </c:if>
                                            </c:forEach>
                                                    href="showopt&id=${h.spot.id}"
                                    </c:otherwise>
                                    </c:choose>>
                    </c:forEach>
            </map>
        </c:forEach>
    </div>
</div>

 <script type="text/javascript">
     
     var sample = new Array();

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
     

     <c:forEach var="map" items="${maps}">
        imageMap = new ImageMap(document.getElementById('${map.id}+map'), document.getElementById('${map.id}+img'), 1715);
        imageMap.resize();     
     </c:forEach>


window.onresize = function(){
setTimeout(function(){
    imageMap.resize();
}
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
             jQuery('#${map.id}+img').maphilight();
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