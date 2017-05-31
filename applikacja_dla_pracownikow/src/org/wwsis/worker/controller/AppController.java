package org.wwsis.worker.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

public class AppController {

	private DataAccess dao;
	private UserAccountsManager usersManager;
	
	public AppController (DataAccess dao) {
		setDao (dao);
		usersManager = new UserAccountsManager();
		usersManager.setDao(dao);
		
	}

	public Worker  addAndGetNewWorker(String name, String lastName) {
		return usersManager.addAndGetNewWorker(name, lastName);
	}

	public Worker loadWorker (String login) {
		return usersManager.loadWorker(login);
	}

	public List<Worker> getAllWorkers() {
		return usersManager.getAllWorkers();
	}

	public void logOut(Worker p) {
		usersManager.logOut(p);
	}
	
	public void logIn (Worker p) {
		usersManager.logIn(p);
	}
	
	public void blockUser (Worker p) {
		usersManager.blockUser(p);
	}
	
	public void unBlockUser (Worker p) {
		usersManager.unBlockUser(p);
	}

	public boolean isValidLogNPass(String password, String login) {
		return usersManager.isValidLogNPass(password, login);
	}

	public void generateNewPass(String login) {
		usersManager.generateNewPass(login);
	}
	
	public void setNewPass (Worker p, String newPass) {
		usersManager.setNewPass(p, newPass);
	}
	
	public boolean isPassValid(String password) {
		return usersManager.isPassValid(password);
	}
	
	public void incrementFailedLoggingAttempt (Worker p) {
		usersManager.incrementFailedLoggingAttempt(p);
	}
	
	
	public List<Worker> gettAllWorker() {

		return usersManager.getAllWorkers();
	}

	public boolean isAdmin(String login, String password) {
		return usersManager.isAdmin(login, password);
	}
	
	public boolean doWorkerExists(Worker p) {
		return usersManager.doWorkerExists(p);
	}

	public DataAccess getDao() {
		return dao;
	}

	public void setDao(DataAccess dao) {
		this.dao = dao;
	}
	
}
