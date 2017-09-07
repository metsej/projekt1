package org.wwsis.worker.view;

public class ViewUtils {

	public static String minutesToHours(int minutes) {
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
}
