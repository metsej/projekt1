package org.wwsis.worker;

import java.awt.EventQueue;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Pracownik;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;
import org.wwsis.worker.view.MainMenu;

public class Runner {
	
	public static void main(String[] args) {
        
		DataAccess dao = new JadisDataAccess("localhost");
		AppController controller = new AppController ();
		controller.setDao(dao);
		
		printDBTest(controller);
		
	
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu(controller);
					window.frame.setVisible(true);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
		
    }
	
	
public static void printDBTest (AppController controller) {
		
		for (Pracownik s : controller.listaPracownikow()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getImie());
			System.out.println("naziwsko: " + s.getNazwisko());
			System.out.println("haslo: " + s.getHaslo());
			System.out.println(" ");
		}
		
		controller.closeDataBase();
		
	}
	
	
	
	
	
	public static void populateDBTest (AppController controller) {
		
		controller.eraseDataBase();

		controller.dodajPracownika("Grzegorz", "Klimek");
		controller.dodajPracownika("Grzegorz", "Klimek");
		controller.dodajPracownika("Edwin", "Langaj");
		controller.dodajPracownika("Lukasz", "Ciesielski");
		controller.dodajPracownika("Bartłomiej ", "Świerzyński");
		controller.dodajPracownika("Bartłomiej ", "Świerzyński");


		for (Pracownik s : controller.listaPracownikow()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getImie());
			System.out.println("naziwsko: " + s.getNazwisko());
			System.out.println("haslo: " + s.getHaslo());
			System.out.println(" ");
		}
		
		controller.closeDataBase();
		
	}
	
	public static void workerNillTest (AppController controller) {
		Pracownik np = controller.wczytajPracownika("rkubica");
		
		
		if (np.getCzasRozpoczecia() == null) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
		
	}
	
	public static void workerNillTest2 (AppController controller) {
		Pracownik np = controller.wczytajPracownika("rkubica");
			System.out.println(np.getCzasRozpoczecia());
	}

}
