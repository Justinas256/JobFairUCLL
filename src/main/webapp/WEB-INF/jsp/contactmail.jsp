<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Contact" name="title"/>
</jsp:include>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
            <h3>Contact</h3>
            <form method="POST" action="sendcontact" novalidate="novalidate">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <fieldset>
                        <legend>Vragen / Opmerkingen</legend>
                        <div class="form-group">
                                <label for="subject">Onderwerp * </label>
                                <input class="form-control" type="text" name="subject" placeholder="Onderwerp" value="${subj}"/>
                        </div>
                        <div class="form-group">
                                <label for="name">Naam * </label>
                                <input class="form-control" type="text" name="name" placeholder="Naam" value="${from}"/>
                        </div>
                        <div class="form-group">
                                <label for="message">Je bericht * </label>
                                <textarea class="form-control" name="message" class="form-control" rows="3" placeholder="Vragen / Opmerkingen">${msg}</textarea>
                        </div>
                        <div class="form-group">
                                <!--div class="g-recaptcha" data-sitekey="6LftmQ4UAAAAAH1SFuSsQkbU9BYViukh6HjUvcqr"></div-->
                                <p> * Deze velden zijn verplicht.</p>
                                <input type="submit" class="btn btn-primary" value="Verzenden"/>
                        </div>
                    </fieldset>
            </form>
	</div>
</div>
<jsp:include page="footer.jsp"/>