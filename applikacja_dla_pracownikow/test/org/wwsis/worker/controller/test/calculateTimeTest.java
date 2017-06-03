package org.wwsis.worker.controller.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.wwsis.worker.controller.WorkTimeManager;
import org.wwsis.worker.data.Worker;

public class calculateTimeTest {


	@Test
	public void test2() {
		
		WorkTimeManager testManager = new WorkTimeManager();
		Worker w = new Worker ();
		List <String> logs = new LinkedList <String>();
		logs.add(0, "2017/06/03 07:00:00");
		logs.add(0, "2017/06/03 08:00:00");
		logs.add(0, "2017/06/03 10:00:00");
		logs.add(0, "2017/06/03 11:00:00");
		logs.add(0, "2017/06/03 15:00:00");
		w.setListOfLogs(logs);
		String result = testManager.calcTodayWorkTime(w);
		
		//String result = testManager.minutesToHours(500);
		assertEquals(result, "3h 21m");
	}
	
	/*
	@Test
	public void test3() {
		
		WorkTimeManager testManager = new WorkTimeManager();
		Worker w = new Worker ();
		List <String> logs = new LinkedList <String>();
		logs.add(0, "2017/06/03 07:00:00");
		logs.add(0, "2017/06/03 08:00:00");
		logs.add(0, "2017/06/03 10:00:00");
		logs.add(0, "2017/06/03 11:00:00");
		logs.add(0, "2017/06/03 15:00:00");
		w.setListOfLogs(logs);
		List <String> logsCopy = testManager.cloneDates("2017/06/03", w.getListOfLogs());
		
		//String result = testManager.minutesToHours(500);
		assertEquals(logsCopy.size(), 5);
	}
	
	@Test	
    public void test4() {
		
		WorkTimeManager testManager = new WorkTimeManager();
		Worker w = new Worker ();
		List <String> logs = new LinkedList <String>();
		logs.add(0, "2017/06/03 07:00:00");
		logs.add(0, "2017/06/03 08:00:00");
		logs.add(0, "2017/06/03 10:00:00");
		logs.add(0, "2017/06/03 11:00:00");
		logs.add(0, "2017/06/03 15:00:00");
		logs.add(0, "2017/06/03 16:00:00");
		w.setListOfLogs(logs);
		String result = testManager.minutesToHours(testManager.calcWorkTime(w.getListOfLogs()));
		
		//String result = testManager.minutesToHours(500);
		assertEquals(result, "03:00");
	}
	
	@Test
    public void test5() {
	
	WorkTimeManager testManager = new WorkTimeManager();
	Worker w = new Worker ();
	List <String> logs = new LinkedList <String>();
	logs.add(0, "2017/06/03 07:00:00");
	logs.add(0, "2017/06/03 08:00:00");
	logs.add(0, "2017/06/03 10:00:00");
	logs.add(0, "2017/06/03 11:00:00");
	logs.add(0, "2017/06/03 15:00:00");
	logs.add(0, "2017/06/03 16:00:00");
	w.setListOfLogs(logs);
	int result = testManager.calcWorkTime(logs);
	
	//String result = testManager.minutesToHours(500);
	assertEquals(result, 180);
}
*/
	
	

}
