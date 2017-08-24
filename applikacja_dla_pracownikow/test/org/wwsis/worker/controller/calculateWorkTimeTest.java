package org.wwsis.worker.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.wwsis.worker.data.Worker;

public class calculateWorkTimeTest {
	
	 
	private static final  AppController controller = new AppController(null);
	
	@Test
	public void test1() {
		Worker w1 = new Worker();
		LocalDateTime startTime = LocalDateTime.now();
		List <LocalDateTime> logs = new LinkedList<LocalDateTime>();
	
		LocalDateTime newLogStart = startTime;
		LocalDateTime newLogStop;
		
		for (int j = 0; j < 9; j++) {
			newLogStart = newLogStart.minusMinutes(j * 10);
			newLogStop = newLogStart.plusSeconds(12);
			logs.add(newLogStop);
			logs.add(newLogStart);
		}
	
		w1.setListOfLogs(logs);
		
		assertEquals("0h 01m", controller.getTodayWorkTime(w1));
	}
	
	@Test
	public void conversionTest(){
		// konwersja typu prostego do odpowiadającego obiektu (niejawna)
		Double d = 1.0;
		double k = d;
		
		Object f = 1.0d + 0.5d;
		Object f2 = 1.0f + 0.5f;
		Object f3 = 1.0d + 0.5f;
		
		Object g = 1 / 0.5;
		
		Object h = new Integer(1) / 5;
		
		System.out.println(f+": "+f.getClass());
		System.out.println(f2+": "+f2.getClass());
		System.out.println(f3+": "+f3.getClass());
		
		System.out.println(g+": "+g.getClass());
		System.out.println(h+": "+h.getClass());
		
		
		
	}

}
