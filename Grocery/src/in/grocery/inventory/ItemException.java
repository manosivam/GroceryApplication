package in.grocery.inventory;

import in.grocery.logs.Logger;

/**
 * @author Manosivam
 *
 */
public class ItemException extends Exception {
		
	Logger logger = Logger.getInstance();
	
	public ItemException(String message)
	{
		super(message);
		logger.logMessage(message);
	}
}
