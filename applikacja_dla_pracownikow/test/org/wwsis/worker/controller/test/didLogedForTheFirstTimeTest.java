package org.wwsis.worker.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class didLogedForTheFirstTimeTest {

	private String connectionName = "localhost";

	@Test
	public void test() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		testController.setDao(dao);
		Worker w1 = testController.addAndGetNewWorker("test1", "User");
		dao.setExpireTimeForWorker(w1, 2);
		testController.markMandatoryPassChange(w1);
		w1 = testController.loadWorker(w1.getLogin());
		assertEquals(w1.getDidLogedForTheFirstTime(), true);
	}
	
	@Test
	public void test2() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		testController.setDao(dao);
		Worker w1 = testController.addAndGetNewWorker("test2", "User");
		dao.setExpireTimeForWorker(w1, 2);

		w1 = testController.loadWorker(w1.getLogin());
		assertEquals(w1.getDidLogedForTheFirstTime(), false);
	}
	
	

}
