package org.wwsis.worker.dataAccess;

import java.util.List;

import org.wwsis.worker.data.Worker;

public interface DataAccess {

	public boolean doWorkerExists(Worker p);

	public Worker loadWorker(Worker p);
	
	public Worker loadWorkerWithoutLogs(Worker p);

	public void saveWorker(Worker p);

	public List<Worker> getAllWorkersWithoutLogs();
	
	public void setExpireTimeForWorker(Worker w, int seconds);
	
	public void save();
	
	public void close();
	
	public void erase();

}
