package org.wwsis.worker.controller;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Pracownik;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class AppControllerTest {

	@Test
	public void populateDB() {

		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);

		pc.dodajPracownika("Grzegorz", "Klimek");
		pc.dodajPracownika("Grzegorz", "Klimek");
		pc.dodajPracownika("Edwin", "Langaj");
		pc.dodajPracownika("Lukasz", "Ciesielski");
		pc.dodajPracownika("Bartłomiej ", "Świerzyński");

		for (Pracownik s : pc.listaPracownikow()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getImie());
			System.out.println("naziwsko: " + s.getNazwisko());
			System.out.println("haslo: " + s.getHaslo());
			System.out.println(" ");
		}

		dao.close();

	}

	@Test
	public void pracownikTest() throws InterruptedException {

		JadisDataAccess dao = new JadisDataAccess("localhost");

		AppController pc = new AppController();
		pc.setDao(dao);

		System.out.print("Test na poprawnosc logowania = ");
		System.out.println(pc.czyPoprawneHasloiLogin("6luhb3r5fl5ijNOPQa7k9ue0", "bświerzyński") == true);

		// przy stworzeniu nowego pracownika
		// zapisywany jest do bazy danych czas zaczecia pracy
		Pracownik nowy_pracownik = Pracownik.zLoginem("gklimek");

		Thread.sleep(1000);

		// przy wylogowaniu zapisywany jest do bazy danych czas koniec pracy
		pc.wyloguj(nowy_pracownik);
		Pracownik np = pc.wczytajPracowniks(nowy_pracownik);

		System.out.println(np.getCzasRozpoczecia());
		System.out.println(np.getCzasZakonczenia());
		dao.close();
	}

}
