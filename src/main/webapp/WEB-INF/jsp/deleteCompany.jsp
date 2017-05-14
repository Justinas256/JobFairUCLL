<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Verwijder Bedrijf" name="title" />
	<jsp:param value="Verwijder bedrijf" name="h2" />
</jsp:include>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<p>Selecteer hieronder het bedrijf dat je wenst te verwijderen.</p>
		<form method="POST" action="deleteCompany">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<div class="form-group">
				<label for="companyID">Bedrijven: </label> <select
					class="form-control" name="companyID" id="companyID">
					<c:if test="${companies != null}">
						<c:forEach var="company" items="${companies}">
							<option value="<c:out value="${company.id}"/>">
								<c:out value="${company.companyName}" />,
								<c:out value="${company.email}" />:
								<c:out value="${company.username}" />
							</option>
						</c:forEach>
					</c:if>
				</select>
			</div>
			<div class="form-group">
				<label for="password">Je Wachtwoord: </label> <input
					type="password" class="form-control" id="password"
					placeholder="password" name="password">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary"
					value="Verwijder admin">Verwijder bedrijf</button>
			</div>
		</form>
	</div>
</div>
<jsp:include page="footer.jsp"/>