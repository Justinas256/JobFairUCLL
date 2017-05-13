<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Alle Plaatsen" name="title"/>
	<jsp:param value="current" name="spotoverview" />
	<jsp:param value="Overzicht van alle plaatsen" name="h2" />
</jsp:include>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">

		<table class="table table-striped">
			<tr>
				<th>Plaats </th>
				<th>Bedrijfsnaam </th>
				<th> </th>
				
				<th><a href="spots">Alle plaatsen </a></th>
				<th><a href="freespots">Vrije plaatsen </a></th>
				<th><a href="takenspots">Bezette plaatsen</a></th>
				<th><a href="sorttakenspots">Bezette plaatsen op alfabetische volgorde</a></th>
			</tr>
			<c:forEach var="spot" items="${spots}">
				<tr>
                                    <%--
                                        <td><a href="Controller?action=showopt&id=<c:out value="${spot.spotID}"/>">${spot.spotID}</a></td>
                                    --%>
                                        <td><a href="Controller?action=showopt&id=<c:out value="${spot.id}"/>">${spot.spotNo}</a></td>
					<td>${spot.user.companyName}</td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>

				</tr>
			</c:forEach>
		</table>

	</div>
</div>

<jsp:include page="footer.jsp"/>