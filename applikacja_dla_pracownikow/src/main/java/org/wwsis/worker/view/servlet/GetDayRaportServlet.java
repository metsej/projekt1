package org.wwsis.worker.view.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetDayRaportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8019394195613780133L;
	
	private WebAppController webController = WebAppController.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(webController.checkIfLogged(req, resp)){
						
		}
	}
	
	

}
