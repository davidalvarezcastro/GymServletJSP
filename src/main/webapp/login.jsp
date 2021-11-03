<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="head.jsp"%>
    <title>Login Page</title>
  </head>
  <body>
  <%@ page import="es.ubu.asi.controller.LoginController" %>
  <%
  	String error_msg = (String)request.getAttribute("error");
  %>

  	<div class="app">
	  	<h1 class="title"> Sistema de Gestión Actividades de UBUGym </h1>
	  	<div class="center" style="max-width: 625px">
	  		<form class=inline-form" method="post" action="login">
		  		<label for="login">Login </label>
		  		<input type="text" name="login" placeholder="login">
		  		<label for="password">Password </label>
		  		<input type="password" name="password" placeholder="password">

		  		<input class="btn info" type="reset" value="Reset">
		        <input class="btn success" type="submit" value="Login">
		        
		        <% if(error_msg != null && !error_msg.equals("")) { %>
				    <p class="error_msg"> <%= error_msg %> <p>
				<% } %>
		  	</form>
	  	</div>
	  	
  	</div>
  </body>
</html>
