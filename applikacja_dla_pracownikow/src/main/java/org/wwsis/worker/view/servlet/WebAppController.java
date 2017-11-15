package org.wwsis.worker.view.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.wwsis.worker.Runner;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;

import com.google.gson.Gson;

public class WebAppController {
	
	static WebAppController instance;
	private AppController controller;
	private Map<String, InternetSession> sessionsData = new HashMap<String, InternetSession>();
	//web addresses
	public static final String INDEX_PAGE = "/index.jsp";
	// checking if user is logged
	private static final String SESSION_COOKIE_NAME = "SID";
	// loging
	public static final String PASSWORD_INPUT_NAME = "password";
	public static final String USER_INPUT_NAME = "user";
	public static final String WELCOME_PANEL_ADDRESS = "/html/welcame.html";
	public static final String WRONG_LOGIN_OR_PASSWORD_RESP = "wrong login or password";
	public static final String USER_IS_BLOCKED_RESP = "user is blocked";
	public static final String SUCCESSFUL_LOGGING_RESP = "success";
	// work time raports
	public static final String TYPE_OF_REPORT_INPUT_NAME = "type";
	public static final String DAY_REPORT_NAME = "day";
	public static final String WEEK_REPORT_NAME = "week";
	public static final String MONTH_REPORT_NAME = "month";
	public static final String YEAR_REPORT_NAME = "year";
	public static final String TIMESTAMP_INPUT_NAME = "time";
	

	public static WebAppController getInstance() {
		if (instance == null) {
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

	public InternetSession getInternetSession(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(SESSION_COOKIE_NAME) && sessionsData.containsKey(c.getValue())) {
					return sessionsData.get(c.getValue());
				}
			}
		}
		return null;
	}

	private Worker getUser(HttpServletRequest request) {
		String login =  getInternetSession(request).getUserLogin();
		return controller.loadWorker(login);
	}
	
	public boolean isUserLogged(HttpServletRequest request) {
		return getInternetSession(request) != null;
	}


	public boolean checkIfLogged(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isUserLogged(request)) {
			response.sendRedirect(INDEX_PAGE);
			return false;
		} else {
			return true;
		}
	}

	public void handleLoginInput(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String login = request.getParameter(USER_INPUT_NAME);
		String password = request.getParameter(PASSWORD_INPUT_NAME);

		if (login != null) {
			boolean doUserExist = controller.doWorkerExists(Worker.withLogin(login));
			Worker w = doUserExist ? controller.loadWorker(login) : null;
			boolean isLogInputValid = doUserExist && controller.isValidLogNPass(password, login);
			boolean isOnlyPasswordInvalid = doUserExist && (!isLogInputValid) && w.getPassword() != null;
			response.setContentType("text/plain");
			String responseText;

			if (isLogInputValid) {
				Map<String, String[]> parametry = request.getParameterMap();
				response.addCookie(getNewSessionCookie(parametry, UUID.randomUUID().toString()));
				controller.logIn(w);
				responseText = SUCCESSFUL_LOGGING_RESP;
			} else if (isOnlyPasswordInvalid) {
				controller.incrementFailedLoggingAttempt(w);
				w = controller.loadWorker(w.getLogin());
				responseText = w.getIsBlocked() ? USER_IS_BLOCKED_RESP : WRONG_LOGIN_OR_PASSWORD_RESP;
			} else {
				responseText = WRONG_LOGIN_OR_PASSWORD_RESP;
			}

			response.getWriter().write(responseText);
		}
	}

	public AppController getAppController() {
		return controller;

	}
	
	private void sendJSON (HttpServletResponse resp, Object obj) throws IOException {
		Gson gson = new Gson();
		String respJSON = gson.toJson(obj);
		resp.setContentType("application/json");
		resp.getWriter().write(respJSON);
		
	}

	public void getReport(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		Worker w = getUser(req);
		
		String typeStr  = req.getParameter(TYPE_OF_REPORT_INPUT_NAME);
		String timeStampSecsStr = req.getParameter(TIMESTAMP_INPUT_NAME);
		long timeStampSec = Long.parseLong(timeStampSecsStr);
		LocalDateTime date = Instant.ofEpochSecond(timeStampSec).atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		switch (typeStr) {
		case DAY_REPORT_NAME:
			SortedMap<LocalTime,Float> report = controller.getDayRaport(date.toLocalDate(), w);
			sendJSON(resp, report);
			break;
		case WEEK_REPORT_NAME:
			break;
		case MONTH_REPORT_NAME:
			break;
		case YEAR_REPORT_NAME:
			break;
		default:
			break;
		}
	}

	

	private Cookie getNewSessionCookie(Map<String, String[]> parametry, String SID) {
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
