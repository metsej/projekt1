package org.wwsis.worker;

import java.awt.EventQueue;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.view.MainMenu;

public class Runner {
		
	
	public static void main(String[] args) {
        
		String file = Runner.class.getResource(args[0]).getPath();		
		ConfigurableApplicationContext context = new FileSystemXmlApplicationContext("/"+file);
	    
		AppController controller = context.getBean(AppController.class);
				
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
		context.close();
		controller.getDao().save();
    }
	
}
