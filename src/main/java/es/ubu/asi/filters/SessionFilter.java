package es.ubu.asi.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ubu.asi.controller.LoginController;

/**
 * @author david {dac1005@alu.ubu.es}
 *
 * Session Filter
 */
public class SessionFilter implements Filter {

	private ArrayList<String> urlList;
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String url = request.getServletPath();
		boolean allowedRequest = false;

		logger.debug("pagina solicitada: " + url);

		if(urlList.contains(url) || url.contains("/css/") || url.contains("/js/")) {
			allowedRequest = true;
		}
			
		if (!allowedRequest) {
			HttpSession session = request.getSession(true);
			String username = (String) session.getAttribute("user");
			if (username == null) {
				logger.debug("redirigiendo a la página de login");
				response.sendRedirect(request.getContextPath() + "/login.jsp");
			}
		}

		try {
			chain.doFilter(req, res);
		} catch (Exception e) {}  
	}

	public void init(FilterConfig config) throws ServletException {
		String urls = config.getInitParameter("avoid-urls");
		StringTokenizer token = new StringTokenizer(urls, ",");

		urlList = new ArrayList<String>();

		while (token.hasMoreTokens()) {
			urlList.add(token.nextToken());

		}
	}
}