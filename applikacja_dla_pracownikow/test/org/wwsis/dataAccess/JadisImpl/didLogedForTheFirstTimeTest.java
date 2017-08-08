package org.wwsis.dataAccess.JadisImpl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class didLogedForTheFirstTimeTest {

	private String connectionName = "localhost";
	@Test
	public void test() {
		String login = "testLogin";
		Worker w1 = Worker.withLogin(login);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		assertEquals(w2.getDidLogedForTheFirstTime(), false);
		
	}
	
	@Test
	public void test2() {
		String login = "testLogin2";
		Worker w1 = Worker.withLogin(login);
		w1.setDidLogedForTheFirstTime(true);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		assertEquals(w2.getDidLogedForTheFirstTime(), true);
		
	}
	
	@Test
	public void test3() {
		String login = "testLogin3";
		Worker w1 = Worker.withLogin(login);
		w1.setDidLogedForTheFirstTime(true);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		w2.setDidLogedForTheFirstTime(false);
		dao.saveWorker(w2);
		Worker w3 = dao.loadWorker(w2);
		assertEquals(w3.getDidLogedForTheFirstTime(), false);
		
	}
	


}
