<%@ page import="es.ubu.asi.controller.UserController" %> <%@ page
language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <%@include file="../head.jsp"%>
    <title>Crear cuenta</title>
  </head>
  <body>
    <% String error_msg = (String)request.getAttribute("error"); %>
    <div class="app">
      <h1 class="title">Nuevo usuario</h1>

      <form class="form" method="post" action="../create">
        <label for="login">Login </label>
        <input type="text" name="login" />

        <label for="password">Password </label>
        <input type="password" name="password" />

        <label for="profile">Perfil </label>
        <!-- <input type="text" name="profile"> -->
        <select name="profile">
          <option value="admin">Admin</option>
          <option value="profesor">Profesor</option>
          <option value="user">Usuario</option>
        </select>

        <input class="btn info" type="reset" value="Reset" />
        <input class="btn success" type="submit" value="Registrar" />

        <% if(error_msg != null && !error_msg.equals("")) { %>
        <p class="error_form"><%= error_msg %></p>
        <p><% } %></p>
      </form>

      <a href="<%= request.getContextPath() %>/home.jsp">Volver a Inicio</a>
    </div>
  </body>
</html>
