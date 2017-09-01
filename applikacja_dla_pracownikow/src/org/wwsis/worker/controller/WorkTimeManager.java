package org.wwsis.worker.controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;

public class WorkTimeManager {

	public String calcTodayWorkTime(Worker w) {
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
		float minutes = calcWorkTime(logsCopy);
		return minutesToHours( (int) minutes);
	}

	public List<String> getMonthReport(Worker w) {
		List<Session> listOfLogCopy = cloneSessions(w.getListOfLogs());
		LocalDateTime now = getNow();
		LocalDate today = now.toLocalDate();
		Session lastSession = w.getListOfLogs().get(w.getListOfLogs().size() -1 );
		if (lastSession.getEndTime() != null) {
			throw new RuntimeException( "Last session shouln't be terminated");
		}
		Session openSession = Session.forDates(lastSession.getStartTime(), now);
		listOfLogCopy.add(openSession);
		int daysInMonth = getMonthsLenght(getNow().toLocalDate());
		Map<Integer, Integer> emptyCalendar = new TreeMap<Integer, Integer>();
		for (int i = 1; i <= daysInMonth; i++) {
			emptyCalendar.put(i, 0);
		}
		Map<Integer, Integer> resultCalendar = getMonthReportHelper(listOfLogCopy, emptyCalendar);

		List<String> result = new ArrayList<String>();
		DayOfWeek numOfFirstDayOfMonth = getFirstDayofMonthNum(getNow().toLocalDate());
		for (int i = 0; i < numOfFirstDayOfMonth.ordinal(); i++) {
			result.add(0, " ");
		}
		for (Integer d : resultCalendar.values()) {
			result.add(minutesToHours(d));
		}

		return result;
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

	private String minutesToHours(int minutes) {
		int hours;
		int remainingMinutes;
		String hoursStr;
		String remainingMinutesStr;

		hours = minutes / 60;
		remainingMinutes = minutes - (hours * 60);

		hoursStr = Integer.toString(hours) + "h";

		if (remainingMinutes < 10) {
			remainingMinutesStr = "0" + Integer.toString(remainingMinutes) + "m";
		} else {
			remainingMinutesStr = Integer.toString(remainingMinutes) + "m";
		}

		return hoursStr + " " + remainingMinutesStr;

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
		LocalDate currnetDay = session.getStartTime().minusDays(1).toLocalDate();
		LocalDateTime endOfDaySession;
		LocalDateTime startOfDaySession;
		
		do {
			currnetDay = currnetDay.plusDays(1); 
			startOfDaySession = max (currnetDay.atStartOfDay(), startOfSession);  
			endOfDaySession = min (currnetDay.plusDays(1).atStartOfDay(), endOfSession);
			Session daySession = Session.forDates(startOfDaySession, endOfDaySession);
			result.add(daySession);
			
		} while (currnetDay.plusDays(1).atStartOfDay().isBefore(endOfSession) );
		
		return result;
	}

	private Map<Integer, Integer> getMonthReportHelper(List<Session> allSesions,
			Map<Integer, Integer> emptyCalendar) {

		int index = allSesions.size() - 1;

		LocalDateTime now = getNow();
		int year = now.getYear();
		int monthValue = now.getMonth().getValue();
		YearMonth currentMonth = YearMonth.of(year, monthValue);
		LocalDateTime beginingOfMonth = LocalDate.of(year, monthValue, 1).atStartOfDay();
		
		LocalDate lastDayOfMonth =  currentMonth.atEndOfMonth();
		LocalDateTime endOfMonth = lastDayOfMonth.plusDays(1).atStartOfDay();

		while (index > 0 && (allSesions.get(index).getStartTime().getMonth().equals(currentMonth)
				&& allSesions.get(index).getStartTime().getYear() == year)) {
			Session currentSesion = allSesions.get(index);
			LocalDateTime currentLastLogin = currentSesion.getEndTime();
			// get last possible login in this month

			// if the last login is after last login in this month we calculate
			// time for the last possible login in this month
			LocalDateTime actualLastLogin = (currentLastLogin.getMonth().equals(currentMonth)
					&& currentLastLogin.getYear() == year) ? currentLastLogin : endOfMonth;
			allSesions.get(index).setEndTime(actualLastLogin);

			LocalDate currentDay = currentLastLogin.toLocalDate();
			float currentWorkTime = 0;
			LocalDateTime endOfDay;
			LocalDateTime beggingAtDay;
			LocalDateTime lastLoginAtDay;
			Session sessionDuringCurrentDat;
			while (index > 0 && isSessionDuringDay(currentSesion, currentDay)) {
				currentSesion = allSesions.get(index);
				currentLastLogin = currentSesion.getEndTime();
				currentDay = currentLastLogin.toLocalDate();
				
				endOfDay = currentDay.plusDays(1).atStartOfDay();
				beggingAtDay = (currentDay.atStartOfDay().isBefore(currentSesion.getStartTime()))
						? currentSesion.getStartTime() : currentDay.atStartOfDay();
				lastLoginAtDay = (endOfDay.isAfter(currentSesion.getEndTime())) ? currentSesion.getEndTime() : endOfDay;
				sessionDuringCurrentDat = Session.forDates(beggingAtDay, lastLoginAtDay);
				currentWorkTime += calcTimeinMins(sessionDuringCurrentDat);
				index--;

			}
			emptyCalendar.put(index, (int)currentWorkTime);
		}
		return emptyCalendar;

	}

	private int getMonthsLenght(LocalDate date) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, date.getYear());
		calendar.set(Calendar.MONTH, date.getMonthValue() - 1);
		return calendar.getActualMaximum(Calendar.DATE);
	}

	private DayOfWeek getFirstDayofMonthNum(LocalDate date) {
		return getFirstDayofMonth(date).getDayOfWeek();
	}

	private LocalDate getFirstDayofMonth(LocalDate date) {
		return date.withDayOfMonth(1);
	}
	
	
	private boolean isSessionDuringDay (Session s, LocalDate day){
		LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();
		return s.getStartTime().isBefore(endOfDay) && s.getEndTime().isAfter(day.atStartOfDay());
	}

}
