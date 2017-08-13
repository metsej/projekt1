package org.wwsis.worker.dataAccess.impl;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

import redis.clients.jedis.Jedis;

public class JadisDataAccess implements DataAccess {

	public String hostName;

	private Jedis connection;
	
	private String userKeyPrefix = "id:user:";
	
	private String userLogsKeyPrefix = "id:userLogs:";
	
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	
	public JadisDataAccess(String hostName) {
		this.hostName = hostName;
		connection = new Jedis(hostName);

	}

	@Override
	public boolean doWorkerExists(Worker p) {

		return connection.exists(userKeyPrefix + p.getLogin());
	}
	
	
	public Worker loadWorkerWithoutLogs(Worker p) {
		String userKey = userKeyPrefix + p.getLogin();
		
		Worker result = new Worker();
		
		result.setLogin(p.getLogin());
		result.setName(connection.hget(userKey, "name"));
		result.setLatName(connection.hget(userKey, "last_name"));
		result.setPassword(connection.hget(userKey, "pass"));

		String isLogged = connection.hget(userKey, "isLogged");
		if (isLogged != null) {
			result.setIsLogged(isLogged.equals("true"));
		} else {
			result.setIsLogged(false);
		}
		
		String isBlocked = connection.hget(userKey, "isBlocked");
		if (isBlocked != null) {
			result.setIsBlocked(isBlocked.equals("true"));
		} else {
			result.setIsBlocked(false);
		}
		
		String didLogedForTheFirstTimeStr = connection.hget(userKey, "didLogedForTheFirstTime");
		if (didLogedForTheFirstTimeStr != null) {
			result.setDidLogedForTheFirstTime(didLogedForTheFirstTimeStr.equals("true"));
		} else {
			result.setDidLogedForTheFirstTime(false);
		}

		String strStartTime = connection.hget(userKey, "start");
		if (strStartTime != null) {
			result.setStartTime( dateTimeFromString( strStartTime));
		}
		String strEndTime = connection.hget(userKey, "stop");
		if (strEndTime != null) {
			
			result.setEndTime(dateTimeFromString( strEndTime));
		}
		
		String strNumOfFailedLogingAttempts = connection.hget(userKey, "numOfFailedLogingAttempts");
		if (strNumOfFailedLogingAttempts != null) {
			
			result.setNumOfFailedLogingAttempts(Integer.parseInt(strNumOfFailedLogingAttempts));
		}
		return result;
		
	}

	@Override
	public Worker loadWorker(Worker p) {
		
		Worker result = loadWorkerWithoutLogs(p);
		
		List <String> listOfLogs = connection.lrange(userLogsKeyPrefix + result.getLogin(), 0, -1);
		if (listOfLogs != null) {
			List<LocalDateTime> listOfDates = new LinkedList<LocalDateTime>();
			for (String s: listOfLogs) {
				listOfDates.add(dateTimeFromString (s));
			}
			result.setListOfLogs(listOfDates);
		}
		
		return result;
	}

	@Override
	public void saveWorker(Worker p) {
		String userKey = userKeyPrefix + p.getLogin();

		if (p.getName() != null) {
			connection.hset(userKey, "name", p.getName());
		}
		
		if (p.getLastName() != null) {
			connection.hset(userKey, "last_name", p.getLastName());
		}
		
		if (p.getPassword() != null) {
			connection.hset(userKey, "pass", p.getPassword());
		}
		
		if (p.getIsLogged() != null) {
			connection.hset(userKey, "isLogged", Boolean.toString(p.getIsLogged()));
		}
		
		if (p.getIsBlocked() != null) {
			connection.hset(userKey, "isBlocked", Boolean.toString(p.getIsBlocked()));
		}
		
		if (p.getDidLogedForTheFirstTime() != null) {
			connection.hset(userKey, "didLogedForTheFirstTime", Boolean.toString(p.getDidLogedForTheFirstTime()));
		}
		
		if (p.getStartTime() != null) {
			connection.hset(userKey, "start", dateTimeToString(p.getStartTime()));
		}
		
		if (p.getEndTime() != null) {
			connection.hset(userKey, "stop", dateTimeToString( p.getEndTime()));
		}
		
		connection.hset(userKey, "numOfFailedLogingAttempts", Integer.toString (p.getNumOfFailedLogingAttempts()));
		
		if (p.getListOfLogs() != null) {
			saveList(p);
		}
	}
	
	@Override
	public void setExpireTimeForWorker (Worker w, int seconds ) {
		connection.expire(userKeyPrefix  + w.getLogin(), seconds);
		connection.expire(userLogsKeyPrefix + w.getLogin(), seconds);
	}


	@Override
	public List<Worker> getAllWorkersWithoutLogs() {
		Set<String> logins = connection.keys("*" + userKeyPrefix + "*");
		List<Worker> result = new ArrayList<Worker>();
		for (String s : logins) {
			String modelObjectLogin = s.substring(userKeyPrefix.length(), s.length());
			result.add(loadWorker(Worker.withLogin(modelObjectLogin)));
		}
		return result;
	}
	
	@Override
	public void save() {
		connection.save();
	}
	
	@Override
	public void close() {
		connection.close();

	}
	
	@Override
	public void erase() {
		connection.flushAll();
	}
	
	private void saveList (Worker p) {
		String key = userLogsKeyPrefix + p.getLogin();
		connection.del(key);
		List <LocalDateTime> list = p.getListOfLogs();
		
	
		for ( int i = 0; i < list.size(); i++) {
			connection.rpush(key, dateTimeToString(list.get(i)));
		}
	}
	
	String dateTimeToString( LocalDateTime dt){
		String dtStr = dt.format(dateformatter);
		return dtStr;
	}
	
	LocalDateTime  dateTimeFromString( String dateStr){
		LocalDateTime date = LocalDateTime.parse(dateStr, dateformatter);
		return date;
	}
	
	
}
