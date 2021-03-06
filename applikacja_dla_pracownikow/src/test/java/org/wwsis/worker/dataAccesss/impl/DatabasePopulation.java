package org.wwsis.worker.dataAccesss.impl;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.wwsis.worker.Runner;
import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;
import org.wwsis.worker.dataAccess.impl.PostgresDataAccess;

public class DatabasePopulation {
	
	private static final String filePosgresConf = Runner.class.getResource("/PostgresTestContext.xml").getPath();
	private static final String fileJadisConf = Runner.class.getResource("/JadisTestContext.xml").getPath();
	private static final ConfigurableApplicationContext posgresContext = new FileSystemXmlApplicationContext("/"+ filePosgresConf); 
	private static final ConfigurableApplicationContext jadisContext = new FileSystemXmlApplicationContext("/"+ fileJadisConf); 
	
	DataAccess getPostgresDataAccess() {
		return posgresContext.getBean(PostgresDataAccess.class);
	}
	
	DataAccess getJadisDataAccess() {
		return jadisContext.getBean(JadisDataAccess.class);
	}
	
	private  String getLogin(String name, String surrname) {
		return name.toLowerCase().substring(0,1) + surrname.toLowerCase();
	}
	
	void populateDatabase(DataAccess da) {
		
		Random r = new Random();
		String [] names = {"Robert", "Grzegorz", "Marcin", "Janusz", "Kondrat"};
		String [] lastNames =  new String [] {"Kowalski", "Kwiatkowski",  "Majewski", "Malinowski", "Zumba"};
		
		for (int i =0; i < names.length; i++) {
			Worker w1 = new Worker();
			w1.setName(names[i]);
			w1.setLatName(lastNames[i]);
			w1.setLogin(getLogin(names[i], lastNames[i]));
			w1.setPassword(getNewPass());
			w1.setIsLogged(false);
			w1.setIsBlocked(false);
			w1.setDidLogedForTheFirstTime(false);
			int hours = 1 + r.nextInt(47);
			int minutesActive = 10 + r.nextInt(80);
			LocalDateTime startTime = LocalDateTime.now().minusHours(hours);
			LocalDateTime stopTime = startTime.plusMinutes(minutesActive);
			
			// dodaj logi
			
			List <Session> logs = new LinkedList<Session>();
			Session session = Session.forDates(startTime, stopTime);
			logs.add(session);

			int numOfDays = 1 + r.nextInt(9);
			
			LocalDateTime newLogStart = startTime;
			LocalDateTime newLogStop;
			
			for (int j = 1; j < numOfDays; j++) {
				newLogStart = newLogStart.minusDays(1);
				newLogStop = newLogStart.plusMinutes(10 + r.nextInt(40));
				Session s = Session.forDates(newLogStart, newLogStop);
				logs.add(s);
				
			}
			
			w1.setListOfLogs(logs);
			System.out.println(w1.getLogin() + " " + logs.size() +" "+da.doWorkerExists(w1));
			 
			da.saveWorker(w1);
			Worker loadWorker = da.loadWorker(w1);
				
			System.out.println("Real: \n"+w1);
			System.out.println();
			System.out.println("DB: \n"+ loadWorker);
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
			
		}
	}
		
	
	@Test
	public void populateJadisDatabase () {
		populateDatabase(getJadisDataAccess());
	}
	
	@Test 
	public void populatePosgresDatabase() {
		populateDatabase (getPostgresDataAccess());
	}
	
	private  String getNewPass() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
		String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String insert = alfabet.substring(randomNum, (randomNum + 3));

		SecureRandom random = new SecureRandom();
		String random_pass = new BigInteger(30, random).toString(32);
		String potencialPass = (random_pass.substring(0, randomNum) + insert
				+ random_pass.substring(randomNum, random_pass.length()));

		return potencialPass;

	}
	
	
	

}
