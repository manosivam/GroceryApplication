package in.grocery.inventory;

import in.grocery.logs.Logger;

/**
 * @author Manosivam
 *
 */
public class ItemTypeException extends Exception{

	Logger logger = Logger.getInstance();
	
	public ItemTypeException(String message) {
		super(message);
		logger.logMessage(message);
	}
	
}
