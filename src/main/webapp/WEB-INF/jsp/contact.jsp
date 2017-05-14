<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Contact" name="title"/>
</jsp:include>
<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <h3>Contact</h3>
        <form:form method="POST" action="mailqestion" commandName="mail" modelAttibute="mail" novalidate="novalidate">
            <fieldset>
                <legend>Vragen / Opmerkingen</legend>
                <div class="form-group">
                        <label for="subject">Onderwerp * </label>
                        <form:input class="form-control" type="text" name="subject" placeholder="Onderwerp"/>
                </div>
                <div class="form-group">
                        <label for="name">Naam * </label>
                        <form:input class="form-control" type="text" name="name" placeholder="Naam"/>
                </div>
                <div class="form-group">
                        <label for="message">Je bericht * </label>
                        <form:textarea class="form-control" name="message" class="form-control" rows="3" placeholder="Vragen / Opmerkingen"/>
                </div>
                <div class="form-group">
                        <div class="g-recaptcha" data-sitekey="6LftmQ4UAAAAAH1SFuSsQkbU9BYViukh6HjUvcqr"></div>
                        <p> * Deze velden zijn verplicht.</p>
                        <button type="submit" class="btn btn-primary" id="submit" value="Verzenden">Verzenden</button>
                </div>
            </fieldset>
        </form:form>
    </div>
</div>
<jsp:include page="footer.jsp"/>