package org.wwsis.worker.data;

import java.util.Collections;
import java.util.List;

public class Worker {


	private String login;
	private String name;
	private String lastName;
	private String password;
	private Boolean isLogged;
	private Boolean isBlocked;
	private Boolean didLogedForTheFirstTime;
	//TODO delete timeOfStart, timeOfEnd fields
	private int numOfFailedLogingAttempts;
	private List <Session> listOfLogs;


	public static Worker withLogin(String login){
		Worker p = new Worker();
		p.setLogin(login);
		return p;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void  setName(String imie) {
		this.name = imie;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLatName(String nazwisko) {
		this.lastName = nazwisko;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String haslo) {
		this.password = haslo;
	}

	public Boolean getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(Boolean zalogowany) {
		this.isLogged = zalogowany;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public int getNumOfFailedLogingAttempts() {
		return numOfFailedLogingAttempts;
	}

	public void setNumOfFailedLogingAttempts(int numOfFailedLogingAttempts) {
		this.numOfFailedLogingAttempts = numOfFailedLogingAttempts;
	}

	public Boolean getDidLogedForTheFirstTime() {
		return didLogedForTheFirstTime;
	}

	public void setDidLogedForTheFirstTime(Boolean didLogedForTheFirstTime) {
		this.didLogedForTheFirstTime = didLogedForTheFirstTime;
	}

	public List<Session> getListOfLogs() {
		return listOfLogs;
	}

	public void setListOfLogs(List<Session> listOfLogs) {
		this.listOfLogs = listOfLogs;
		Collections.sort(this.listOfLogs);
		
	}
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("[LOGIN:").append(login).append("]\n[NAME:").append(name).append("]\n[SURNAME:");
		sb.append(lastName).append("]\n[PASSWORD:").append(password).append("]\n[ISLOGGED:");
		sb.append(isLogged).append("]\n[ISBLOCKED:").append(isBlocked).append("]\n[NUMOFFAILEDATTEMPTS:");
		sb.append(numOfFailedLogingAttempts).append("]\n[DIDLOGFORTHEFIRSTTIME:").append(didLogedForTheFirstTime);
		sb.append("]\n[FAILEDLOGS:").append(numOfFailedLogingAttempts).append("]\n[LOGS");
		if (listOfLogs != null) {
			sb.append("( ").append(listOfLogs.size()).append(") : ");
			for (Session log: listOfLogs) {
				sb.append(log).append(" | " );
			}
			
		} else {
			sb.append("(0)");
		}
		sb.append("]");
		
		return sb.toString();			
	}


}
