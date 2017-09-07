package org.wwsis.worker.controller;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import org.junit.Test;
import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;

public class calculateWorkTimeTest {
	
	 
	private static final  AppController controller = new AppController(null);
	
	private SortedMap<LocalDate, Float> testCalendar = calculateWorkTimeTest.generateTestCalendar();
	
	private static Month currentMonth =  LocalDate.now().getMonth();
	private static  int currentYear = LocalDate.now().getYear();
	
	
	@Test
	public void conversionTest(){
		// konwersja typu prostego do odpowiadającego obiektu (niejawna)
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

	private static SortedMap<LocalDate, Float> generateTestCalendar () {
		
		
		LocalDateTime startS1 = LocalDateTime.of(2016, Month.NOVEMBER, 12, 12, 0);
		LocalDateTime endS1 = LocalDateTime.of(currentYear, currentMonth, 03, 13, 30);
		
		LocalDateTime startS2 = LocalDateTime.of(currentYear, currentMonth, 03, 12, 0);
		LocalDateTime endS2 = LocalDateTime.of(currentYear, currentMonth, 03, 22, 30);
		
		LocalDateTime startS3 = LocalDateTime.of(currentYear, currentMonth, 03, 21, 30);
		LocalDateTime endS3 = LocalDateTime.of(currentYear,  currentMonth, 04, 11, 30);
		
		LocalDateTime startS4 = LocalDateTime.of(currentYear,  currentMonth, 04, 9, 0);
		LocalDateTime endS4 = LocalDateTime.of(currentYear,  currentMonth, 04, 12, 30);
		
		LocalDateTime startS5 = LocalDateTime.of(currentYear, currentMonth, 04, 14, 0);
		LocalDateTime endS5 = LocalDateTime.of(currentYear,  currentMonth, 05, 1, 30);
		
		LocalDateTime startS6 = LocalDateTime.of(currentYear,  currentMonth, 06, 22, 20);
		LocalDateTime endS6 = LocalDateTime.of(currentYear,  currentMonth, 06, 22, 30);
		
		LocalDateTime startS7 = LocalDateTime.of(currentYear,  currentMonth, 06, 1, 20);
		LocalDateTime endS7 = LocalDateTime.of(currentYear,  currentMonth, 06, 1, 30);
		
		LocalDateTime startS8 = LocalDateTime.of(currentYear,  currentMonth, 06, 2, 20);
		LocalDateTime endS8 = LocalDateTime.of(currentYear,  currentMonth, 06, 2, 40);
		
		LocalDateTime startS9 = LocalDateTime.of(currentYear,  currentMonth, 06, 14, 20);
		LocalDateTime endS9 = LocalDateTime.of(currentYear,  currentMonth, 06, 14, 39);
		
		LocalDateTime startS10 = LocalDateTime.of(currentYear,  currentMonth, 07, 0, 20, 10);
		LocalDateTime endS10 = LocalDateTime.of(currentYear,  currentMonth, 07, 2, 23, 50 );
		
		LocalDateTime startS11 = LocalDateTime.of(currentYear,  currentMonth, 07, 15, 46, 10);
		LocalDateTime endS11 = LocalDateTime.of(currentYear,  currentMonth, 07, 15, 46, 40 );
		
		LocalDateTime startLs = LocalDateTime.of(currentYear,  currentMonth, 28, 23, 59);
		LocalDateTime endLs = LocalDateTime.of(currentYear,  currentMonth, 28, 23, 59);
		

		
		Session s1 = Session.forDates(startS1, endS1);
		Session s2 = Session.forDates(startS2, endS2);
		Session s3 = Session.forDates(startS3, endS3);
		Session s4 = Session.forDates(startS4, endS4);
		Session s5 = Session.forDates(startS5, endS5);
		Session s6 = Session.forDates(startS6, endS6);
		Session s7 = Session.forDates(startS7, endS7);
		Session s8 = Session.forDates(startS8, endS8);
		Session s9 = Session.forDates(startS9, endS9);
		Session s10 = Session.forDates(startS10, endS10);
		Session s11 = Session.forDates(startS11, endS11);
		Session lastSession = Session.forDates(startLs, endLs);


		

		List<Session> sessionList = new ArrayList<Session>(Arrays.asList(s1,s2, s3,s4,s5,s6, s7, s8, s9, s10, s11, lastSession));
		Worker testWorker = new Worker();
		testWorker.setListOfLogs(sessionList);
		lastSession = sessionList.get(sessionList.size() -1);
		lastSession.setStartTime(LocalDateTime.now());
		lastSession.setEndTime(null);
		return   controller.getMonthRaport(testWorker);
		
	}
	
	@Test
	public void splitSessionTest1 () {
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 1));
		assertEquals(1440, result.intValue());
	}
	
	@Test
	public void splitSessionTest2 () {
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 2));
		assertEquals(1440, result.intValue());
	}
	
	@Test
	public void splitSessionTest3 () {
		
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 3));
		assertEquals(1590, result.intValue());
	}
	
	@Test
	public void splitSessionTest4 () {
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 4));
		assertEquals(1500, result.intValue());
	}
	
	@Test
	public void splitSessionTest5 () {
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 5));
		assertEquals(90, result.intValue());
	}
	
	@Test
	public void splitSessionTest6 () {
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 6));
		assertEquals(59, result.intValue());
	}
	
	@Test
	public void splitSessionTest7 () {
		Float result = testCalendar.get(LocalDate.of(currentYear, currentMonth, 7));
		assertEquals(124, result.intValue());
	}


}
