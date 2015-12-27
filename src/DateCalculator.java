
import java.util.Calendar;

public class DateCalculator {
	
	private static Calendar calendar;

	public static void main(String[] args) {
		calendar = Calendar.getInstance();
		calendar.set(2016,0,1);
		while(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}
		System.out.println(calendar.getTime());
		while(calendar.get(Calendar.YEAR) == 2016){
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			if(calendar.get(Calendar.YEAR)==2016){
				System.out.println(calendar.getTime());
			}
		}
	}

	
	
}
