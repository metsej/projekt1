package org.wwsis.worker.controller.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.controller.WorkTimeManager;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

import java.util.Date;


public class calculateTimeTest {



	
	@Test
	public void test1() {
		
		WorkTimeManager testManager = new WorkTimeManager();
		Worker w = new Worker ();
		List <String> logs = new LinkedList <String>();
		logs.add(0, "2017/05/03 07:00:00");
		logs.add(0, "2017/05/03 08:00:00");
		logs.add(0, "2017/05/03 10:00:00");
		logs.add(0, "2017/05/03 11:00:00");
		
		logs.add(0, "2017/05/04 07:00:00");
		logs.add(0, "2017/05/04 08:00:00");
		logs.add(0, "2017/05/04 10:00:00");
		logs.add(0, "2017/05/04 11:00:00");
		
		logs.add(0, "2017/06/01 07:00:00");
		logs.add(0, "2017/06/01 10:00:00");
		logs.add(0, "2017/06/01 10:05:00");
		logs.add(0, "2017/06/01 14:05:00");
		logs.add(0, "2017/06/01 16:00:00");
		logs.add(0, "2017/06/01 18:00:00");
		

		logs.add(0, "2017/06/02 07:00:00");
		logs.add(0, "2017/06/02 10:00:00");
		logs.add(0, "2017/06/02 10:05:00");
		logs.add(0, "2017/06/02 14:05:00");
		logs.add(0, "2017/06/02 16:00:00");
		logs.add(0, "2017/06/02 18:00:00");
		

		logs.add(0, "2017/06/03 07:00:00");
		logs.add(0, "2017/06/03 10:00:00");
		logs.add(0, "2017/06/03 10:05:00");
		logs.add(0, "2017/06/03 14:05:00");
		logs.add(0, "2017/06/03 16:00:00");
		logs.add(0, "2017/06/03 18:00:00");
		
		logs.add(0, "2017/06/04 07:00:00");
		logs.add(0, "2017/06/04 08:00:00");
		logs.add(0, "2017/06/04 10:00:00");

		w.setListOfLogs(logs);
		List <String> result =  testManager.getMonthReport(w);
		
		for (String s: result) {
			System.out.println(s);
		}
		
		assertEquals(result.get(3), "4 6h 31m");
	}
	
	@Test
	public void test2() {
		
		WorkTimeManager testManager = new WorkTimeManager();
		DataAccess dao = new JadisDataAccess("localhost");
		AppController controller = new AppController(dao);
		Worker loggedWorker  = controller.addAndGetNewWorker("Grzegorz", "Klimek");
		
		
		List <String> logs = new LinkedList <String>();
		logs.add(0, "2017/05/03 07:00:00");
		logs.add(0, "2017/05/03 08:00:00");
		logs.add(0, "2017/05/03 10:00:00");
		logs.add(0, "2017/05/03 11:00:00");
		
		logs.add(0, "2017/05/04 07:00:00");
		logs.add(0, "2017/05/04 08:00:00");
		logs.add(0, "2017/05/04 10:00:00");
		logs.add(0, "2017/05/04 11:00:00");
		
		logs.add(0, "2017/06/01 07:00:00");
		logs.add(0, "2017/06/01 10:00:00");
		logs.add(0, "2017/06/01 10:05:00");
		logs.add(0, "2017/06/01 14:05:00");
		logs.add(0, "2017/06/01 16:00:00");
		logs.add(0, "2017/06/01 18:00:00");
		

		logs.add(0, "2017/06/02 07:00:00");
		logs.add(0, "2017/06/02 10:00:00");
		logs.add(0, "2017/06/02 10:05:00");
		logs.add(0, "2017/06/02 14:05:00");
		logs.add(0, "2017/06/02 16:00:00");
		logs.add(0, "2017/06/02 18:00:00");
		

		logs.add(0, "2017/06/03 07:00:00");
		logs.add(0, "2017/06/03 10:00:00");
		logs.add(0, "2017/06/03 10:05:00");
		logs.add(0, "2017/06/03 14:05:00");
		logs.add(0, "2017/06/03 16:00:00");
		logs.add(0, "2017/06/03 18:00:00");
		
		logs.add(0, "2017/06/04 07:00:00");
		logs.add(0, "2017/06/04 08:00:00");
		logs.add(0, "2017/06/04 10:00:00");
		logs.add(0, "2017/06/04 11:00:00");
		
		
		loggedWorker.setListOfLogs(logs);

		controller.logIn(loggedWorker);
		
		Worker w2 = controller.loadWorker("gklimek");
		List <String> result =  testManager.getMonthReport(w2);
		
		controller.logOut(loggedWorker);
		
		for (String s: result) {
			System.out.println(s);
		}
		
		assertEquals(result.get(3), "4 6h 31m");
	}
	
	

}
