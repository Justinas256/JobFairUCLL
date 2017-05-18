<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<script src='https://www.google.com/recaptcha/api.js'></script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="UTF-8" content="">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Change later</title>
		<!-- Bootstrap CSS -->
		<link rel="stylesheet" href="css/bootstrap.css" >
		<link rel="stylesheet" href="css/font-awesome.css" >
		<link rel="stylesheet" href="css/style.css">
		<!-- jQuery -->
		<script src="http://code.jquery.com/jquery.js"></script>
		<!-- Bootstrap JavaScript -->
		<script src="js/bootstrap.js"></script>

		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
			<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
			<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
</head>

    <sec:authentication var="user" property="principal" />

    <header>
        <nav class="navbar navbar-default" role="navigation">
            <div class="container-fluid">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <h1 class="h1-normal"><a class="navbar-brand" href="Controller?action=home">Jobfair 2017</a></h1>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav"><li id="${param.home}"><a href="Controller?action=home"><i class="fa fa-home"></i> Home</a></li>
                        <li id="${param.spotoverview}"><a href="spots"><i class="fa fa-globe"></i> Alle plaatsen</a></li>

                        
                        <sec:authorize ifAllGranted="COMPANY">
                            <li id="${param.myspot}"><a href="Controller?action=myspot"><i class="fa fa-map-marker"></i> Mijn plaats</a></li>
                        </sec:authorize>   
                                   
                        <sec:authorize ifAllGranted="ADMIN">
                             <li id="${param.admin}"><a href="admin"><i class="fa fa-cog"></i> Admin Tools</a></li>
                        </sec:authorize>

                        <li> <a href="contact"><i class="fa fa-info-circle"> </i> Contact</a></li>
                    </ul>

                    <ul class="nav navbar-nav navbar-right">	

                        <sec:authorize access="isAuthenticated()">
                                <li><a href="account"><i class="fa fa-user-circle"></i> <c:out value="${user.username}"/></a></li>
                                <li><a href="logout"><i class="fa fa-sign-out"></i> Uitloggen</a></li>
                        </sec:authorize>
                        <sec:authorize access="!isAuthenticated()">
                            <li><a href="login"><i class="fa fa-sign-in" ></i> Login</a></li>
                        </sec:authorize>

                    </ul>
                </div><!-- /.navbar-collapse -->
            </div>
        </nav>
</header>
<main>
    <div class="container">
        <div class="row">
                <h2>${param.h2}</h2>
                <c:if test="${errors!=null}">
                    <div class="alert alert-danger">
                        <ul>
                            <c:forEach var="error" items="${errors}">
                                <li><c:out value="${error}"/></li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>
                <c:if test="${success!=null}">
                    <div class="alert alert-success">
                        <ul>
                        <c:forEach var="succes" items="${success}">
                            <li><c:out value="${succes}"/></li>
                        </c:forEach>
                        </ul>
                    </div>
                </c:if>