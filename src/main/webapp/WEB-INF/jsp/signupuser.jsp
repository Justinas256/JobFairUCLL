<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<jsp:include page="header.jsp">
	<jsp:param value="admin" name="type"/>
	<jsp:param value="Jobbeurs - Registreer" name="title"/>
	<jsp:param value="Registreer bedrijf" name="h2"/>
</jsp:include>

<div class="row">	
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <form:form id="users" method="POST" action="signupcompany" commandName="users" modelAttibute="users" novalidate="novalidate">
            <form:errors path="*" modelAttibute="errors"/>
            <legend class="sr-only">Registreer bedrijf</legend>
            <td><form:hidden path="id" /></td>
            <div class="form-group">
                <label for="companyName">Naam van bedrijf: </label>
                <form:input path="companyName" type="text" class="form-control" id="companyName" name="companyName"/>
            </div>
            <div class="form-group">
                <label for="contactName">Naam van contactpersoon: </label>
                <form:input path="contactName" type="text" class="form-control" id="contactName" name="contactName"/>
            </div>
            <div class="form-group">
                <label for="email">Email van contactpersoon: </label>
                <form:input path="email" type="text" class="form-control" id="email" name="email" />
            </div>
                <button type="submit" class="btn btn-primary" id="submit" value="registreer bedrijf"> Registreer bedrijf </button>
        </form:form>
    </div>
</div>

<jsp:include page="footer.jsp"/>