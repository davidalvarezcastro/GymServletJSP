<%@ page import="es.ubu.asi.controller.LogoutController" %>
<div class="menu nav">
	<ul>
	  <li style="float:left">
		  <a class="active" href="${pageContext.request.contextPath}/home.jsp">
		  	<button class="btn no-margin text white" style='font-size:24px'> <i class='fas fa-home'></i> </button>
		  </a>
	  </li>
	  
	  <li style="float:right">
		  <a class="active" href="${pageContext.request.contextPath}/logout">
		  	<button class="btn no-margin text white" style='font-size:24px'> <i class='fas fa-sign-out-alt'></i> </button>
		  </a>
	  </li>
	  <li style="float:right">
		  <a class="active" href="${pageContext.request.contextPath}/usuarios/registrar.jsp">
		  	<button class="btn no-margin text success" style='font-size:24px'> <i class='fas fa-user-plus'></i> </button>
		  </a>
	  </li>
	</ul>
</div>
