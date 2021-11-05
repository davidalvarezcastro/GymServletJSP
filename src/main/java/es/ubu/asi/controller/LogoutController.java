package es.ubu.asi.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Servlet implementation class LogoutController
 */
@WebServlet(name = "logout", urlPatterns = {"/logout"})
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutController() {
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
		response.setContentType("text/html;charset=UTF-8");

		try {
			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("user");
			
			if (!session.isNew() && username != null) {
				session.invalidate();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

}
