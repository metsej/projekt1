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
import org.springframework.format.number.money.CurrencyUnitFormatter;
import org.wwsis.worker.Runner;
import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;

public class getRaportsTests {
	final private static String CONFIGURATION = "/PostgresTestContext.xml";
	final private static int CURRENT_DAY = LocalDate.now().getDayOfMonth();
	final private static Month CURRENT_MONTH =  LocalDate.now().getMonth();
	final private static  int CURRENT_YEAR = LocalDate.now().getYear();

	
	
	
	private AppController getAppController (String confFile) {
		String file = Runner.class.getResource(confFile).getPath();		
		ConfigurableApplicationContext context = new FileSystemXmlApplicationContext("/"+file);
		return context.getBean(AppController.class);
	}
	
	private List <Session> getSessionsForPopulateSessionTest() {
		
		List<Session> result = new ArrayList<Session>();
		
		
		LocalDateTime finalDate = LocalDateTime.of(CURRENT_YEAR, CURRENT_MONTH,  CURRENT_DAY - 1, 12,0);
		
		LocalDateTime start = LocalDateTime.of(CURRENT_YEAR, 1, 1,0,0);
		LocalDateTime end = start.plusHours(6);
			
		while (end.isBefore(finalDate)) {
			
			Session session = Session.forDates(start, end);
			result.add(session);
			start = start.plusDays(1);
			end = start.plusHours(6);
			
		}
		
		return result;
	}
	
	private List <Session> getSessionsForDayRaport () {
	
		List <Session> list = new ArrayList<>();
		
		for (int i =0; i < 24; i++) {
			
			Random r = new Random();
			int firstMinute = r.nextInt(15);
			LocalDateTime start = LocalDateTime.of(CURRENT_YEAR, CURRENT_MONTH, CURRENT_DAY - 1, i, firstMinute);
			LocalDateTime end = LocalDateTime.of(CURRENT_YEAR, CURRENT_MONTH, CURRENT_DAY - 1, i, firstMinute + 20);
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

		
		List <Session> list = new ArrayList<>();
		
		for (int i =0; i < 7; i++) {
			
			LocalDateTime start = LocalDateTime.of(CURRENT_YEAR, CURRENT_MONTH, firstDayOfWeek + i, 0, 0);
			LocalDateTime end = LocalDateTime.of(CURRENT_YEAR, CURRENT_MONTH, firstDayOfWeek + i, 10, 00);
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
	
	@SuppressWarnings("unchecked")
	private <K, V> V getValueFromMap (int index,SortedMap<K, V> map) {
		
		return (V) map.values().toArray()[index];
	}
	
	@Test
	public  void dayRaportTest () {
		List <Session> sessions = getSessionsForDayRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalTime, Float> raport = controller.getDayRaport(LocalDate.now().minusDays(1), w);
		assertEquals( 24, raport.entrySet().size());
		for (int i = 0; i < 24; i++){
			float acctual = getValueFromMap(i, raport);
			assertEquals( 45f, acctual, 0.001);
		
		}
		
	}
	
	
	@Test
	public  void weekRaportTest () {
		List <Session> sessions = getSessionsForWeekRaport();
		AppController controller = getAppController(CONFIGURATION);
		Worker w = new Worker();
		w.setListOfLogs(sessions);
		neutralizeLastSession(sessions);
		SortedMap<LocalDate, Float> raport = controller.getWeekRaport(LocalDate.now(), w);
		
		for (int i = 0 ; i <7 ; i++) {
			
			float accual = (float) getValueFromMap(i, raport);
			assertEquals( "I: " + i,600f, accual,  0.001f);
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
	public void printDateTest() {
		System.out.println(Instant.ofEpochSecond(1510702258).atZone(ZoneId.systemDefault()).toLocalDateTime());
		
	}
	
	@Test
	public void populateWorkerSessionsList () {
		AppController controller = getAppController(CONFIGURATION);
		Worker w = controller.loadWorker("gklimek");
		List<Session> sessions = getSessionsForPopulateSessionTest();
		w.setListOfLogs(sessions);
		controller.getDao().saveWorker(w);
	}
	
	

}
