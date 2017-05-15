package org.wwsis.worker.controller;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
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

		pc.addAndGetNewWorker("Grzegorz", "Klimek");
		pc.addAndGetNewWorker("Grzegorz", "Klimek");
		pc.addAndGetNewWorker("Edwin", "Langaj");
		pc.addAndGetNewWorker("Lukasz", "Ciesielski");
		pc.addAndGetNewWorker("Bartłomiej ", "Świerzyński");
		pc.addAndGetNewWorker("Bartłomiej ", "Świerzyński");


		for (Worker s : pc.getAllWorkers()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getName());
			System.out.println("naziwsko: " + s.getLastName());
			System.out.println("haslo: " + s.getPassword());
			System.out.println(" ");
		}

		pc.closeDataBase();

	}
	
	
	public boolean isAdminTest() {
		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);
		
		return pc.isAdmin("admin", "admin");
	}

	
	public void pracownikTest() throws InterruptedException {

		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);

		System.out.print("Test na poprawnosc logowania = ");
		System.out.println(pc.isValidLogNPass("ABCDn8gnaukfhnlgocoaecup", "bświerzyński") == true);

		// przy stworzeniu nowego pracownika
		// zapisywany jest do bazy danych czas zaczecia pracy
		Worker nowy_pracownik = pc.loadWorker("gklimek");
	
		pc.saveStartTime(nowy_pracownik);

		Thread.sleep(1000);

		// przy wylogowaniu zapisywany jest do bazy danych czas koniec pracy
		
		pc.logOut(nowy_pracownik);

		System.out.println("czas rozpoczecia pracy to: " + nowy_pracownik.getStartTime());
		System.out.println("czas zakonczenia pracy to: " + nowy_pracownik.getEndTime());
		dao.close();
	}
	
    public static void pracownikWczytywanieTest() {
    	JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);
		
		Worker s = pc.loadWorker("rkubica");
		System.out.println("login: " + s.getLogin());
		System.out.println("imie: " + s.getName());
		System.out.println("naziwsko: " + s.getLastName());
		System.out.println("haslo: " + s.getPassword());
		
		System.out.println("czas rozpoczęcia to " + s.getStartTime());
		System.out.println("czas zakonczenia to " + s.getEndTime());
		
	 }

}

	
