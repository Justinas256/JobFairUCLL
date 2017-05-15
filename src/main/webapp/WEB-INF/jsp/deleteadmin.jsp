<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="admin" name="type" />
	<jsp:param value="Jobbeurs - Verwijder admin" name="title" />
	<jsp:param value="Verwijder beheerder" name="h2" />
</jsp:include>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <p>Selecteer hieronder de beheerder die je wenst te verwijderen.</p>
        <form method="POST" action="deleteadmin">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
                <label for="adminID">Beheerder: </label>
                <select class="form-control" name="adminID" id="adminID">
                    <c:forEach var="admin" items="${admins}">
                        <option value="<c:out value="${admin.username}"/>"><c:out value="${admin.username}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="password">Je Wachtwoord: </label>
                <input type="password" class="form-control" id="password" placeholder="password" name="password"/>
            </div>
            <div class="form-group">
                <button type="submit" class="btn btn-primary" value="Verwijder admin">Verwijder beheerder</button>
            </div>
        </form>
    </div>
</div>

<jsp:include page="footer.jsp"/>