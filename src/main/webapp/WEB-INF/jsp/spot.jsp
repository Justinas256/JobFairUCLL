<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp">
	<jsp:param value="Jobbeurs - Plaats${spot.spotNo}" name="title"/>
	<jsp:param value="current" name="myspot"/>
	<jsp:param value="Plaats   ${spot.spotNo} - ${user.companyName}" name="h2"/>
</jsp:include>

<div class="row">
    <div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
        <c:choose>
            <c:when test="${spot==null}">
                    <p>U heeft nog geen plaats gereserveerd.</p>
            </c:when>
            <c:otherwise>
                <ul>
                        <li>
                                <p>Aantal stoelen: ${spot.chairs}</p>
                        </li>
                        <li>
                                <p>Aantal tafels: ${spot.tables}</p>
                        </li>
                        <li>
                                <p>Elektriciteit: ${electricity}</p>
                        </li>
                        <li>
                                <p>Extra opmerkingen:</p>
                                <p>${spot.remarks}</p>
                        </li>
                </ul>

                <form method="POST" action="updatespot" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <input type="hidden" name="spotID" value="${spot.id}">
                        <button type="submit" class="btn btn-primary" value="Wijzig voorkeuren">Wijzig voorkeuren</button>
                    </div>
                </form>
                <form method="POST" action="cancelspot" role="form">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <input type="hidden" name="spotID" value="${spot.id}">
                        <button type="submit" class="btn btn-primary" value="Annuleer reservering">Annuleer reservering</button>
                    </div>
                </form>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<jsp:include page="footer.jsp"/>