package in.grocery.registers;

import in.grocery.backend.Backend;
import in.grocery.logs.Logger;


/**
 * <p>Singleton class only contains utility for Register class</p>
 * 
 * @author Manosivam
 *
 */
public class RegisterHelper implements IRegisterHandler{

	static RegisterHelper registerHelper;
	Logger logger;
	private Backend backend;
	
	private RegisterHelper() 
	{
		logger = Logger.getInstance();
		backend = Backend.getInstance();
	}
	
	public static RegisterHelper getRegisterHelperInstance()
	{
		if(registerHelper == null)
		{
			registerHelper = new RegisterHelper();
		}
		return registerHelper;
	}
	
	/**
	 * Get the next available register from the available register pool.
	 * If Register is not available, it will wait for 1.5s and look for any available registers in the pool if any released.
	 * Registers will be released after completion of a transaction.
	 * It used LRU algorithm using LinkedList to allocate the instance to be used. 
	 * @see #releaseRegisterIntoCommonPool(Register register)
	 */
	public IRegister getAvailableRegisterInstance()
	{
		IRegister register = backend.getAvailableRegisterInstance();
		
		if(register == null)
		{
			try {
				Thread.sleep(1500); // 1.5 seconds and try again. 
			} catch (InterruptedException e) {
				logger.logMessage(e.getMessage());
			}
		}
		while(register==null)
		{
			register = backend.getAvailableRegisterInstance();
		}
		return register;
	}
	
	/**
	 * @param register - register to be released to the common pool. will be called after the transaction is completed. 
	 */
	public void releaseRegisterIntoCommonPool(Register register)
	{
		backend.releaseRegisterIntoCommonPool(register);
	}

}
