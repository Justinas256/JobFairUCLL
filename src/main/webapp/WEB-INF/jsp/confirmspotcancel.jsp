<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="header.jsp">
    <jsp:param value="Confirm" name="title"/>
</jsp:include>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <h3>Reservering van plaats ${spot.spotNo} voor ${user.companyName} annuleren?</h3>
        <form method="POST" action="confirmspotcancel">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <input type="hidden" name="spotID" value="${spot.id}">
                <input type="submit" name="submit" value="ja" class="btn btn-primary"> 
                <input type="submit" name="submit" value="neen" class="btn btn-primary">
            </div>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>