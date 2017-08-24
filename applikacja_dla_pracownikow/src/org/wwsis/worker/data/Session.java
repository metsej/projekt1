package org.wwsis.worker.data;

import java.time.LocalDateTime;

public class Session {
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	static Session forDates (LocalDateTime startDate, LocalDateTime endDate) {
		Session result = new Session();
		result.setStartTime(startDate);
		result.setEndTime(endDate);
		return result;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

}
