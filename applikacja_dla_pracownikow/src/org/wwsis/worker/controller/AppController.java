package org.wwsis.worker.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

public class AppController {

	private DataAccess dao;

	public Worker  addAndGetNewWorker(String name, String lastName) {
		String login = name.substring(0, 1) + lastName.substring(0, lastName.length());
		login = login.toLowerCase();
		login = getAvalibleLog(login);

		String haslo = getNewPass();

		Worker p = new Worker();
		p.setLogin(login);
		p.setPassword(haslo);
		p.setImsetName(name);
		p.setLatName(lastName);
		getDao().saveWorker(p);
		return p;

	}

	public Worker loadWorker (String login) {
		
		Worker p = new Worker();
		p.setLogin(login);
		
		return loadWorkerHelper(p);
	}

	public List<Worker> getAllWorkers() {
		return dao.getAllWorkers();
	}

	public void saveStartTime(Worker p) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String str_now = dtf.format(now);
		p.setStartTime(str_now);
		getDao().saveWorker(p);
	}

	public void logOut(Worker p) {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String str_now = dtf.format(now);
		p.setEndTime(str_now);
		p.setIsLogged(false);
		getDao().saveWorker(p);
	}
	
	public void blockUser (Worker p) {
		p.setIsBlocked(true);
		dao.saveWorker(p);
	}
	
	public void unBlockUser (Worker p) {
		p.setIsBlocked(false);
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

	public void changePass(String login) {
		String noweHaslo = getNewPass();
		Worker p1 = new Worker();
		p1.setLogin(login);
		Worker p2 = getDao().loadWorker(p1);
		p2.setPassword(noweHaslo);
		getDao().saveWorker(p2);
	}

	public List<Worker> gettAllWorker() {

		return getDao().getAllWorkers();
	}

	public boolean isAdmin(String login, String password) {
		return (login.equals("admin") && password.equals("admin"));
	}
	
	public boolean doWorkerExists(Worker p) {
		return dao.doWorkerExists(p);
	}

	public DataAccess getDao() {
		return dao;
	}

	public void setDao(DataAccess dao) {
		this.dao = dao;
	}
	
	public void saveDataBase() {
		dao.save();
	}
	
	public void closeDataBase(){
		dao.close();
	}
	
	public void eraseDataBase(){
		dao.erase();
	}
	
	private static String getNewPass() {
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
	
	private static boolean isPassValid(String password) {
		boolean hasLowerCase = false;
		boolean hasUpperCase = false;
		boolean hasNum = false;

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

			if (hasLowerCase && hasUpperCase && hasNum) {
				return true;
			}

		}
		return false;
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
	
	private Worker loadWorkerHelper(Worker p) {
		return dao.loadWorker(p);
	}
	

	

}
