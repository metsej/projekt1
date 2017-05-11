package org.wwsis.worker.data;

import java.time.LocalDateTime;

public class Pracownik {

	private String login;
	private String imie;
	private String nazwisko;
	private String haslo;
	private Boolean zalogowany;
	private LocalDateTime czasRozpoczecia;
	private LocalDateTime czasZakonczenia;

	public static Pracownik zLoginem(String login){
		Pracownik p = new Pracownik();
		p.setLogin(login);
		return p;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public LocalDateTime getCzasRozpoczecia() {
		return czasRozpoczecia;
	}

	public void setCzasRozpoczecia(LocalDateTime czasRozpoczecia) {
		this.czasRozpoczecia = czasRozpoczecia;
	}

	public LocalDateTime getCzasZakonczenia() {
		return czasZakonczenia;
	}

	public void setCzasZakonczenia(LocalDateTime czasZakonczenia) {
		this.czasZakonczenia = czasZakonczenia;
	}

	public Boolean getZalogowany() {
		return zalogowany;
	}

	public void setZalogowany(Boolean zalogowany) {
		this.zalogowany = zalogowany;
	}

}
