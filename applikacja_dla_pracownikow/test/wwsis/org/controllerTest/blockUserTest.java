package wwsis.org.controllerTest;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class blockUserTest {
	
	private String connectionName = "localhost";

	@Test
	public void test() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		testController.setDao(dao);
		Worker w1 = testController.addAndGetNewWorker("test", "User");
		dao.setExpireTimeForWorker(w1, 2);
		testController.blockUser(w1);
		w1 = testController.loadWorker(w1.getLogin());
		assertEquals(w1.getIsBlocked(), true);
	}
	
	@Test
	public void test1() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		testController.setDao(dao);
		Worker w1 = testController.addAndGetNewWorker("test1", "User");
		dao.setExpireTimeForWorker(w1, 2);
		testController.blockUser(w1);
		testController.unBlockUser(w1);
		w1 = testController.loadWorker(w1.getLogin());
		assertEquals(w1.getIsBlocked(), false);
	}

}
