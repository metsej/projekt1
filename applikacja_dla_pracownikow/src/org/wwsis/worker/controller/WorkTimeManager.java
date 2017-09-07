package org.wwsis.worker.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;

public class WorkTimeManager {

	public float calcTodayWorkTime(Worker w) {
		LocalDateTime now = getNow();
		LocalDate today = now.toLocalDate();
		List<Session> logsCopy = seesionsFromDay(today, w.getListOfLogs());
		Session lastSession = w.getListOfLogs().get(w.getListOfLogs().size() -1 );
		if (lastSession.getEndTime() != null) {
			throw new RuntimeException( "Last session shouln't be terminated");
		}
		LocalDateTime openSessionTodayStart = (today.atStartOfDay().isBefore(lastSession.getStartTime()) ) ? today.atStartOfDay() : lastSession.getStartTime() ;
		Session openSession = Session.forDates(openSessionTodayStart, now);
		
		logsCopy.add(openSession);
		return  calcWorkTime(logsCopy);
		 
	}
	
	private SortedMap<LocalDate, Float> getEmptyCalendar(YearMonth ym) {
		
		
		
		SortedMap<LocalDate, Float> emptyCalendar = new TreeMap<LocalDate, Float>();
		LocalDate currentDay = ym.atDay(1);
		while (currentDay.getYear() == ym.getYear() && currentDay.getMonth().equals(ym.getMonth()) ) {
			emptyCalendar.put(currentDay, 0.0f);
			currentDay = currentDay.plusDays(1);
		}
		return emptyCalendar;
	}

	public SortedMap<LocalDate, Float>  getMonthRaport(Worker w) {
		List<Session> listOfLogCopy = cloneSessions(w.getListOfLogs());
		Session currentSession = w.getListOfLogs().get(w.getListOfLogs().size() -1 );
		if (currentSession.getEndTime() != null) {
			throw new RuntimeException( "Current session shouln't be closed");
		}
		LocalDateTime now = getNow();
		Session openSession = Session.forDates(currentSession.getStartTime(), now);
		listOfLogCopy.remove(listOfLogCopy.size() - 1);
		listOfLogCopy.add(openSession);
		
		int year = now.getYear();
		int monthValue = now.getMonth().getValue();
		YearMonth currentMonth = YearMonth.of(year, monthValue);
		SortedMap<LocalDate, Float> calendar = getEmptyCalendar(currentMonth);
		
		LocalDateTime beginingOfMonth = LocalDate.of(year, monthValue, 1).atStartOfDay();
		
		LocalDate lastDayOfMonth =  currentMonth.atEndOfMonth();
		LocalDateTime endOfMonth = lastDayOfMonth.plusDays(1).atStartOfDay();

		for (int i = 0; i < listOfLogCopy.size(); i++) {
			Session sessionInMonth = sessionInMonth(listOfLogCopy.get(i), beginingOfMonth, endOfMonth);
			if (sessionInMonth != null) {
				List<Session> sessionsInDays = sessionInDay(sessionInMonth);
				for (int j = 0; j < sessionsInDays.size(); j++){
					LocalDate day = sessionsInDays.get(j).getStartTime().toLocalDate();
					Float addedDurration =  calcTimeinMins(sessionsInDays.get(j));
					Float currentDuarration = calendar.containsKey(day) ? calendar.get(day) : 0.0f;
					calendar.put(day, currentDuarration + addedDurration);
					
				}
			}
		}
		
		return calendar;
		
	
	}

	public String getCurrentMonthTitlle() {

		LocalDateTime toDay = (getNow());
		Month m = toDay.getMonth();
		int y = toDay.getYear();
		return m.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + Integer.toString(y);
	}

	private float calcWorkTime(List<Session> datesList) {

		float result = 0;

		if (datesList.size() < 2) {
			return result;
		}
		for (int i = 0; i < datesList.size() - 1; i ++) {
			result += calcTimeinMins(datesList.get(i));
		}
		return result;

	}

	private float calcTimeinMins(Session session) {
		return  (float)(Duration.between(session.getStartTime(), session.getEndTime()).toMillis() /  60000d );
	}

	private LocalDateTime getNow() {
		return LocalDateTime.now();
	}


	private List<Session> seesionsFromDay(LocalDate day, List<Session> sessions) {
		List<Session> copy = new ArrayList<Session>();
		
		LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();
		for(Session s: sessions) {
			if(s.getStartTime() == null || s.getEndTime() == null) {
				continue;
			}
			
			if (isSessionDuringDay(s, day)) {
				LocalDateTime beggingAtDay = (day.atStartOfDay().isBefore(s.getStartTime()))  ? s.getStartTime() : day.atStartOfDay() ;
				LocalDateTime endAtDay = (endOfDay.isAfter(s.getEndTime())) ? s.getEndTime() : endOfDay ;
				copy.add(Session.forDates(beggingAtDay, endAtDay));
			}
		}

		return copy;
	}

	private List<Session> cloneSessions(List<Session> sessionList) {
		List<Session> copy = new ArrayList<Session>();

		for (Session sesion : sessionList) {
			copy.add(sesion);
		}
		return copy;
	}
	
	private LocalDateTime min (LocalDateTime first, LocalDateTime second) {
		if ( first.isAfter(second) ){
			return second;
		} else {
			return first; 
		}
	}
	
	private LocalDateTime max (LocalDateTime first, LocalDateTime second) {
		if ( first.isBefore(second)){
			return second;
		} else {
			return first; 
		}
	}
	
	private Session sessionInMonth (Session session, LocalDateTime startOfMonth, LocalDateTime endOfMonth ) {
		
		if ( session.getStartTime().isAfter(endOfMonth) || session.getEndTime().isBefore(startOfMonth)) {
			return null;
		} else {
			LocalDateTime start = max(session.getStartTime(), startOfMonth);
			LocalDateTime stop = min(session.getEndTime(), endOfMonth );
			Session result = Session.forDates(start, stop);
			return result;
		}
	}
	
	private List<Session> sessionInDay (Session session) {
		List<Session> result = new ArrayList<>();
		LocalDateTime startOfSession = session.getStartTime();
		LocalDateTime endOfSession = session.getEndTime();
		LocalDate currnetDay = session.getStartTime().toLocalDate();
		LocalDateTime endOfDaySession;
		LocalDateTime startOfDaySession;
		
		do {
			startOfDaySession = max (currnetDay.atStartOfDay(), startOfSession);  
			endOfDaySession = min (currnetDay.plusDays(1).atStartOfDay(), endOfSession);
			Session daySession = Session.forDates(startOfDaySession, endOfDaySession);
			result.add(daySession);
			currnetDay = currnetDay.plusDays(1); 
		} while (currnetDay.atStartOfDay().isBefore(endOfSession) );
		
		return result;
	}


	private boolean isSessionDuringDay (Session s, LocalDate day){
		LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();
		return s.getStartTime().isBefore(endOfDay) && s.getEndTime().isAfter(day.atStartOfDay());
	}

}
