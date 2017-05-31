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
		
		controller.getDao().save();
    }
	

}
