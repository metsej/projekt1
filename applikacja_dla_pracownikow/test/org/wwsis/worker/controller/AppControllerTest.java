package org.wwsis.worker.controller;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Pracownik;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class AppControllerTest {
	
	
	public static void main(String[] args) {
		
		pracownikWczytywanieTest();
	}

	
	public void populateDB() {

		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);
		
		pc.eraseDataBase();

		pc.dodajPracownikadoDBiZwroc("Grzegorz", "Klimek");
		pc.dodajPracownikadoDBiZwroc("Grzegorz", "Klimek");
		pc.dodajPracownikadoDBiZwroc("Edwin", "Langaj");
		pc.dodajPracownikadoDBiZwroc("Lukasz", "Ciesielski");
		pc.dodajPracownikadoDBiZwroc("Bartłomiej ", "Świerzyński");
		pc.dodajPracownikadoDBiZwroc("Bartłomiej ", "Świerzyński");


		for (Pracownik s : pc.listaPracownikow()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getImie());
			System.out.println("naziwsko: " + s.getNazwisko());
			System.out.println("haslo: " + s.getHaslo());
			System.out.println(" ");
		}

		pc.closeDataBase();

	}
	
	
	public boolean isAdminTest() {
		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);
		
		return pc.czyAdmin("admin", "admin");
	}

	
	public void pracownikTest() throws InterruptedException {

		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);

		System.out.print("Test na poprawnosc logowania = ");
		System.out.println(pc.czyPoprawneHasloiLogin("ABCDn8gnaukfhnlgocoaecup", "bświerzyński") == true);

		// przy stworzeniu nowego pracownika
		// zapisywany jest do bazy danych czas zaczecia pracy
		Pracownik nowy_pracownik = pc.wczytajPracownika("gklimek");
	
		pc.zapiszPoczatekPracy(nowy_pracownik);

		Thread.sleep(1000);

		// przy wylogowaniu zapisywany jest do bazy danych czas koniec pracy
		
		pc.wyloguj(nowy_pracownik);

		System.out.println("czas rozpoczecia pracy to: " + nowy_pracownik.getCzasRozpoczecia());
		System.out.println("czas zakonczenia pracy to: " + nowy_pracownik.getCzasZakonczenia());
		dao.close();
	}
	
    public static void pracownikWczytywanieTest() {
    	JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);
		
		Pracownik s = pc.wczytajPracownika("rkubica");
		System.out.println("login: " + s.getLogin());
		System.out.println("imie: " + s.getImie());
		System.out.println("naziwsko: " + s.getNazwisko());
		System.out.println("haslo: " + s.getHaslo());
		
		System.out.println("czas rozpoczęcia to " + s.getCzasRozpoczecia());
		System.out.println("czas zakonczenia to " + s.getCzasZakonczenia());
		
	 }

}

	
