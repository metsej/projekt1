package org.wwsis.worker.dataAccess.impl;


import java.util.ArrayList;
import java.util.Arrays;
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

	public JadisDataAccess(String hostName) {
		this.hostName = hostName;
		connection = new Jedis(hostName);

	}

	@Override
	public boolean doWorkerExists(Worker p) {

		return connection.exists(userKeyPrefix + p.getLogin());
	}

	@Override
	public Worker loadWorker(Worker p) {
		String userKey = userKeyPrefix + p.getLogin();
		String userLogsKey = userLogsKeyPrefix + p.getLogin();

		p.setImsetName(connection.hget(userKey, "name"));
		p.setLatName(connection.hget(userKey, "last_name"));
		p.setPassword(connection.hget(userKey, "pass"));

		String isLogged = connection.hget(userKey, "isLogged");
		if (isLogged != null) {
			p.setIsLogged(isLogged.equals("true"));
		} else {
			p.setIsLogged(false);
		}
		
		String isBlocked = connection.hget(userKey, "isBlocked");
		if (isBlocked != null) {
			p.setIsBlocked(isBlocked.equals("true"));
		} else {
			p.setIsBlocked(false);
		}
		
		String didLogedForTheFirstTimeStr = connection.hget(userKey, "didLogedForTheFirstTime");
		if (didLogedForTheFirstTimeStr != null) {
			p.setDidLogedForTheFirstTime(didLogedForTheFirstTimeStr.equals("true"));
		} else {
			p.setDidLogedForTheFirstTime(false);
		}

		String strStartTime = connection.hget(userKey, "start");
		if (strStartTime != null) {
			p.setStartTime(strStartTime);
		}
		String strEndTime = connection.hget(userKey, "stop");
		if (strEndTime != null) {
			
			p.setEndTime(strEndTime);
		}
		
		String strNumOfFailedLogingAttempts = connection.hget(userKey, "numOfFailedLogingAttempts");
		if (strNumOfFailedLogingAttempts != null) {
			
			p.setNumOfFailedLogingAttempts(Integer.parseInt(strNumOfFailedLogingAttempts));
		}
		
		List <String> listOfLogs = connection.lrange(userLogsKeyPrefix + p.getLogin(), 0, -1);
		if (listOfLogs != null) {
			p.setListOfLogs(listOfLogs);
		}
		
		return p;
	}

	@Override
	public void saveWorker(Worker p) {
		String userKey = userKeyPrefix + p.getLogin();
		String userLogsKey = userLogsKeyPrefix + p.getLogin();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");

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
			connection.hset(userKey, "start", p.getStartTime());
		}
		
		if (p.getEndTime() != null) {
			connection.hset(userKey, "stop", p.getEndTime());
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
	public List<Worker> getAllWorkers() {
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
		List <String> list = p.getListOfLogs();
		
	
		for ( int i = 0; i < list.size(); i++) {
			connection.rpush(key, list.get(i));
		}
	}
	
	
}
