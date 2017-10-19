package wristonfroze.partyplaylist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Utils {

    /**
     * Get the data
     */
    public static String GetDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();

        return formatter.format(calendar.getTime());
    }

    public static String GetDateFiveDaysBack() {
        DateFormat formatter = new SimpleDateFormat("dd_MMM_yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -5);
        return formatter.format(calendar.getTime());
    }

    /**
     * Get time and date
     *
     * @return {@link String}
     */

    public static String GetTimeandDate() {
        DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy|HH:mm:ss.SSS"); // enter milliseconds
        Calendar calendar = Calendar.getInstance();
        return formatter.format(calendar.getTime());

    }

}
