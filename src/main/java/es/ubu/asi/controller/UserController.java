package es.ubu.asi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.asi.dao.UserDAO;
import es.ubu.asi.model.User;

/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Servlet implementation class UserController
 */
@WebServlet(name = "user", urlPatterns = {"/create"})
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("./usuarios/registrar.jsp").forward(request, response);
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
			String profile = request.getParameter("profile");

			if(login.equals("") || password.equals("") || profile.equals("")) {
				msg = "Algún campo vacío";
				error = true;
			} else {
				try {
					User u = new User(login, password, profile);
					UserDAO.add(u);
					request.setAttribute("msg", "Usuario creado correctamente!");
					request.getRequestDispatcher("index.jsp").forward(request, response);
					return;
				} catch (Exception e) {
					error = true;
					msg = "Error creando el usuario en el sistema";
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
		}
		
		return;
	}

}
