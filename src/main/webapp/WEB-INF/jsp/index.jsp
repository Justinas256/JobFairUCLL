<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<jsp:include page="header.jsp">
		<jsp:param value="Jobbeurs - Home" name="title"/>
		<jsp:param value="current" name="home"/>
		<jsp:param value="UCLL Jobbeurs 2017" name="h2"/>
	</jsp:include>
		<c:if test="${sessionScope.user!=null}">
			<h3>Welkom <c:out value="${sessionScope.user.companyName}"/>!</h3>
		</c:if>
		<c:choose>
			<c:when test="${reserveer!=null}">
				<h3>Plaats ${spotnr} werd gereserveerd.</h3>
			</c:when>
			<c:when test="${update!=null}">
				<h3>Plaats ${spotnr} werd geüpdatet.</h3>
			</c:when>
			<c:when test="${annuleer!=null}">
				<h3>Plaats ${spotnr} werd geannuleerd.</h3>
			</c:when>
		</c:choose>
		
<jsp:include page="footer.jsp"/>