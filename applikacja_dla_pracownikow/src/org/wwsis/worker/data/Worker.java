package org.wwsis.worker.data;


public class Worker {

	private String login;
	private String name;
	private String lastName;
	private String password;
	private Boolean isLogged;
	private Boolean isBlocked;
	private String timeOfStart;
	private String timeofEnd;


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

	public String getStartTime() {
		return timeOfStart;
	}

	public void setStartTime(String czasRozpoczecia) {
		this.timeOfStart = czasRozpoczecia;
	}

	public String getEndTime() {
		return timeofEnd;
	}

	public void setEndTime(String czasZakonczenia) {
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

}
