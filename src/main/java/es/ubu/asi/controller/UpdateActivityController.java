package es.ubu.asi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

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
 * Servlet implementation class UpdateActivityController
 */
@WebServlet(name = "updateActivity", urlPatterns = {"/updateActivity"})
public class UpdateActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateActivityController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("handleActivity").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String id = request.getParameter("id");
			String title = request.getParameter("title");
            String description = request.getParameter("description");
            String suggestions = request.getParameter("suggestions");
            String teachers = request.getParameter("teachers");
            String days = request.getParameter("days");
            String timeStart = request.getParameter("timeStart");
            String timeEnd = request.getParameter("timeEnd");
            String dateStart = request.getParameter("dateStart");
            String dateEnd = request.getParameter("dateEnd");
            String schedule = String.format("%s-%s", timeStart, timeEnd);

            ActivityDAO.update(new Activity(Long.parseLong(id), title, description, suggestions, teachers, days, schedule, format.parse(dateStart), format.parse(dateEnd)));

            request.setAttribute("msg", "Actividad modificada correctamente!");
            // TODO: modificar ficheros
            request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Error modificando la actividad en el sistema");
			request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		}
	}

}
