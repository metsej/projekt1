package org.wwsis.worker.controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.wwsis.worker.data.Worker;

public class WorkTimeManager {

	public String calcTodayWorkTime(Worker w) {
		LocalDateTime now = getNow();
		LocalDate today = now.toLocalDate();

		List<LocalDateTime> logsCopy = cloneDates(today, w.getListOfLogs());
		logsCopy.add(0, now);
		int minutes = calcWorkTime(logsCopy);
		return minutesToHours(minutes);
	}

	public List<String> getMonthReport(Worker w) {
		List<LocalDateTime> listOfLogCopy = cloneDates(w.getListOfLogs());
		listOfLogCopy.add(0, getNow());
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
			result.add(minutesToHours( d));
		}

		return result;
	}

	public String getCurrentMonthTitlle() {

		LocalDateTime toDay = (getNow());
		Month m = toDay.getMonth();
		int y = toDay.getYear();
		return m.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + Integer.toString(y);
	}

	private int calcWorkTime(List<LocalDateTime> datesList) {

		int result = 0;

		if (datesList.size() < 2) {
			return result;
		}
		for (int i = 0; i < datesList.size() - 1; i = i + 2) {
			result += calcTimeinMins(datesList.get(i), datesList.get(i + 1));
		}
		return result;

	}

	private int calcTimeinMins(LocalDateTime date1, LocalDateTime date2) {
		return (int) Duration.between(date1, date2).toMinutes();
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

	private List<LocalDateTime> cloneDates(LocalDate day, List<LocalDateTime> datesListOrg) {
		List<LocalDateTime> copy = new LinkedList<LocalDateTime>();
		for (int i = 0; i < datesListOrg.size(); i++) {
			if (datesListOrg.get(i).toLocalDate().equals(day)) {
				copy.add(datesListOrg.get(i));
			}
		}
		return copy;
	}

	private List<LocalDateTime> cloneDates(List<LocalDateTime> datesListOrg) {
		List<LocalDateTime> copy = new LinkedList<LocalDateTime>();

		for (LocalDateTime s : datesListOrg) {
			copy.add(s);
		}
		return copy;
	}

	private Map<Integer, Integer> getMonthReportHelper(List<LocalDateTime> allLogs, Map<Integer, Integer> emptyCalendar) {

		ListIterator<LocalDateTime> iter = allLogs.listIterator(0);

		int year = getNow().getYear();
		Month currentMonth = getNow().getMonth();

		while (iter.hasNext()) {

			LocalDateTime currentDay = iter.next();
			iter.previous();

			if (!currentDay.getMonth().equals(currentMonth) || currentDay.getYear() != year) {
				break;
			}
			int dayNum = currentDay.getDayOfMonth();
			int currentDayWorkMiutes = 0;

			while (iter.hasNext()) {
				LocalDateTime login = iter.next();
				if (!login.toLocalDate().equals(currentDay)) {
					iter.previous();
					break;
				}
				LocalDateTime logout = iter.next();
				currentDayWorkMiutes += calcTimeinMins(login, logout);
			}
			emptyCalendar.put(dayNum, currentDayWorkMiutes);
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

}
