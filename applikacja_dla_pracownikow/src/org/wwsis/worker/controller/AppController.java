package org.wwsis.worker.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.wwsis.worker.data.Pracownik;
import org.wwsis.worker.dataAccess.DataAccess;

public class AppController {

	private DataAccess dao;

	public void dodajPracownika(String imie, String nazwisko) {
		String login = imie.substring(0, 1) + nazwisko.substring(0, nazwisko.length());
		login = login.toLowerCase();
		login = zwrocDostepnyLogin(login);

		String haslo = generujHaslo();

		Pracownik p = new Pracownik();
		p.setLogin(login);
		p.setHaslo(haslo);
		p.setImie(imie);
		p.setNazwisko(nazwisko);
		getDao().zapiszPracownika(p);

	}

	public Pracownik wczytajPracowniks(Pracownik p) {
		return dao.wczytajPracownika(p);
	}
	

	public List<Pracownik> listaPracownikow() {
		return dao.listaPracownikow();
	}

	public void zapiszPoczatekPracy(String login) {
		Pracownik p = getDao().wczytajPracownika(Pracownik.zLoginem(login));
		p.setCzasRozpoczecia(LocalDateTime.now());
		getDao().zapiszPracownika(p);
	}

	public void wyloguj(Pracownik p) {
		p.setCzasZakonczenia(LocalDateTime.now());
		p.setZalogowany(false);
		getDao().zapiszPracownika(p);
	}

	public boolean czyPoprawneHasloiLogin(String haslo, String login) {
		Pracownik klucz = Pracownik.zLoginem(login);
		if (getDao().czyPracownikIstnieje(klucz)) {
			Pracownik p = getDao().wczytajPracownika(klucz);
			return haslo.equals(p.getHaslo());
		}
		return false;
	}

	public void zmienHaslo(String login) {
		String noweHaslo = generujHaslo();
		Pracownik p1 = new Pracownik();
		p1.setLogin(login);
		Pracownik p2 = getDao().wczytajPracownika(p1);
		p2.setHaslo(noweHaslo);
		getDao().zapiszPracownika(p2);
	}

	public List<Pracownik> zwrocPracownikow() {

		return getDao().listaPracownikow();

	}

	private String zwrocDostepnyLogin(String login) {
		String potLogin = login;

		Pracownik p = new Pracownik();
		p.setLogin(potLogin);
		int numer = 0;

		while (getDao().czyPracownikIstnieje(p)) {
			p.setLogin(login + numer);
			numer++;
		}
		return p.getLogin();
	}

	private static boolean czyHasloPoprawne(String haslo) {
		boolean czyMaMalaLitere = false;
		boolean czyMaDuzaLitere = false;
		boolean czyMaLiczbe = false;

		for (int i = 0; i < haslo.length(); i++) {
			char c = haslo.charAt(i);

			if (Character.isUpperCase(c)) {
				czyMaDuzaLitere = true;
			}

			if (Character.isLowerCase(c)) {
				czyMaMalaLitere = true;
			}

			if (Character.isDigit(c)) {
				czyMaLiczbe = true;
			}

			if (czyMaMalaLitere && czyMaDuzaLitere && czyMaLiczbe) {
				return true;
			}

		}
		return false;
	}

	public boolean czyAdmin(String login, String password) {
		return (login == "admin" && password == "admin");
	}

	public static String generujHaslo() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 15);
		String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String insert = alfabet.substring(randomNum, (randomNum + 4));

		SecureRandom random = new SecureRandom();
		String random_pass = new BigInteger(100, random).toString(32);
		String potencjalneHaslo = (random_pass.substring(0, randomNum) + insert
				+ random_pass.substring(randomNum, random_pass.length()));

		while (!czyHasloPoprawne(potencjalneHaslo)) {
			potencjalneHaslo = generujHaslo();
		}

		return potencjalneHaslo;

	}

	public DataAccess getDao() {
		return dao;
	}

	public void setDao(DataAccess dao) {
		this.dao = dao;
	}

}
