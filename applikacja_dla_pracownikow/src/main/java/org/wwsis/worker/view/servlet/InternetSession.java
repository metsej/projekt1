package org.wwsis.worker.view.servlet;

import java.time.LocalDateTime;

public class InternetSession {
	private String sid;
	private LocalDateTime start;
	private String userLogin;
	
	public LocalDateTime getStart() {
		return start;
	}
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	public String getUserLogin() {
		return userLogin;
	}
	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}

}
