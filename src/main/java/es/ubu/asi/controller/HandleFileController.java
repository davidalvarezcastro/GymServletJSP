package es.ubu.asi.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.ubu.asi.dao.ActivityDAO;
import es.ubu.asi.dao.FileDAO;
import es.ubu.asi.model.Activity;
import es.ubu.asi.model.File;
import es.ubu.asi.utils.FileSystem;

/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Servlet implementation class DeleteFileController
 */
@WebServlet("/handleFile")
public class HandleFileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleFileController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = null, error = null;

		try {
			action = (String)request.getAttribute("method");
			if (action == null) {
				action = ""; // desencadena la opción por defecto
			}

			switch (action) {
			case "delete":
				String fileID = (String)request.getAttribute("file");
				
				try {
					// se obtiene el objeto de la base de datos
					File f = FileDAO.getFile(Long.valueOf(fileID));
					// se obtiene la actividad asociada
					Activity a = ActivityDAO.getActivity(String.valueOf(f.getActivity()));
					
					String path = this.getServletContext().getRealPath("") + f.getRoute();
					boolean borrarDataBase = true;
					// se comprueba si existe en el directorio de ficheros
					if (FileSystem.existsFile(path)) {
						if (!FileSystem.removeFile(path)) {
							borrarDataBase = false;
							error  = "Error eliminando el fichero del servidor de ficheros";
						}
					}

					// se elimina la información del fichero
					if (borrarDataBase) {
						try {
							FileDAO.deleteFile(Long.valueOf(fileID));
						} catch (Exception e) {
							// se ha borrado del sistema de ficheros, no se da como malo pero se muestra el error
							error  = "Error eliminando el fichero de la base de datos";
							request.setAttribute("error", error);
						}
						
						List<File> filesActivity = FileDAO.getFiles(a.getId());
		            	request.setAttribute("activity", a); // se vuelve a mostrar la información de la actividad
		            	request.setAttribute("files", filesActivity);

			            request.setAttribute("msg", "Fichero eliminado correctamente del sistema");
			            request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
			            return; // se sale de la función
					}
				} catch (Exception e) {
					error  = "Error recuperando información del fichero";
				}

				request.setAttribute("error", error);
				request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
				break;
			default:
				request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
				break;
			}
		} catch (Exception e) {
			error = "Error en el controlador de fichero";
			request.setAttribute("error", error);
			request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		}
	}

}
