package org.wwsis.worker.controller;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.LinkedList;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;


public class ListLogtest {

private String connectionName = "localhost";
	
	@Test
	public void test() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController(dao);
		Worker testWorker = testController.addAndGetNewWorker("test", "worker");
		dao.setExpireTimeForWorker(testWorker, 2);
		for (int i =0; i < 5; i++) {
			testController.logIn(testWorker);
			testController.logOut(testWorker);
		}
		
		Worker testWorker2 = testController.loadWorker(testWorker.getLogin());
		List <LocalDateTime> testList = testWorker2.getListOfLogs();

		assertEquals(testList.size(), 10);
	}
	
	@Test
	public void test2() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController(dao);
		Worker testWorker = testController.addAndGetNewWorker("test1", "worker");
		dao.setExpireTimeForWorker(testWorker, 2);
		for (int i =0; i < 5; i++) {
			testController.logIn(testWorker);
			testController.logOut(testWorker);
		}
		
		Worker testWorker2 = testController.loadWorker(testWorker.getLogin());
		List <LocalDateTime> testList = testWorker2.getListOfLogs();

		assertEquals(testList.size(), 10);
	}
	
	@Test
	public void test3() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController(dao);
		Worker testWorker = testController.addAndGetNewWorker("test1", "worker");
		dao.setExpireTimeForWorker(testWorker, 2);
		for (int i =0; i < 5; i++) {
			testController.logIn(testWorker);
			testWorker = testController.loadWorker(testWorker.getLogin());
			testController.logOut(testWorker);
		}
		
		Worker testWorker2 = testController.loadWorker(testWorker.getLogin());
		List <LocalDateTime> testList = testWorker2.getListOfLogs();

		assertEquals(testList.size(), 10);
	}
	
	

}
