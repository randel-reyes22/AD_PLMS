package sample.Classes.Utility;

import org.omg.CORBA.PUBLIC_MEMBER;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class WeekDates {

    //sets the calendar to current date and time
    public static LinkedList<String> dates = new LinkedList<>();
    private static String monday;
    private static String sunday;

    public static String getMonday() {
        GetDates();
        return monday;
    }

    public static String getSunday() {
        return sunday;
    }


    private static void GetDates() {
        Calendar date = Calendar.getInstance();
        //sets the calendar with starting day of week
        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //printing of first and last day of th week
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        monday = dateformat.format(date.getTime());

        for (int i = 0; i < 6; i++) {
            date.add(Calendar.DATE, 1);
        }
        sunday = dateformat.format(date.getTime());
    }

    public static void GetAllWeekDates(){
        Calendar date = Calendar.getInstance();
        //clear the values
        dates.clear();

        date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //printing of first and last day of th week
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 7; i++) {
            //add to the linklist
            dates.add(dateformat.format(date.getTime()));
            
            date.add(Calendar.DATE, 1);
        }
    }

    //use for report window -> revenue tab
    public static void GetAllDays(String startDate, String endDate) throws ParseException {
        dates.clear();
        DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(startDate));

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endDate));

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            dates.add(dateformat.format(result));
            calendar.add(Calendar.DATE, 1);
        }
    }

    public static String DateNow(){
        //get the current date today
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

}
