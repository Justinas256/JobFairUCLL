<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="current" name="companiesoverview" />
	<jsp:param value="Jobbeurs - Overzicht Bedrijven" name="title"/>
	<jsp:param value="Overzicht van alle bedrijven" name="h2" />
</jsp:include>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">

		<table class="table table-striped">
			<tr>
				<th><a href="companies">Bedrijfsnaam</a></th>
				<th><a href="companiesByContact">Contactnaam</a></th>
				<th><a href="companiesByEmail">Email</a></th>
				<th><a href="companiesBySpot">Plaats</a></th>

			</tr>
			<c:forEach var="company" items="${companies}" varStatus="status">
				<tr>
					<td>${company.companyName}</td>
					<td>${company.contactName}</td>
					<td>${company.email}</td>
					<c:choose>
						<c:when test="${empty companies}">
							<td></td>
						</c:when>
						<c:otherwise>
							<td> ${company.spots[0].spotNo}</td>
						</c:otherwise>
					</c:choose>				
				</tr>
			</c:forEach>
		</table>

	</div>
</div>

<jsp:include page="footer.jsp"/>