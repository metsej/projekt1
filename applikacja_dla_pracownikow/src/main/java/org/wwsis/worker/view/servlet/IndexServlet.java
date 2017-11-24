package org.wwsis.worker.view.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexClassicServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private WebAppController webController;
       
   
    public IndexServlet() {
        webController = WebAppController.getInstance();
    
    }

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    
    	String pathToHTMLPage = webController.isUserLogged(request) ? WebAppController.WELCOME_PANEL_ADDRESS : WebAppController.INDEX_HTML_PATH;
    	RequestDispatcher view = request.getRequestDispatcher(pathToHTMLPage);
    	view.forward(request, response);
    	
	}

}
