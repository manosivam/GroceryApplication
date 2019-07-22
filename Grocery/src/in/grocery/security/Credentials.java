package in.grocery.security;

/**
 * Can be used to authenticate Employee when they are applying for employee discount. 
 * 
 * @author Manosivam
 *
 */
public class Credentials {
	private String username; 
	private String password; 
	
	public Credentials(String username, String password)
	{
		this.username = username;
		this.password = password; 
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
}
