package org.wwsis.worker.data;

import java.time.LocalDateTime;
import java.util.List;

public class Worker {


	private String login;
	private String name;
	private String lastName;
	private String password;
	private Boolean isLogged;
	private Boolean isBlocked;
	private Boolean didLogedForTheFirstTime;
	private LocalDateTime timeOfStart;
	private LocalDateTime timeofEnd;
	private int numOfFailedLogingAttempts;
	private List <LocalDateTime> listOfLogs;


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

	public void setImsetName(String imie) {
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

	public LocalDateTime getStartTime() {
		return timeOfStart;
	}

	public void setStartTime(LocalDateTime czasRozpoczecia) {
		this.timeOfStart = czasRozpoczecia;
	}

	public LocalDateTime getEndTime() {
		return timeofEnd;
	}

	public void setEndTime(LocalDateTime czasZakonczenia) {
		this.timeofEnd = czasZakonczenia;
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

	public List<LocalDateTime> getListOfLogs() {
		return listOfLogs;
	}

	public void setListOfLogs(List<LocalDateTime> listOfLogs) {
		this.listOfLogs = listOfLogs;
	}


}
