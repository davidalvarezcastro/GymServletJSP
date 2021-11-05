package es.ubu.asi.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.asi.dao.UserDAO;

/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Servlet implementation class LoginController
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		boolean error = false;
		String msg = null;

		try {
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			
			if(login.equals("") || password.equals("")) {
				msg = (login.equals("")) ? "Nombre de usuario vacío" : "Contraseña vacía";
				error = true;
			} else {
				try {
					if (!UserDAO.checkLogin(login, password)) {
						msg = "Login incorrecto!";
						error = true;
					}						
				} catch (Exception e) {
					msg = "Fallo de conexión con la base de datos";
					error = true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			error = true;
			msg = "Error en el Controlador";
		}
		
		if (error) {
			request.setAttribute("error", msg);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.getSession(true).setAttribute("user", "david");
			rd.forward(request, response);
		}
	}

}
