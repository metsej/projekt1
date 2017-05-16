package org.wwsis.worker;

import java.awt.EventQueue;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;
import org.wwsis.worker.view.MainMenu;

public class Runner {
	
	public static void main(String[] args) {
        
		DataAccess dao = new JadisDataAccess("localhost");
		AppController controller = new AppController ();
		controller.setDao(dao);
	
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
		
		controller.saveDataBase();
    }
	
    public static void printDBTest (AppController controller) {
		
		for (Worker s : controller.getAllWorkers()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getName());
			System.out.println("naziwsko: " + s.getLastName());
			System.out.println("haslo: " + s.getPassword());
			System.out.println("czas rozpoczecia: " + s.getStartTime());
			System.out.println("czas zakończenia: " + s.getEndTime());
			System.out.println(" ");
		}
		controller.closeDataBase();
	}
	
	public static void populateDBTest (AppController controller) {
		
		controller.eraseDataBase();

		controller.addAndGetNewWorker("Grzegorz", "Klimek");
		controller.addAndGetNewWorker("Grzegorz", "Klimek");
		controller.addAndGetNewWorker("Edwin", "Langaj");
		controller.addAndGetNewWorker("Lukasz", "Ciesielski");
		controller.addAndGetNewWorker("Bartłomiej ", "Świerzyński");
		controller.addAndGetNewWorker("Bartłomiej ", "Świerzyński");


		for (Worker s : controller.getAllWorkers()) {
			System.out.println("login: " + s.getLogin());
			System.out.println("imie: " + s.getName());
			System.out.println("naziwsko: " + s.getLastName());
			System.out.println("haslo: " + s.getPassword());
			System.out.println(" ");
		}
		
		controller.closeDataBase();
		
	}
	
	public static void workerNillTest (AppController controller) {
		Worker np = controller.loadWorker("rkubica");
		
		
		if (np.getStartTime() == null) {
			System.out.println("yes");
		} else {
			System.out.println("no");
		}
		
	}
	
	public static void workerNillTest2 (AppController controller) {
		Worker np = controller.loadWorker("rkubica");
			System.out.println(np.getStartTime());
	}

}
