package wwsis.org.dataAccess.Jadis;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class blockUsersTests {
	private String connectionName = "localhost";

	@Test
	public void test() {
		String login = "testLogin";
		Worker w1 = Worker.withLogin(login);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		assertEquals(w2.getIsBlocked(), false);
	}
	
	@Test
	public void test2() {
		String login = "testLogin1";
		Worker w1 = Worker.withLogin(login);
		w1.setIsBlocked(true);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		assertEquals(w2.getIsBlocked(), true);
	}
	
	@Test
	public void test3() {
		String login = "testLogin1";
		Worker w1 = Worker.withLogin(login);
		w1.setIsBlocked(true);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		Worker w2 = dao.loadWorker(w1);
		w2.setIsBlocked(false);
		dao.saveWorker(w2);
		Worker w3 = dao.loadWorker(w1);		
		dao.setExpireTimeForWorker(w1, 2);
		assertEquals(w3.getIsBlocked(), false);
	}
	
	@Test
	public void test4() {
		String login = "testLogin1";
		Worker w1 = Worker.withLogin(login);
		w1.setIsBlocked(false);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		Worker w2 = dao.loadWorker(w1);
		w2.setIsBlocked(true);
		dao.saveWorker(w2);
		Worker w3 = dao.loadWorker(w1);		
		dao.setExpireTimeForWorker(w1, 2);
		assertEquals(w3.getIsBlocked(), true);
	}

}
