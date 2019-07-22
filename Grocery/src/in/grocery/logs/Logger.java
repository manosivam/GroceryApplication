package in.grocery.logs;

/**
 * @author Manosivam
 * singleton class
 */
public class Logger{

	private static Logger logger = null;
	
	/**
	 * For an application instance, one logger is good enough. 
	 * Hence having a Singleton class
	 */
	private Logger() {}
	
	public static Logger getInstance()
	{
		if(logger == null)
		{
			logger = new Logger();
		}
		return logger;
	}
	
	/**
	 * @param Message to log into the output medium.
	 * For the simplicity I am logging into console output PrintStream. 
	 * If LogLevel are needed, we can introduce a new constant enum class with Debug, error, verbose types and add overload logMessage with LogLevel. 
	 */
	public void logMessage(String classAndFunctionName, String message)
	{
		System.out.println(message);
	}
	
	public void logMessage(String message)
	{
		System.out.println(message);
	}
	
}
