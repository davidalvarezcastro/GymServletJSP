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
 * Servlet implementation class HandleActivityController
 */
@WebServlet("/handleActivity")
public class HandleActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HandleActivityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = null, error = null;

		try {
			action = request.getParameter("method");
			if (action == null) {
				action = ""; // desencadena la opción por defecto
			}

			switch (action) {
			case "update":
			case "delete":
				String activityID = request.getParameter("activity");
				// se obtiene el objeto de la base de datos
				Activity a = ActivityDAO.getActivity(activityID);
				
				// redirección a la página de gestión o eliminación de actividades (por consiguiente, ficheros asociados)
				if (action.equals("update")) {
					List<File> files = FileDAO.getFiles(a.getId());

					request.setAttribute("activity", a);
					request.setAttribute("files", files);
					request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
				} else {
					try {
						if (!ActivityDAO.deleteActivity(activityID)) {
							error = "Error eliminando la actividad " + activityID;
						} else {
							// se eliminan los ficheros del directorio (de la BD es automático)
							String path = FileSystem.generatePathFiles(this.getServletContext().getRealPath(""), a.getId());
							FileSystem.removePath(path);
						}
					} catch (Exception e) {
						error = "Error eliminando la actividad " + activityID;
					}

					if (error == null) {
						request.setAttribute("msg", "Actividad eliminada correctamente!");						
					} else {
						request.setAttribute("error", error);
					}
					request.getRequestDispatcher("home.jsp").forward(request, response);
				}
				break;
			case "add":
			default:
				request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
				break;
			}
		} catch (Exception e) {
			error = "Error en el controlador de actividades";
			request.setAttribute("error", error);
			request.getRequestDispatcher("home.jsp").forward(request, response);
		}
	}
}
