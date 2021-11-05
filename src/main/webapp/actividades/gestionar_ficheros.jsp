<!-- variables declaradas en clase padre-->
<div>
	<div class="flex space-between">
		<label>Ficheros adjuntos: </label>
		<button type="button" class="btn text success" onclick="anhadir_fichero()"> <i class='fas fa-plus'></i> </button> </th>
	</div>
	<div id="new_files" class="file-list">
		<div class="add-file">
			<label for="title_file">Titulo: </label>
			<input type="text" name="title_file">
			<input type="file" name="file""
				accept="application/msword, application/vnd.ms-excel, application/vnd.ms-powerpoint, text/plain, application/pdf, image/*"
			/>
		</div>
	</div>
</div>

<% if(a != null) { %>
    <div>
    	<div class="flex space-between">
    		<label>Ficheros guardados: </label>
	    	<button type="button" class="btn text info" onclick="gestionar_visualizar_ficheros(<%=a.getId()%>)"> Ver/Ocultar ficheros</button>
    	</div>
	    <div id="ficheros-actividad-<%= a.getId() %>" class="file-list menu">
	    	<%
	        	for (File f : files) {
	        %>
	        	<ul class="flex space-between">
	        		<div class="long-text">
	        			<p> <%= String.format("%s (ruta: %s)", f.getTitle(), f.getRoute())%> </p>
	        		</div>
	        		<div>
	        			<button name="delete_file" value="<%= f.getId() %>" class="btn danger text"> <i class='fas fa-trash-alt' style='font-size:14px'></i> </button>
	        		</div>
	        	</ul>
	        <%}%>
	    </div>
    </div>
<% } %>

