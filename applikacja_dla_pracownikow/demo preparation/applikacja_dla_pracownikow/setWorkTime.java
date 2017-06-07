package applikacja_dla_pracownikow;

import java.util.LinkedList;
import java.util.List;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.controller.WorkTimeManager;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class setWorkTime {
	
	public static void main (String [] args) {
		setWorkTime obj = new setWorkTime();
		obj.setWorkTime();
	} 
	
	public void setWorkTime () {
		WorkTimeManager testManager = new WorkTimeManager();
		DataAccess dao = new JadisDataAccess("localhost");
		AppController controller = new AppController(dao);
		Worker loggedWorker  = controller.addAndGetNewWorker("Grzegorz", "Klimek");
		
		
		List <String> logs = new LinkedList <String>();
		logs.add(0, "2017/05/03 07:00:00");
		logs.add(0, "2017/05/03 08:00:00");
		logs.add(0, "2017/05/03 09:26:00");
		logs.add(0, "2017/05/03 11:12:00");
		
		logs.add(0, "2017/05/04 07:32:00");
		logs.add(0, "2017/05/04 08:00:00");
		logs.add(0, "2017/05/04 10:50:00");
		logs.add(0, "2017/05/04 10:55:00");
		
		logs.add(0, "2017/06/01 07:12:00");
		logs.add(0, "2017/06/01 10:10:00");
		logs.add(0, "2017/06/01 12:05:00");
		logs.add(0, "2017/06/01 14:27:00");
		logs.add(0, "2017/06/01 16:00:00");
		logs.add(0, "2017/06/01 18:10:00");
		

		logs.add(0, "2017/06/02 07:50:00");
		logs.add(0, "2017/06/02 10:16:00");
		logs.add(0, "2017/06/02 11:05:00");
		logs.add(0, "2017/06/02 14:07:00");
		logs.add(0, "2017/06/02 16:00:00");
		logs.add(0, "2017/06/02 18:04:00");
		

		logs.add(0, "2017/06/03 07:32:00");
		logs.add(0, "2017/06/03 10:00:00");
		logs.add(0, "2017/06/03 11:17:00");
		logs.add(0, "2017/06/03 14:05:00");
		logs.add(0, "2017/06/03 16:32:00");
		logs.add(0, "2017/06/03 18:12:00");
		
		logs.add(0, "2017/06/04 07:50:00");
		logs.add(0, "2017/06/04 08:06:00");
		logs.add(0, "2017/06/04 11:22:00");
		logs.add(0, "2017/06/04 12:19:00");
		logs.add(0, "2017/06/04 13:12:00");
		logs.add(0, "2017/06/04 15:19:00");
		
		logs.add(0, "2017/06/05 07:50:00");
		logs.add(0, "2017/06/05 16:06:00");
		
		logs.add(0, "2017/06/06 07:53:00");
		logs.add(0, "2017/06/06 16:04:00");
		
		logs.add(0, "2017/06/07 08:02:00");
		logs.add(0, "2017/06/07 16:09:00");
		
		
		
		
		loggedWorker.setListOfLogs(logs);

		controller.logIn(loggedWorker);
		controller.logOut(loggedWorker);
	}

}
