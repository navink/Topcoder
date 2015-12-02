/*
During the week, the cafeteria of my university is open until 2:30 PM. Normally, I will be at the university before this time anyway. Since I am a lazy person, I don't want to cook for myself and prefer eating in the cafeteria. But sometimes I don't have any classes in the morning, so I have to make sure to get to the university before the cafeteria closes.

There are several bus stops within walking distance of my home and each one has a bus connection to the university. For each bus stop, there will be a bus driving to the university every ten minutes, so I just need to remember the number of minutes after a full hour when the first bus departs from each stop, how long it takes to get to the bus stop and how long the bus needs to drive to the university.

Given a int[] offset (the number of minutes after a full hour when the first bus departs), int[] walkingTime (the number of minutes I need to walk to a bus stop) and a int[] drivingTime (the number of minutes the bus needs to drive to the university) you should write a method that returns the latest time when I have to leave my house in order to be at the university at 2:30 PM or before. The ith element of offset, walkingTime and drivingTime refers to the ith bus stop.
*/

package topcoder.bruteforcebacktracking;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cafeteria {
	
	public final String closingTimeStr = "14:30";
	public Calendar cal = Calendar.getInstance();
		 
	public Cafeteria() throws ParseException {
		DateFormat sdf = new SimpleDateFormat("hh:mm");
		Date closingTime;
		closingTime = sdf.parse(closingTimeStr);
		cal.setTime(closingTime);
	}
	
	public String latestTime(int[] offset, int[] walkingTime, int[] drivingTime) {
		Date bestTime = new Date(0, 0, 0);
				
		for(int bus = 0; bus<offset.length; bus++) {						
			Calendar busCal = (Calendar) cal.clone();
			busCal.add(Calendar.MINUTE, drivingTime[bus]*(-1));
						
			if((busCal.getTime().getMinutes()%10) != offset[bus])
			{
				busCal.add(Calendar.MINUTE, -1*(10-(1*offset[bus])));				
			}			
			
			busCal.add(Calendar.MINUTE, walkingTime[bus]*(-1));
			Date latestTime =  busCal.getTime();
			
			if(latestTime.after(bestTime))
				bestTime = latestTime;
		}
		
		DateFormat sdf = new SimpleDateFormat("hh:mm");
		return sdf.format(bestTime);
	}
	
	public static void main(String[] args) throws ParseException {
		Cafeteria cafe = new Cafeteria();
		int[] offset = {0,1,2,3,4,5,6,7,8,9};
		int[] walkingTime = {11,11,11,11,11,11,11,11,11,11};
		int[] drivingTime = {190,190,190,190,190,190,190,190,190,190};
		
		String result = cafe.latestTime(offset, walkingTime, drivingTime);
		System.out.println(result);		
	}
}
