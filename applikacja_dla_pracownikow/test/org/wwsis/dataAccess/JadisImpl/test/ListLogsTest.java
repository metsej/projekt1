package org.wwsis.dataAccess.JadisImpl.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

import org.junit.Test;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class ListLogsTest {

private String connectionName = "localhost";
	
	@Test
	public void test() {
		String login = "testLogin";
		Worker w1 = Worker.withLogin(login);
		LinkedList <String> list = new LinkedList <String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		w1.setListOfLogs(list);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		List <String> loadedList = w2.getListOfLogs();
		assertEquals(loadedList.get(0), "ccc");
		
	}
	
	@Test
	public void test2() {
		String login = "testLogin2";
		Worker w1 = Worker.withLogin(login);
		LinkedList <String> list = new LinkedList <String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		w1.setListOfLogs(list);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		List <String> loadedList = w2.getListOfLogs();
		assertEquals(loadedList.get(1), "ccc");
		
	}
	
	@Test
	public void test3() {
		String login = "testLogin3";
		Worker w1 = Worker.withLogin(login);
		LinkedList <String> list = new LinkedList <String>();
		list.add("aaa");
		list.add("bbb");
		list.add("ccc");
		list.add("ddd");
		w1.setListOfLogs(list);
		JadisDataAccess dao = new JadisDataAccess(connectionName);
		dao.saveWorker(w1);
		dao.setExpireTimeForWorker(w1, 2);
		Worker w2 = dao.loadWorker(w1);
		List <String> loadedList = w2.getListOfLogs();
		assertEquals(loadedList.size(), 4);
		
	}


}
