package org.wwsis.worker.view.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */

	private WebAppController webController;



	
	public LoginServlet() {
		webController = WebAppController.getInstance();
	};

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		webController.ifUserLoggedRedirectToWelcomePg(request, response);
		webController.ifUserNotLoggedRedirectToIndexPg(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		webController.handleLoginInput(request, response);
		
	}

}
