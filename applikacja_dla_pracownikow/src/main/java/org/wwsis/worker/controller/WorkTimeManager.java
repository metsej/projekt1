package org.wwsis.worker.controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Function;

import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;

public class WorkTimeManager {

	public float calcTodayWorkTime(Worker w) {
		LocalDateTime now = getNow();
		LocalDate today = now.toLocalDate();
		List<Session> logsCopy = seesionsFromDay(today, w.getListOfLogs());
		Session lastSession = w.getListOfLogs().get(w.getListOfLogs().size() - 1);
		if (lastSession.getEndTime() != null) {
			throw new RuntimeException("Last session shouln't be terminated");
		}
		LocalDateTime openSessionTodayStart = (today.atStartOfDay().isBefore(lastSession.getStartTime()))
				? today.atStartOfDay() : lastSession.getStartTime();
		Session openSession = Session.forDates(openSessionTodayStart, now);

		logsCopy.add(openSession);
		return calcWorkTime(logsCopy);

	}

	private SortedMap<LocalDate, Float> getEmptyCalendar(YearMonth ym) {

		SortedMap<LocalDate, Float> emptyCalendar = new TreeMap<LocalDate, Float>();
		LocalDate currentDay = ym.atDay(1);
		while (currentDay.getYear() == ym.getYear() && currentDay.getMonth().equals(ym.getMonth())) {
			emptyCalendar.put(currentDay, 0.0f);
			currentDay = currentDay.plusDays(1);
		}
		return emptyCalendar;
	}
	
	private List<Session> getSessionsForReport (Worker w) {
		List<Session> listOfLogCopy = cloneSessions(w.getListOfLogs());
		Session currentSession = w.getListOfLogs().get(w.getListOfLogs().size() - 1);
		if (currentSession.getEndTime() != null) {
			throw new RuntimeException("Current session shouln't be closed");
		}
		LocalDateTime now = getNow();
		Session openSession = Session.forDates(currentSession.getStartTime(), now);
		listOfLogCopy.remove(listOfLogCopy.size() - 1);
		listOfLogCopy.add(openSession);
		return listOfLogCopy;
		
	}
	
	private List<LocalDateTime> generatePeriods (LocalDateTime startTime, LocalDateTime endTime, Function<LocalDateTime, LocalDateTime> nextPeriod ) {
		List<LocalDateTime> resultList = new ArrayList<>();
		LocalDateTime iterTime = startTime;
		
		while (!iterTime.isAfter(endTime)){
			resultList.add(iterTime);
			iterTime = nextPeriod.apply(iterTime);
		}
		return resultList;
	}

	public SortedMap<LocalDate, Float> getMonthRaport(Worker w) {
		
		List<Session> sessions = getSessionsForReport(w);
		LocalDateTime now = getNow();

		int year = now.getYear();
		int monthValue = now.getMonth().getValue();
		YearMonth currentMonth = YearMonth.of(year, monthValue);
		//SortedMap<LocalDate, Float> calendar = getEmptyCalendar(currentMonth);
		SortedMap<LocalDate, Float> calendar = new TreeMap<>();
		LocalDateTime beginingOfMonth = LocalDate.of(year, monthValue, 1).atStartOfDay();

		LocalDate lastDayOfMonth = currentMonth.atEndOfMonth();
		LocalDateTime endOfMonth = lastDayOfMonth.plusDays(1).atStartOfDay();
		
		List <LocalDateTime> periods = generatePeriods(beginingOfMonth, endOfMonth, (LocalDateTime date) -> date.plusDays(1) ) ;
		List<Float> listOfDurations = getDurations(sessions, periods);
		SortedMap<LocalDate, Float> resultMap = new TreeMap<>();
		LocalDateTime iterDay = beginingOfMonth;
		int i = 0;
		while (i < listOfDurations.size()) {
			resultMap.put(iterDay.toLocalDate(), listOfDurations.get(i));
			i++;
			iterDay = iterDay.plusDays(1);
		}
	
		return resultMap;

	}
	
	public SortedMap<LocalTime, Float> getDayRaport (LocalDate day, Worker w) {
		List<Session> sessions = getSessionsForReport(w);
		LocalDateTime startOdDay = day.atStartOfDay();
		LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();
		SortedMap<LocalTime, Float> resultList = new TreeMap<>();

		List <LocalDateTime> periods = generatePeriods(startOdDay, endOfDay, (LocalDateTime date) -> date.plusHours(1));
		List<Float> listOfDurations = getDurations(sessions, periods);
		
		LocalTime iter = startOdDay.toLocalTime();
		int i = 0;
		while (i < listOfDurations.size()) {
			resultList.put(iter, listOfDurations.get(i));
			i++;
			iter = iter.plusHours(1);
		}
		
				
		return resultList;
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
		for (int i = 0; i < datesList.size() - 1; i++) {
			result += calcTimeinMins(datesList.get(i));
		}
		return result;

	}

	private float calcTimeinMins(Session session) {
		return (float) (Duration.between(session.getStartTime(), session.getEndTime()).toMillis() / 60000d);
	}

	private LocalDateTime getNow() {
		return LocalDateTime.now();
	}

	private List<Session> seesionsFromDay(LocalDate day, List<Session> sessions) {
		List<Session> copy = new ArrayList<Session>();

		LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();
		for (Session s : sessions) {
			if (s.getStartTime() == null || s.getEndTime() == null) {
				continue;
			}

			if (isSessionDuringDay(s, day)) {
				LocalDateTime beggingAtDay = (day.atStartOfDay().isBefore(s.getStartTime())) ? s.getStartTime()
						: day.atStartOfDay();
				LocalDateTime endAtDay = (endOfDay.isAfter(s.getEndTime())) ? s.getEndTime() : endOfDay;
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

	private LocalDateTime min(LocalDateTime first, LocalDateTime second) {
		if (first.isAfter(second)) {
			return second;
		} else {
			return first;
		}
	}

	private LocalDateTime max(LocalDateTime first, LocalDateTime second) {
		if (first.isBefore(second)) {
			return second;
		} else {
			return first;
		}
	}

	private Session sessionInDuration(Session session, LocalDateTime startOfPeriod, LocalDateTime endOfPeriod) {

		if (session.getStartTime() == null || session.getEndTime() == null || session.getStartTime().isAfter(endOfPeriod)
				|| session.getEndTime().isBefore(startOfPeriod)) {

			return null;
		} else {
			LocalDateTime start = max(session.getStartTime(), startOfPeriod);
			LocalDateTime stop = min(session.getEndTime(), endOfPeriod);
			Session result = Session.forDates(start, stop);
			return result;
		}
	}

	private List <Float>  getDurations (List<Session> sesions, List <LocalDateTime> datesTimes) {
		float [] resultList = new float [datesTimes.size() - 1];
		for (Session s: sesions) {
			for (int i = 0; i < datesTimes.size() - 1; i ++) {
				Session period = sessionInDuration(s, datesTimes.get(i), datesTimes.get(i + 1)); 
				if (period != null) {
					resultList[i] += calcTimeinMins(period);					
				}
			}
			
		}
		List<Float> finaleResult = new ArrayList<Float>();
		for (float f: resultList) {
			finaleResult.add( (Float) f );
		}
		return finaleResult;
	}

	private boolean isSessionDuringDay(Session s, LocalDate day) {
		LocalDateTime endOfDay = day.plusDays(1).atStartOfDay();
		return s.getStartTime().isBefore(endOfDay) && s.getEndTime().isAfter(day.atStartOfDay());
	}

}
