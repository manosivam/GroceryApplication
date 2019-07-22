package in.grocery.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Manosivam
 *
 */
public class DateUtil {

	/**
	 * @return current date in yyyy/MM/dd, which can be used to update total sale of the current day.
	 * @see in.grocery.inventory.Inventory#updateSalesPriceForTheDay(String, double)
	 */
	public static String getCurrentDate()
	{
		DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDateTime localDateTime = LocalDateTime.now();
		return dateTimeformatter.format(localDateTime);
	}
	
}
