<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Link" name="title"/>
	<jsp:param value="Link een vrije plaats aan een bedrijf" name="h2"/>
</jsp:include>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<form method="POST" action="linkSpot" role="form">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<legend class="sr-only">Sign up</legend>
			<div class="form-group">
				<label for="spotID">Lege plaats: </label>
				<select class="form-control" name="spotID" id="spotID">
					<c:forEach var="freeSpot" items="${freeSpots}">
						<option value="<c:out value="${freeSpot.id}"/>"><c:out value="${freeSpot.spotNo}"/></option>
					</c:forEach>
				</select>
			</div>
			<div class="form-group">
				<label for="userID">Bedrijven die niet gekozen hebben: </label>
				<select class="form-control" name="userID" id="userId">
					<c:forEach var="freeUser" items="${freeUsers}">
						<option value="<c:out value="${freeUser.id}"/>"><c:out value="${freeUser.username}"/></option>
					</c:forEach>
				</select>
			</div>
			<button type="submit" class="btn btn-primary" id="submit" value="Link plaats"> Link plaats </button>
		</form>
	</div>
</div>
<jsp:include page="footer.jsp"/>