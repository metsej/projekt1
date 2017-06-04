package org.wwsis.worker.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import org.wwsis.worker.data.Worker;

public class WorkTimeManager {
	
	private  int yearInMin = 525600;
	private  int MonthInMin = 43200;
	private  int DayInMin = 1440;
	private  int HourInMin = 60;
	
	public String calcTodayWorkTime (Worker w) {
		String str_now = getToday();
		String today = str_now.substring(0,10);
		List <String> logsCopy = cloneDates (today, w.getListOfLogs());
		logsCopy.add(0, str_now);
		int minutes = calcWorkTime (logsCopy);
		return minutesToHours (minutes);
	} 
	
	public List <String> getMonthReport (Worker w) {
		List <String> listOfLogCopy = cloneDates ( w.getListOfLogs()) ;
		listOfLogCopy.add(0,getToday());
		int sizeOfCal = getMonthsLenght(getToday());
		List <String> emptyCalendar = new ArrayList();
		for (int i = 0; i < sizeOfCal; i++)  {
			emptyCalendar.add(i, Integer.toString(i + 1) + " 0h 00m");
		}
		List <String> result = getMonthReportHelper (listOfLogCopy , emptyCalendar);
		
		int numOfFirstDayOfMonth = getFirstDayofMonthNum(getToday());
		for (int i = 0; i < numOfFirstDayOfMonth - 1; i++ ) {
			result.add(0," ");
		}
		
		return result;
	}
	
	
	
	private int calcWorkTime( List<String> datesList) {
		
		int result = 0;
		
		if (datesList.size() < 2) {
			return result;
		}
		for (int i = 0; i < datesList.size() - 1; i = i + 2) {
			result += calcTimeinMins (datesList.get(i), datesList.get(i+ 1));
		}
		return result;
	
	}
	
	private int calcTimeinMins (String date1, String date2 ) {
		
		
		return  Math.abs(dateToMinutes (date2) - dateToMinutes (date1) );
	}
	
	private int dateToMinutes (String date) {
		
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));
		int hour = Integer.parseInt(date.substring(11,13));
		int minutes = Integer.parseInt(date.substring(14,16));

		
		
		return year * yearInMin + month * MonthInMin + day * DayInMin + hour * HourInMin + minutes ;
	}
	
	private String getToday () {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String str_now = dtf.format(now);
		return str_now;
		
		
	}
	
	private String minutesToHours (int minutes) {
		int hours;
		int remainingMinutes;
		String hoursStr;
		String remainingMinutesStr;
		
		hours = minutes/60;
		remainingMinutes = minutes - (hours * 60);
		
		
			hoursStr = Integer.toString(hours) + "h";
		
		
		if (remainingMinutes < 10) {
			remainingMinutesStr = "0" + Integer.toString(remainingMinutes) + "m";
		} else {
			remainingMinutesStr = Integer.toString(remainingMinutes) + "m";
		}
		
		return hoursStr + " " + remainingMinutesStr;
		
	}
   
	private List <String> cloneDates (String pariodOfTime, List <String> datesListOrg) {
	   List <String> copy = new LinkedList <String> ();
	   
	   for (int i = 0; i < datesListOrg.size(); i++){
		   if (!datesListOrg.get(i).contains(pariodOfTime)) {
			   break;
		   }
		   copy.add( new String(datesListOrg.get(i)));
	   }
	   return copy;
   }
   
	private List <String> cloneDates (List <String> datesListOrg) {
	   List <String> copy = new LinkedList <String> ();
	   
	   for (String s: datesListOrg) {
		   copy.add(new String(s));
	   }
	   return copy;
   }
	
	private List <String> getMonthReportHelper (List <String> listOfDates, List <String> emptyCalendar ) {

		ListIterator<String> iter = listOfDates.listIterator(0);
		String currentMont = getToday().substring(0,7);
		while (iter.hasNext()) {
			

			
			String currentDay = iter.next().substring(0,10);
			iter.previous();
			
			if (!currentDay.contains(currentMont)) {
				break;
			}
			int dayNum = Integer.parseInt(currentDay.substring(8,10) );
			int currentDayWorkTime = 0;
			
			while (iter.hasNext()) {
				String day1 = iter.next();
				if (!day1.contains(currentDay)) {
					iter.previous();
					break;
				}
				String day2 = iter.next();
				
				
				currentDayWorkTime += calcTimeinMins(day1, day2);
			}
			String dayRaport = Integer.toString(dayNum) + " " +  minutesToHours(currentDayWorkTime);
			emptyCalendar.set(dayNum - 1, dayRaport ); 
		}
		return emptyCalendar;
	
	}
	
	private int getMonthsLenght (String date) {
		int month = Integer.parseInt(date.substring(5,7));
		int year = Integer.parseInt(date.substring(0,4));
		if (month == 1 || month == 3 || month == 5 ||  month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		} else if (month == 2) {
			if (year%4 == 0 ) {
				return 29;
			} else {
				return 28;
			}
		} else {
			return 30;
		}
		
	} 
	
	private int getFirstDayofMonthNum (String date) {
		return getDayOftheWeek (getFirstDayofMonth(date) );
	}
	
	private String getFirstDayofMonth (String date) {
		return date.substring(0,7) + "/01 00:00";
	}
	
	private int getDayOftheWeek (String date) {
		LocalDateTime lDate = getDateFromString (date);
		DayOfWeek dow = lDate.getDayOfWeek();
		return  dow.getValue();
	}
	
	private  LocalDateTime  getDateFromString (String date) {
		
		int year = Integer.parseInt(date.substring(0,4));
		int month = Integer.parseInt(date.substring(5,7));
		int day = Integer.parseInt(date.substring(8,10));
		int hour = Integer.parseInt(date.substring(11,13));
		int minutes = Integer.parseInt(date.substring(14,16));
		
		LocalDateTime result =   LocalDateTime.of( year,  month,  day,  hour,  minutes);
		
		return result;
		
	}
}
