<%@page import="java.util.Base64"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="es.ubu.asi.model.Activity" %>
<%@ page import="es.ubu.asi.model.File" %>
<%@ page import="es.ubu.asi.dao.ActivityDAO" %>
<%@ page import="es.ubu.asi.dao.FileDAO" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@include file="head.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.js"></script>
<title>Home Actividades de UBUGym </title>
</head>
<body>
	<%@include file="menu.jsp"%>
	<%
        List<Activity> activities = ActivityDAO.getActivities();
		String error_msg = (String)request.getAttribute("error");
	  	String succes_msg = (String)request.getAttribute("msg");
    %>
	<h1> Sistema de Gestión de Actividades de UBUGym </h1>

	<% if(error_msg != null && !error_msg.equals("")) { %>
	    <p class="error_msg"> <%= error_msg %> <p>
	<% } %>

	<% if(succes_msg != null && !succes_msg.equals("")) { %>
	    <p class="success_msg"> <%= succes_msg %> <p>
	<% } %>

	<div class="app">
		<div class="content">
			<table border="1">
				<caption>
					<h3 style="float: left;"> Actividades registradas en UBUGym: </h3>
					<form method="post" action="handleActivity">
						<button name="method" value="add" class="btn success submit" style="float: right;"> <i class='fas fa-plus' style='font-size:14px'></i> </button> </th>
					</form>
				</caption>
		         <tr>
		             <th> Título </th>
		             <th> Descripción </th>
		             <th> Recomendaciones </th>
		             <th> Docentes </th>
		             <th> Días </th>
		             <th> Horario </th>
		             <th> Inicio </th>
		             <th> Fin </th>
		             <th> Operaciones </th>
		             <th> Ficheros </th>
		         </tr>

		         <%
		         	for (Activity a : activities) {
		         %>
		         <tr valign="middle">
		             <td> <%= a.getTitle()%></td>
		             <td> <%= a.getDescription()%></td>
		             <td> <%= a.getSuggestions()%></td>
		             <td> <%= a.getTeachers()%></td>
		             <td> <%= a.getDays()%></td>
		             <td> <%= a.getSchedule()%></td>
		             <td> <%= a.getDateStart()%></td>
		             <td> <%= a.getDateEnd()%></td>
		             <td>
		             	<form method="post" action="handleActivity">
		             		<input type="hidden" name="activity" value="<%= a.getId() %>" />
							<button name="method" value="update" class="btn warning"> <i class='fas fa-edit' style='font-size:14px'></i> </button>
							<button name="method" value="delete" class="btn danger"> <i class='fas fa-trash-alt' style='font-size:14px'></i> </button>
						</form>
		             </td>
		             <td> <button class="btn text info" onclick="gestionar_visualizar_ficheros(<%=a.getId()%>)"> Ver/Ocultar ficheros</button> </td>
		         </tr>
		         <tr id="ficheros-actividad-<%=a.getId()%>" class="hide-element">
		         	 <%
				        List<File> files = FileDAO.getFiles(a.getId());
				     %>
		         	 <td colspan="1"> </td>
		             <td colspan="9">
						<% if(files.size() > 0) { %>
							<%
				         		for (File f : files) {
					        %>
					        	<p><%= String.format("%s (ruta %s)", f.getTitle(), f.getRoute())%></p>
					        <%}%>
						<% } else { %>
							<p> No hay ficheros adicionales </p>
						<% } %>
		             </td>
		         </tr>
		         <%}%>
		    </table>
		</div>

		<div class="content">
			<a href="<%= request.getContextPath() %>/usuarios/registrar.jsp">Registrar nuevo usuario</a>
		</div>
	</div>	
</body>
</html>