package in.grocery.utils;

import java.text.DecimalFormat;

/**
 * @author Manosivam
 *
 */
public class Utils {

	
	public static double roundOffMoney(double toRoundOff)
	{
		return Math.round(Double.parseDouble(new DecimalFormat("0.0").format(toRoundOff)));
	}
}
