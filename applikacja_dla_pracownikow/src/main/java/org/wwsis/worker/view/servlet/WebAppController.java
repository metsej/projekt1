package org.wwsis.worker.view.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.wwsis.worker.Runner;
import org.wwsis.worker.controller.AppController;

public class WebAppController {
	static WebAppController  instance;
	private AppController controller;
	private Map <String, InternetSession> sessionsData = new HashMap <String, InternetSession> (); 
	private static final  String SESSION_COOKIE_NAME = "SID"; 
	private static final String USER_INPUT_NAME = "user";
	private static final String WELCOME_PANEL_ADDRESS = "/Welcome";
	private static final String INDEX_PAGE = "/index.jsp";
	private static final String PASSWORD_INPUT_NAME = "password";


	
	public static WebAppController getInstance(){
		if(instance == null){
			instance = new WebAppController();
		}
		return instance;
	}

	
	
	public WebAppController() {
		String file = Runner.class.getResource("/PostgresTestContext.xml").getPath();
		ConfigurableApplicationContext context = new FileSystemXmlApplicationContext("/" + file);
		controller = context.getBean(AppController.class);
		context.close();
	}
	
	public InternetSession getCurrentInternetSession (HttpServletRequest request) {
		Cookie [] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(SESSION_COOKIE_NAME) && sessionsData.containsKey(c.getValue()) ) {
					return sessionsData.get(c.getValue());
				}
			}
		} 
		return null;
	} 
	
	public boolean isUserLogged (HttpServletRequest request) {
		return getCurrentInternetSession(request) != null;
	}

	public void ifUserLoggedRedirectToWelcomePg (HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
			if (isUserLogged(request) ) {
				response.sendRedirect(WELCOME_PANEL_ADDRESS);
			}
		}
	
	public void ifUserNotLoggedRedirectToIndexPg (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,  IOException  {
		if (!isUserLogged(request)) {
			response.sendRedirect(INDEX_PAGE);
		}
	}
	
	public void handleLoginInput (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,  IOException  {
		Map<String, String[]> parametry = request.getParameterMap();
		try {
			if (getAppController().isValidLogNPass(parametry.get(PASSWORD_INPUT_NAME)[0], parametry.get(USER_INPUT_NAME)[0])) {
			
	
				response.addCookie(getNewSessionCookie(parametry, UUID.randomUUID().toString()));
				
				response.sendRedirect(WELCOME_PANEL_ADDRESS);
			} else {
				
				ifUserNotLoggedRedirectToIndexPg(request, response);
			}
		} catch (Exception E) {

		}
		
	}
	
	
	public AppController getAppController() {
		return controller;
		
	}
	
	public Cookie getNewSessionCookie (Map<String, String[]> parametry, String SID) {
		InternetSession newSession = new InternetSession();

		newSession.setStart(LocalDateTime.now());
		newSession.setUserLogin(parametry.get(USER_INPUT_NAME)[0]);
		newSession.setSid(SID);
		sessionsData.put(SID, newSession);
		return new Cookie(SESSION_COOKIE_NAME, newSession.getSid());
		
	}
	
	public Map<String, InternetSession> getSessionData() {
		return sessionsData;
	}
	

}
