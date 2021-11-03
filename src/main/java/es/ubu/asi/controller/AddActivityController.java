package es.ubu.asi.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.asi.dao.ActivityDAO;
import es.ubu.asi.model.Activity;

/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Servlet implementation class AddActivityController
 */
@WebServlet(name = "addActivity", urlPatterns = {"/addActivity"})
public class AddActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddActivityController() {
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

            ActivityDAO.add(new Activity(title, description, suggestions, teachers, days, schedule, format.parse(dateStart), format.parse(dateEnd)));
            
            request.setAttribute("msg", "Actividad creada correctamente!");
            // TODO: añadir ficheros
            request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "Error añadiendo la nueva actividad en el sistema");
			request.getRequestDispatcher("actividades/gestionar.jsp").forward(request, response);
		}
	}
}
