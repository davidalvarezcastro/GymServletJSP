package es.ubu.asi.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.ubu.asi.dao.ActivityDAO;
import es.ubu.asi.model.Activity;

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
		// TODO Auto-generated method stub
		String action = null, error = "";
		
		try {
			action = request.getParameter("method");
			System.out.println(action);
			switch (action) {
			case "update":
			case "delete":
				String activityID = request.getParameter("activity");
				// se obtiene el objeto de la base de datos
				Activity a = ActivityDAO.getActivity(activityID);
				
				// redirección a la página de gestión o eliminación de actividades (por consiguiente, ficheros asociados)
				if (action.equals("update")) {
					request.setAttribute("activity", a);
					request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
				} else {
					try {
						if (!ActivityDAO.deleteActivity(activityID)) {
							error = "Error eliminando la actividad " + activityID;
						} else {
							// TODO: eliminar ficheros directorio de ficheros
						}
					} catch (Exception e) {
						error = "Error eliminando la actividad " + activityID;
					}

					request.setAttribute("msg", "Actividad eliminada correctamente!");
					request.getRequestDispatcher("home.jsp").forward(request, response);
				}
				break;
			case "add":
			default:
				request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

}
