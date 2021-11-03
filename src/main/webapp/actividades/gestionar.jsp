<%@page import="es.ubu.asi.model.File"%>
<%@page import="es.ubu.asi.model.Activity"%>
<%@ page import="es.ubu.asi.controller.AddActivityController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="../head.jsp"%>
<title>Gestionar Actividad</title>
</head>
<body>

	<%
		// mensajes
		String error_msg = (String)request.getAttribute("error");
		String success_msg = (String)request.getAttribute("msg");

		// instancias
		Activity a = (Activity)request.getAttribute("activity");
		File f = (File)request.getAttribute("files");

		// formato
		String formAction = a != null ? "updateActivity" : "addActivity";
		String titlePage = a != null ? "Modificar Actividad" : "Nueva Actividad";
		String submitButton = a != null ? "Modificar" : "Registrar";
    %>

	<div class="app">
		<h1 class="title">
			<%= titlePage %>
		</h1>

  		<form class="form" method="post" action="<%= formAction %>">
  			<% if(error_msg != null && !error_msg.equals("")) { %>
			  <p class="error_msg"> <%= error_msg %> <p>
			<% } %>
			<% if(success_msg != null && !success_msg.equals("")) { %>
			  <p class="success_msg"> <%= success_msg %> <p>
			<% } %>

			<% if(a != null) { %>
			    <!-- guardamos el id de la actividad a modificar -->
				<input type="hidden" type="text" name="id" value="<%= a.getId() %>">
			<% } %>

	  		<label for="title">Titulo: </label>
	  		<input type="text" name="title" value="<%= a != null ? a.getTitle() : ""%>">

	  		<label for="description">Descripción: </label>
	  		<textarea type="text" name="description"><%= a != null ? a.getDescription() : ""%></textarea>

	  		<label for="suggestions">Recomendaciones: </label>
	  		<textarea type="text" name="suggestions"><%= a != null ? a.getSuggestions() : ""%></textarea>

	  		<label for="teachers">Docentes: </label>
	  		<input type="text" name="teachers" value="<%= a != null ? a.getTeachers() : ""%>">

	  		<label for="days">Días de la semana: </label>
	  		<input type="text" name="days" value="<%= a != null ? a.getDays() : ""%>">

	  		<div class="group-inline">
	  			<div>
	  				<label for="timeStart">Hora Inicio: </label>
		  			<input type="time" name="timeStart" value="<%= a != null ? a.getScheduleStart() : ""%>">
	  			</div>
	  			<div>
	  				<label for="timeEnd">Hora Fin: </label>
		  			<input type="time" name="timeEnd" value="<%= a != null ? a.getScheduleEnd() : ""%>">
	  			</div>
	  		</div>

	  		<div class="group-inline">
	  			<div>
	  				<label for="dateStart">Fecha Inicio: </label>
		  			<input type="date" name="dateStart" value="<%= a != null ? a.getDateStart() : ""%>">
	  			</div>
	  			<div>
	  				<label for="dateEnd">Fecha Fin: </label>
		  			<input type="date" name="dateEnd" value="<%= a != null ? a.getDateEnd() : ""%>">
	  			</div>
	  		</div>

	  		<input class="btn info" type="reset" value="Reset">
	        <input class="btn success" type="submit" value="<%= submitButton %>">
	  	</form>

	  	<a href="<%= request.getContextPath() %>/home.jsp">Volver a Inicio</a>
  	</div>
</body>
</html>