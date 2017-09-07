package org.wwsis.worker.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

public class UserAccountsManager {
	
	private DataAccess dao;
	
	public Worker  addAndGetNewWorker(String name, String lastName) {
		String login = name.substring(0, 1) + lastName.substring(0, lastName.length());
		login = login.toLowerCase();
		login = getAvalibleLog(login);

		String haslo = getNewPass();

		Worker p = new Worker();
		p.setLogin(login);
		p.setPassword(haslo);
		p.setName(name);
		p.setLatName(lastName);
		getDao().saveWorker(p);
		return p;

	}

	public Worker loadWorker (String login) {
		
		Worker p = new Worker();
		p.setLogin(login);
		
		return dao.loadWorker(p);
	}

	public List<Worker> getAllWorkersWithoutLogs() {
		return dao.getAllWorkersWithoutLogs();
	}

	public void logOut(Worker w) {
		LocalDateTime now = LocalDateTime.now();
		Session currentSession = w.getListOfLogs().get(w.getListOfLogs().size() -1 );
		if (currentSession.getEndTime() != null) {
			throw new RuntimeException( "Current session shouln't be closed");
		}
		Session openSession = Session.forDates(currentSession.getStartTime(), now);
		w.getListOfLogs().remove(w.getListOfLogs().size() - 1);
		w.getListOfLogs().add(openSession);
		w.setIsLogged(false);
		addCurrentLogin(w);
		getDao().saveWorker(w);
	}
	
	public void logIn (Worker p) {
		markMandatoryPassChange(p);
		resetFailedLoggingAttempt(p);
		addCurrentLogin(p);
		dao.saveWorker(p);
	}
	
	public void blockUser (Worker p) {
		p.setIsBlocked(true);
		dao.saveWorker(p);
	}
	
	public void unBlockUser (Worker p) {
		p.setIsBlocked(false);
		resetFailedLoggingAttempt(p);
		dao.saveWorker(p);
	}

	public boolean isValidLogNPass(String password, String login) {
		Worker key = Worker.withLogin(login);
		if (getDao().doWorkerExists(key)) {
			Worker p = getDao().loadWorker(key);
			return password.equals(p.getPassword());
		}
		return false;
	}

	public void generateNewPass(String login) {
		String noweHaslo = getNewPass();
		Worker p1 = new Worker();
		p1.setLogin(login);
		Worker p2 = getDao().loadWorker(p1);
		p2.setPassword(noweHaslo);
		getDao().saveWorker(p2);
	}
	
	public void setNewPass (Worker p, String newPass) {
		p.setPassword(newPass);
		dao.saveWorker(p);
	}
	
	public boolean isPassValid(String password) {
		
		boolean hasLowerCase = false;
		boolean hasUpperCase = false;
		boolean hasNum = false;
		boolean noSpaces = password.contains(" ");
		boolean isLongEnought = password.length() >= 8;

		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);

			if (Character.isUpperCase(c)) {
				hasUpperCase = true;
			}

			if (Character.isLowerCase(c)) {
				hasLowerCase = true;
			}

			if (Character.isDigit(c)) {
				hasNum = true;
			}

			if (hasLowerCase && hasUpperCase && hasNum && !noSpaces && isLongEnought) {
				return true;
			}

		}
		return false;
	}
	
	public void incrementFailedLoggingAttempt (Worker p) {
		int current = p.getNumOfFailedLogingAttempts();
		p.setNumOfFailedLogingAttempts(current + 1);
		dao.saveWorker(p);
		
	}
	
	public List<Worker> gettAllWorker() {

		return getDao().getAllWorkersWithoutLogs();
	}

	public boolean isAdmin(String login, String password) {
		return (login.equals("admin") && password.equals("admin"));
	}
	
	public boolean doWorkerExists(Worker p) {
		return dao.doWorkerExists(p);
	}

	
	private  String getNewPass() {
		int randomNum = ThreadLocalRandom.current().nextInt(0, 3);
		String alfabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String insert = alfabet.substring(randomNum, (randomNum + 3));

		SecureRandom random = new SecureRandom();
		String random_pass = new BigInteger(30, random).toString(32);
		String potencialPass = (random_pass.substring(0, randomNum) + insert
				+ random_pass.substring(randomNum, random_pass.length()));

		while (!isPassValid(potencialPass)) {
			potencialPass = getNewPass();
		}

		return potencialPass;

	}
	
	private String getAvalibleLog(String login) {
		String potLogin = login;

		Worker p = new Worker();
		p.setLogin(potLogin);
		int num = 1;

		while (getDao().doWorkerExists(p)) {
			p.setLogin(login + num);
			num++;
		}
		return p.getLogin();
	}
	
	
	private void addCurrentLogin (Worker p) {
		Session now = Session.forStart(LocalDateTime.now());
		List <Session> logList;
		if (p.getListOfLogs() != null){
			logList = p.getListOfLogs();
		} else {
			logList = new ArrayList <Session>();
		}
		logList.add(now);
		p.setListOfLogs(logList);
	}
	
	private void markMandatoryPassChange (Worker p) {
		p.setDidLogedForTheFirstTime(true);
		
	}
	
	private void resetFailedLoggingAttempt  (Worker p) {
		p.setNumOfFailedLogingAttempts(0);
	}

	public DataAccess getDao() {
		return dao;
	}

	public void setDao(DataAccess dao) {
		this.dao = dao;
	}
	
}
