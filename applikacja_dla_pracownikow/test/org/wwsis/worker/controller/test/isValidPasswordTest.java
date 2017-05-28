package org.wwsis.worker.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

public class isValidPasswordTest {

	private String connectionName = "localhost";
	
	@Test
	public void test() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		Boolean result = testController.isPassValid("asDf90eDw");
		

		assertEquals(result, true);
	}
	
	@Test
	public void test2() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		Boolean result = testController.isPassValid("dfsgfdgdfhd");
		

		assertEquals(result, false);
	}
	@Test
	public void test3() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		Boolean result = testController.isPassValid("aSdVdsdsD");
		

		assertEquals(result, false);
	}
	
	@Test
	public void test4() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		Boolean result = testController.isPassValid("5sfgbn902ds");
		

		assertEquals(result, false);
	}
	
	@Test
	public void test5() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		Boolean result = testController.isPassValid("4scF");
		

		assertEquals(result, false);
	}
	
	@Test
	public void test6() {
		DataAccess dao = new JadisDataAccess (connectionName);
		AppController testController = new AppController();
		Boolean result = testController.isPassValid("Wxc65cR0xd 09dsa");
		

		assertEquals(result, false);
	}

}
