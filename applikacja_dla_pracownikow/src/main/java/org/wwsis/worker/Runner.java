package org.wwsis.worker;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.view.jframe.MainMenu;

public class Runner {
		
	
	public static void main(String[] args) {
        
		String file = Runner.class.getResource(args[0]).getPath();		
		ConfigurableApplicationContext context = new FileSystemXmlApplicationContext("/"+file);
	    
		AppController controller = context.getBean(AppController.class);
				
		Runnable frameApp = new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu(controller);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		try {
			EventQueue.invokeAndWait(frameApp);
		} catch (InvocationTargetException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		controller.getDao().save();
		context.close();
    }
	
}
  