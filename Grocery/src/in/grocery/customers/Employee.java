package in.grocery.customers;

import in.grocery.discounts.DiscountManager;
import in.grocery.security.Credentials;

/**
 * @author Manosivam
 *
 */
public class Employee extends Customer{

	private int employeeId; 
	private Credentials credentials;
	
	public Employee(String name, int age, String accountName, String accountPassword) throws CustomerException
	{
		super(name,age);
		if(accountName== null || accountPassword == null)
		{
			throw new CustomerException("Employee account name or password is/are missing");
		}
		this.type = CustomerType.EMPLOYEE;
		this.credentials = new Credentials(accountName, accountPassword);
		this.discountPercent = getDiscountPercent();
	}
	
	public void setEmployeeId(int id)
	{
		this.employeeId = id;
	}
	
	public void setCredentials(Credentials cred)
	{
		this.credentials = cred;
	}
	
	public int getEmployeeId()
	{
		return this.employeeId;
	}
	
	public Credentials getCredentials()
	{
		return this.credentials;
	}

	@Override
	public double getDiscountPercent() {
		return DiscountManager.getInstance().getDiscountPercent(this.type);
	}
	
}
