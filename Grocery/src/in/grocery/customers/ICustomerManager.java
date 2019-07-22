package in.grocery.customers;

/**
 * @author Manosivam
 *
 */
public interface ICustomerManager {

	public Customer createNewCustomer(String name, int age) throws CustomerException;
	public Customer createNewCustomer(String name, int age, String accountName, String accountPassword) throws CustomerException;
	
	public Customer getCustomerFromBackEnd(String name) throws CustomerException;
	public boolean deleteEmployee(String name);
}
