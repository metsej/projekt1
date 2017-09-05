package org.wwsis.worker.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

@Controller 
public class AppController {
	
	private DataAccess dao;
	
	private UserAccountsManager usersManager;
	
	private WorkTimeManager timeManager;
	
	@Autowired
	public AppController(DataAccess dao){
		usersManager = new UserAccountsManager();
		usersManager.setDao(dao);
		timeManager = new WorkTimeManager();
		this.dao = dao;
	}
	
	
	public Worker  addAndGetNewWorker(String name, String lastName) {
		return usersManager.addAndGetNewWorker(name, lastName);
	}

	public Worker loadWorker (String login) {
		return usersManager.loadWorker(login);
	}

	public List<Worker> getAllWorkersWithoutLogs() {
		return usersManager.getAllWorkersWithoutLogs();
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

		return usersManager.getAllWorkersWithoutLogs();
	}

	public boolean isAdmin(String login, String password) {
		return usersManager.isAdmin(login, password);
	}
	
	public float getTodayWorkTime (Worker w) {
		return timeManager.calcTodayWorkTime(w);
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
	
	public SortedMap<LocalDate, Float>  getMonthRaport (Worker w) {
		return timeManager.getMonthReport(w);
	}
	
	public String getCurrentMonthTitlle () {
		return timeManager.getCurrentMonthTitlle();
	}
	
}
