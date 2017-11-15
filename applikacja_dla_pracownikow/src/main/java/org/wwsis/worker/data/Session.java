package org.wwsis.worker.data;

import java.time.LocalDateTime;

public class Session implements Comparable <Session> {
	
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	public static Session forDates (LocalDateTime startDate, LocalDateTime endDate) {
		Session result = new Session();
		result.setStartTime(startDate);
		result.setEndTime(endDate);
		return result;
	}
	
	public static Session forStart (LocalDateTime startDate) {
		Session result = new Session();
		result.setStartTime(startDate);
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

	@Override
	public int compareTo(Session o) {
		if (startTime.isBefore(o.startTime)) {
			return -1;
		} else if (o.startTime.isBefore(startTime)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("( ").append(startTime).append(" | ").append(endTime).append(" )");
		return sb.toString();
	}

}
