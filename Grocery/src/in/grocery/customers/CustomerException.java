package in.grocery.customers;

import in.grocery.logs.Logger;

/**
 * @author Manosivam
 *
 */
public class CustomerException extends Exception{
	
	Logger logger = Logger.getInstance();
	
	public CustomerException(String errorMessage){
		super(errorMessage);
		logger.logMessage("CustomerException::CustomerException",errorMessage);
	}
	
}
