package org.wwsis.worker.controller;

import static org.junit.Assert.assertEquals;

import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.wwsis.worker.Runner;
import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;

public class getRaportsTests {
	final private static String CONFIGURATION = "/PostgresTestContext.xml";
	final private static int currentDay = LocalDate.now().getDayOfMonth();
	final private static Month currentMonth =  LocalDate.now().getMonth();
	final private static  int currentYear = LocalDate.now().getYear();

	
	
	
	private AppController getAppController (String confFile) {
		String file = Runner.class.getResource(confFile).getPath();		
		ConfigurableApplicationContext context = new FileSystemXmlApplicationContext("/"+file);
		return context.getBean(AppController.class);
	}
	
	private List <Session> getSessionsForDayRaport () {
	
		List <Session> list = new ArrayList<>();
		
		for (int i =0; i < 24; i++) {
			
			Random r = new Random();
			int firstMinute = r.nextInt(15);
			LocalDateTime start = LocalDateTime.of(currentYear, currentMonth, currentDay, i, firstMinute);
			LocalDateTime end = LocalDateTime.of(currentYear, currentMonth, currentDay, i, firstMinute + 45);
			Session session = Session.forDates(start, end);
			list.add(session);
		}
		
		return list;
	}
	
	private List<Session> getSessionsForWeekRaport () {
		
		LocalDate toDay = LocalDate.now();
		TemporalField fieldUS = WeekFields.of(Locale.FRANCE).dayOfWeek();
		LocalDate beginOfWeek =  toDay.with(fieldUS, 1);
		int firstDayOfWeek = beginOfWeek.atStartOfDay().getDayOfMonth();
		int initDay = firstDayOfWeek - 3;
		
		List <Session> list = new ArrayList<>();
		
		for (int i =0; i < 15; i++) {
			
			LocalDateTime start = LocalDateTime.of(currentYear, currentMonth, initDay + i, 0, 0);
			LocalDateTime end = LocalDateTime.of(currentYear, currentMonth, initDay + i, i, 30);
			Session session = Session.forDates(start, end);
			list.add(session);
		}
		
		return list;
	}
	
private List<Session> getSessionsForMontRaport () {
		
		List <Session> list = new ArrayList<>();
		
		for (int i =0; i < 31; i++) {
			
			LocalDateTime start = LocalDateTime.of(2017, 1, i + 1,0,0);
			LocalDateTime end = LocalDateTime.of(2017, 1 ,  i + 1, 2,0);
			Session session = Session.forDates(start, end);
			list.add(session);
		}
		
		return list;
	}
	
private List<Session> getSessionsForYearRaport () {
		
		List <Session> list = new ArrayList<>();
		
		for (int i =0; i < 12; i++) {
			
			LocalDateTime start = LocalDateTime.of(2017, 1 + i, 1,0,0);
			LocalDateTime end = LocalDateTime.of(2017, 1 + i,  21, 0,0);
			Session session = Session.forDates(start, end);
			list.add(session);
		}
		
		return list;
	}
	
	private void neutralizeLastSession (List<Session> sessions) {
		Session lastSession = Session.forDates(LocalDateTime.now(), null );
		sessions.add(lastSession);
	}
	
	private <K, V> V getValueFromMap (int index,SortedMap<K, V> map) {
		
		return (V) map.values().toArray()[index];
	}
	
	@Test
	public  void dayRaportTest1 () {
		List <Session> sessions = getSessionsForDayRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalTime, Float> raport = controller.getDayRaport(LocalDate.now(), w);
		
		
		assertEquals( 24, raport.entrySet().size());
		
	}
	
	@Test
	public  void dayRaportTest2 () {
		List <Session> sessions = getSessionsForDayRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalTime, Float> raport = controller.getDayRaport(LocalDate.now(), w);
		
		for (int i = 0; i < 24; i++){
			float acctual = getValueFromMap(i, raport);
			assertEquals( 45f, acctual, 0.001);
		
		}
	}
	
	
	
	@Test
	public  void weekRaportTest1 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		float accual = (float) getValueFromMap(1, raport);
		
		assertEquals( 270f, accual,  0.001f);
		
	}
	
	@Test
	public  void weekRaportTest2 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		float accual = (float) getValueFromMap(2, raport);
		
		assertEquals( 330f, accual,  0.001f);
		
	}
	
	@Test
	public  void weekRaportTest3 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		float accual = (float) getValueFromMap(3, raport);
		
		assertEquals( 390f, accual,  0.001f);
		
	}
	
	@Test
	public  void weekRaportTest4 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		float accual = (float) getValueFromMap(4, raport);
		
		assertEquals( 450f, accual,  0.001f);
		
	}
	
	@Test
	public  void weekRaportTest5 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		float accual = (float) getValueFromMap(5, raport);
		
		assertEquals( 510f, accual,  0.001f);
		
	}
	
	@Test
	public  void weekRaportTest6 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		float accual = (float) getValueFromMap(6, raport);
		
		assertEquals( 570f, accual,  0.001f);
		
	}
	
	@Test
	public  void weekRaportTest7 () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		
		
		assertEquals( 7, raport.entrySet().size());
		
	}
	
	@Test
	public  void yearRaportTest () {
		List <Session> sessions = getSessionsForYearRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<YearMonth, Float> raport = controller.getYearRaport(LocalDate.of(2017, 1, 1), w);
	
		
		for (int i = 0; i < 12; i++) {
			float accual = getValueFromMap(i, raport);
			assertEquals( "I: "+i,20 * 1440f, accual,  0.001f);
			
		}
		
	}
	
	@Test
	public  void monthRaportTest () {
		List <Session> sessions = getSessionsForMontRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getMonthRaport(LocalDate.of(2017, 1, 1), w);
	
		
		for (int i = 0; i < 31; i++) {
			float accual = getValueFromMap(i, raport);
			assertEquals( "I: "+i,120f, accual,  0.001f);
			
		}
		
	}
	
	@Test
	public void printDateTest() {
		System.out.println(Instant.ofEpochSecond(1510702258).atZone(ZoneId.systemDefault()).toLocalDateTime());
		
	}

}
