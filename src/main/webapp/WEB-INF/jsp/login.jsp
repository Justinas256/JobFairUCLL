<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<jsp:include page="header.jsp">
		<jsp:param value="Jobbeurs - Login" name="title"/>
		<jsp:param value="current" name="Login"/>
		<jsp:param value="Login" name="h2"/>
	</jsp:include>
	
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<form action="login" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		
			<legend class="sr-only">Login Form</legend>
			<div class="form-group">
				<label for="username">Gebruikersnaam</label>
				<input type="text" class="form-control" id="username" placeholder="Gebruikersnaam" name="username" value="${userid}" autofocus>
			</div>
			<div class="form-group">
				<label for="password">Wachtwoord</label>
				<input type="password" class="form-control" id="password" placeholder="Wachtwoord" name="password">
			</div>
			<div class="form-group">
				<a href="Controller?action=resetpw">Wachtwoord vergeten?</a>
			</div>
			<div class="form-group">
				<input type="submit" name="submit" class="btn btn-primary" value="Inloggen">
			</div>

			<c:if test="${param.error ne null}">
				<div class="alert-danger">Invalid username and password.</div>
			</c:if>
			<c:if test="${param.logout ne null}">
				<div class="alert-normal">You have been logged out.</div>
			</c:if>

		</form>
	</div>
</div>
<jsp:include page="footer.jsp"/>