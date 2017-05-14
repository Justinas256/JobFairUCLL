<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp">
	<jsp:param value="admin" name="type" />
	<jsp:param value="Admin" name="title" />
	<jsp:param value="Tools voor beheerders" name="h2" />
</jsp:include>
	
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<form method="POST" action="saveuserscvs" enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<legend><i class="fa fa-upload"></i> Bedrijven importeren</legend>
			<div class="form-group form-inline">
				<p>Gebruik deze tool om meerdere bedrijven tegelijk te importeren uit een '.csv' bestand.</p>
			<p>Kijk ook na of het juist is ingedeeld; 'bedrijfsnaam;contactpersoon;email;...'  met kolomhoofdingen 'Bedrijfsnaam;Naam contactpersoon;E-mailadres contactpersoon' 
				(deze indeling is vereist voor het correct weergeven van de informatie op deze website).</p>
				<p><a href="files/csvexample.csv">Voorbeeld</a></p>
				<label for="file">Bestand: </label>
				<input type="file" class="form-control" id="file" name="file" value="Upload file">
				<button type="submit" class="btn btn-primary" value="Bestand uploaden"><i class="fa fa-upload"></i> Bestand uploaden</button>
				<p>Dit kan even duren - tijd voor een pauze? <i class="fa fa-coffee"></i></p>
			</div>
		</form>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<form method="POST" action="setdeadline">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<legend><i class="fa fa-clock-o"></i> Verander eind datum</legend>
		<div class="form-group form-inline">
			<p>Vanaf deze datum zullen geregistreerde bedrijven geen wijzigingen meer kunnen toepassen op hun reservering.</p>
			<label for="date">Eind datum:  </label>
			<input type="text" name="date" class="form-control" id="date" placeholder="dd-mm-yyyy">
			<button type="submit" class="btn btn-primary" value="Toepassen">Toepassen</button>
			</div>
		</form>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<h4 class="bordboth4"><i class="fa fa-wrench"></i> Bedrijf Manager</h4>
		<p>Deze tool geeft je de mogelijkheid om bedrijven toe te voegen en te verwijderen.
				Na afloop van de jobbeurs kunnen ook alle bedrijven verwijderd worden.</p>
		<a class="btn btn-primary" href = "signup">Voeg Bedrijf toe</a>
		<a class="btn btn-danger" href = "Controller?action=getBedrijven">Verwijder Bedrijf</a>
		<a class="btn btn-danger" href="confirmdeleteAll.jsp">Verwijder alle Bedrijven</a>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<h4 class="bordboth4"><i class="fa fa-link"></i> Link bedrijven aan vrije plaatsen</h4>
		<p>Met deze tool kan je een bedrijf zonder plaats een plaats toekennen.</p>
		<a class="btn btn-primary" href = "linkSpot">Link bedrijven aan vrije plaatsen</a>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<h4 class="bordboth4"><i class="fa fa-lock"></i> Beheerders</h4>
		<p>Gebruik deze tool om nieuwe beheerders toe te voegen of om een beheerder te verwijderen</p>
		<a class="btn btn-primary" href = "adminform">Voeg een beheerder toe</a>
		<a class="btn btn-danger" href = "Controller?action=getAdmin">Verwijder een beheerder</a>
	</div>
</div>


<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<form method="POST" action="Controller?action=endMail">
			<legend><i class="fa fa-bell"></i> Herinnering</legend>
			<p>Als je merkt dat een aantal bedrijven nog geen plaats gekozen hebben en de deadline nadert, kan je deze tool gebruiken om naar die bedrijven een herinneringsmail te sturen</p>
			<div class="form-group form-inline">
				<button type="submit" class="btn btn-primary" value="Submit">Stuur herinneringsmail</button>
			</div>
		</form>
	</div>
</div>

<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<h4 class="bordboth4"><i class="fa fa-list-ul"></i> Alle bedrijven</h4>
		<p>Deze tool toont je een overzicht van alle bedrijven en biedt ook de mogelijkheid om een '.csv' bestand te downloaden in hetzelfde formaat dat je nodig heeft voor de upload-tool.</p>
		<a class="btn btn-primary" href = "Controller?action=companies">Lijst met alle bedrijven</a>
		<a class="btn btn-primary" href = "Controller?action=download"><i class="fa fa-download"></i> Download lijst</a>
	</div>
</div>
<div class="row">
	<div class="col-xs-12 col-sm-12 col-md-8 col-lg-6">
		<h4 class="bordboth4"><i class="fa fa-file-text"></i> Handleiding</h4>
		<p>Niet meer zeker hoe je deze site optimaal kan benutten? Gebruik dan deze tool om de handleiding te bekijken.</p>
		<a class="btn btn-primary" href="files/Readme.pdf" target="_blank">Bekijk de handleiding</a>
	</div>
</div>
	
	
<jsp:include page="footer.jsp"/>