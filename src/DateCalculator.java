import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DateCalculator {

	private static Calendar calendar;
	private ArrayList<Date> list;

	public ArrayList<Date> getAll(int day, int year) {
		calendar = Calendar.getInstance();
		list = new ArrayList<>();

		if (day > 7 || day < 1) {
			// error
		}
		if (year > calendar.getActualMaximum(Calendar.YEAR)
				|| year < calendar.getActualMinimum(Calendar.YEAR)) {
			// error
		}
		calendar.set(year, 0, 1);
		while (calendar.get(Calendar.DAY_OF_WEEK) != day) {
			System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

		list.add(calendar.getTime());
		while (calendar.get(Calendar.YEAR) == year) {
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			if (calendar.get(Calendar.YEAR) == year) {
				list.add(calendar.getTime());
			}
		}

		return list;
	}
}
