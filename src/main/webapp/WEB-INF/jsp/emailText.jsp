<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="header.jsp">
	<jsp:param value="admin" name="type" />
	<jsp:param value="Jobbeurs - Voeg Admin Toe" name="title" />
	<jsp:param value="Stuur herinneringsmail" name="h2" />
</jsp:include>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <form method="POST" action="endmail" role="form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label for="endmailsubject">Onderwerp: </label>
                <input type="text" class="form-control" id="endmailsubject" name="endmailsubject" value="${endmail.subject}"/>
            </div>
            <div class="form-group">
                <label for="endmailsubject">Tekst:</label> 
                <textarea type="text" class="form-control" rows="12" id="endmailtext" name="endmailtext" >${endmail.text}</textarea>
            </div>
            <div class="form-group">
                <input type="hidden" name="endemailid" value="${endmail.id}">
                <input type="button" id="btn" value="Beheerder eind datum" class="btn btn-primary" />
                <input type="hidden" id="deadlinedate" value='${deadlinedate}'/>
                <button type="submit" class="btn btn-primary" id="submit" value="Stuur herinneringsmail">Stuur herinneringsmail</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<script>
    jQuery("#btn").on('click', function() {
        var $txt = jQuery("#endmailtext");
        var caretPos = $txt[0].selectionStart;
        var textAreaTxt = $txt.val();
        var txtToAdd = $('#deadlinedate').val();
        $txt.val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
    });
</script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>