package org.wwsis.worker.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

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
	
	
	private int calcWorkTime( List<String> datesList) {
		
		int result = 0;
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
	
	private String beginOfDay (String date) {
		
		
		return date.substring(0,10) + " 00:00:00";
			 
	}

	private String beginOfTomorrow (String date) {
	   
	   int year = Integer.parseInt(date.substring(0,4));
	   int month = Integer.parseInt(date.substring(5,7));
	   int day = Integer.parseInt(date.substring(8,10));
	   
	   if (day < 30 ) {
		   day ++;
	   } else if (month < 12) {
		   day = 1;
		   month ++;
	   } else {
		   day = 1;
		   month = 1;
		   year++;
	   }
	   
	   String dayStr;
	   String monthStr;
	   String yearStr = Integer.toString(year);
	   
	   if (day < 10) {
		   dayStr = "0" + Integer.toString(day);
	   } else {
		   dayStr = Integer.toString(day);
	   }
	   
	   if (month < 10) {
		   monthStr = "0" + Integer.toString(month);
	   } else {
		   monthStr = Integer.toString(month);
	   }
	   
	  return yearStr + "/" + monthStr + "/" + dayStr + " 00:00:00";
	   
	   
	   
	   
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
		   copy.add(0, new String(s));
	   }
	   return copy;
   }
}
