<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Verwijder Bedrijf" name="title" />
	<jsp:param value="Map manager" name="h2" />
</jsp:include>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<form method="POST" action="map">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="form-group">
                            <label for="companyID">Map </label> 
                            <select class="form-control" name="mapID" id="mapID">
                                <c:if test="${maps != null}">
                                    <c:forEach var="map" items="${maps}">
                                        <option value="<c:out value="${map.id}"/>">
                                            <c:out value="${map.name}" />
                                        </option>
                                    </c:forEach>
                                </c:if>
                            </select>
			</div>
			<div class="form-group">
                            <button type="submit" id="delete" name="delete" class="btn btn-primary" value="delete">Delete map</button>
                            <button type="submit" id="addSpots" name="addSpots" class="btn btn-primary" value="Add spots">Add spots</button>
                            <button type="submit" id="showSpots" name="showSpots" class="btn btn-primary" value="Show spots">Show spots</button>
			</div>
		</form>
	</div>
</div>
<jsp:include page="footer.jsp"/>