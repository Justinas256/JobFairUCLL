<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Verwijder Bedrijf" name="title" />
	<jsp:param value="${map.name} - add spots" name="h2" />
</jsp:include>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <form method="POST" action="addSpotToMap" id="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <input type="hidden" name="mapID" value="${map.id}"/>

                    <input type="hidden" rows=3 id="coords" name="coords" class="canvas-area img-responsive respmap"
                        placeholder="Shape Coordinates" 
                        data-image-url="${pageContext.request.contextPath}/map/image/${map.id}"> </input>

                    <input type="submit" value="add"/>
        </form>
    </div>
</div>


<script language="javascript" src="js/jquery.canvasAreaDraw.min.js"></script>
<jsp:include page="footer.jsp"/>