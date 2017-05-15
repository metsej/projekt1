package org.wwsis.worker.dataAccess.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

import redis.clients.jedis.Jedis;

public class JadisDataAccess implements DataAccess {

	public String hostName;

	private Jedis connection;

	public JadisDataAccess(String hostName) {
		this.hostName = hostName;
		connection = new Jedis(hostName);

	}

	@Override
	public boolean doWorkerExists(Worker p) {

		return connection.exists(p.getLogin());
	}

	@Override
	public Worker loadWorker(Worker p) {
		String key = p.getLogin();

		p.setImsetName(connection.hget(key, "name"));
		p.setLatName(connection.hget(key, "last_name"));
		p.setPassword(connection.hget(key, "pass"));

		String isLogged = connection.hget(key, "czyZalogowany");
		if (isLogged != null) {
			p.setIsLogged(isLogged.equals("true"));
		}

		String strStartTime = connection.hget(key, "start");
		if (strStartTime != null) {
			p.setStartTime(strStartTime);
		}
		String strEndTime = connection.hget(key, "stop");
		if (strEndTime != null) {
			
			p.setEndTime(strEndTime);
		}
		
		return p;
	}

	@Override
	public void saveWorker(Worker p) {
		String key = p.getLogin();
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/ddHH:mm:ss");

		if (p.getName() != null) {
			connection.hset(key, "name", p.getName());
		}
		if (p.getLastName() != null) {
			connection.hset(key, "last_name", p.getLastName());
		}
		if (p.getPassword() != null) {
			connection.hset(key, "pass", p.getPassword());
		}
		if (p.getIsLogged() != null) {
			connection.hset(key, "czyZalogowany", Boolean.toString(p.getIsLogged()));
		}
		if (p.getStartTime() != null) {
			connection.hset(key, "start", p.getStartTime());
		}
		if (p.getEndTime() != null) {
			connection.hset(key, "stop", p.getEndTime());
		}
	}

	@Override
	public List<Worker> getAllWorkers() {
		Set<String> logins = connection.keys("*");
		List<Worker> result = new ArrayList<Worker>();
		for (String s : logins) {
			result.add(loadWorker(Worker.withLogin(s)));
		}
		return result;
	}
	
	@Override
	public void close() {
		connection.close();

	}
	
	@Override
	public void erase() {
		connection.flushAll();
	}

}
