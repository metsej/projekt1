package concreteTest;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class tedNetTest {
	
	private String connectionName = "localhost";

	@Test
	public void test() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController(dao);
		Worker testWorker =testController.loadWorker("tned");
		//dao.setExpireTimeForWorker(testWorker, 2);
		for (int i =0; i < 5; i++) {
			testController.logIn(testWorker);
			testController.logOut(testWorker);
		}
		
		Worker testWorker2 = testController.loadWorker(testWorker.getLogin());
		List <String> testList = testWorker2.getListOfLogs();
		assertEquals(testList.size(), 10);
	}

}
