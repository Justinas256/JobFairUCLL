<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

    <jsp:include page="header.jsp">
            <jsp:param value="Confirm" name="title"/>
    </jsp:include>
    
        <h3>Wil je alle bedrijven uit het systeem verwijderen?</h3>
        <p>Het verwijderen van alle bedrijven zorgt tevens ook dat alle plaatsen weer vrij komen.</p>
        <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <form method="POST" action="deletecompanies" commandName="deletecompanies">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
            <p>
                <input type="button" id="delete" value="ja" class="btn btn-primary"/> 
                <input type="submit" name="cancel" value="neen" class="btn btn-primary"/>
            </p>
            <div id="pass" hidden="true">
                <div class="form-group">
                    <label for="password">Je Wachtwoord: </label> <input
                            type="password" class="form-control" id="password"
                            placeholder="password" name="password">
                </div>
                <div class="form-group">
                    <input type="submit" name="delete" class="btn btn-primary" value="Verwijder bedrijf"/>
                </div>
            </div>
            </div>
        </form>
    </div>

<script>
$(document).ready(function(){
    $("#delete").click(function(){
        $("#pass").show();
    });
});
</script>            
            
           
<jsp:include page="footer.jsp"/>