package in.grocery.customers;

import java.util.Map;

import in.grocery.backend.Backend;
import in.grocery.logs.Logger;

/**
 * <p>
 * 	This is similar to controller code. 
 * 	This will just accept input and call appropriate Handler which will take care of processing and backend call. 
 * <p>
 * 
 * @author Manosivam
 */
public class CustomerManager implements ICustomerManager{
	
	//nameCustomerMap can be cached on demand one by one. But for simplicity caching every customer here.
	private Map<String, Customer> nameCustomerMap;
	
	private static CustomerManager customerManager;
	
	private Logger logger;
	private Backend backend;
	
	private CustomerManager() {
		logger = Logger.getInstance();
		nameCustomerMap = null;
		backend = Backend.getInstance(); // prepares the connectivity with the backend. 
	}
	
	public static CustomerManager getInstance()
	{
		if(customerManager == null)
		{
			customerManager = new CustomerManager();
		}
		return customerManager;
	}
	
	private Customer sendCreateCustomerRequestToBackend(String name, int age, String accountName, String accountPassword) throws CustomerException
	{
		Logger logger = Logger.getInstance();
		Customer customer = null;
		CustomerType type = CustomerType.REGULAR;
		
		if(accountName!=null || accountPassword !=null)
		{
			type = CustomerType.EMPLOYEE;
		}
		else if(age >60) 
		{
			type = CustomerType.SENIOR_CITIZEN;
		}
		
		switch(type)
		{
		case REGULAR:
			logger.logMessage("creating Regular customer having name:["+name+"] age:["+age+"]");
			customer = new RegularCustomer(name, age);
		break;
		case SENIOR_CITIZEN:
			logger.logMessage("creating Senior citizen customer having name:["+name+"] age:["+age+"]");
			customer = new SeniorCitizen(name,age);
			break;
		case EMPLOYEE:
			logger.logMessage("creating Employee having name:["+name+"], age:["+age+"], accountName:["+accountName+"], accountPassword:[****](masked for security purpose)" );
			customer = new Employee(name, age, accountName, accountPassword);
			break;
		}
		
		//customer can be serialized to json or xml and sent to backend. 
		
		Customer serializedCustomer = customer;
		
		Customer newCustomerFromDB = backend.createCustomerInDB(serializedCustomer);
		
		if(newCustomerFromDB!=null)
		{
			logger.logMessage("Customer ["+ newCustomerFromDB.getName()+ "] created successfully in DB");
		}
		else 
		{
			logger.logMessage("Something went wrong in backend!!");
		}
		
		return newCustomerFromDB;
	}
	
	
	@Override
	public Customer createNewCustomer(String name, int age) throws CustomerException {
		// Create Customer object with the supplied input and call backend to write new customer in DB.
		// on success, return Customer object which can be use in client to show or assert 
		return sendCreateCustomerRequestToBackend(name, age, null, null);
	}

	@Override
	public Customer createNewCustomer(String name, int age, String accountName, String accountPassword) throws CustomerException {
		// Create Customer object with the supplied input and call backend to write new customer in DB.
		// on success, return Customer object which can be use in client to show or assert 
		return sendCreateCustomerRequestToBackend(name, age, accountName, accountPassword);
	}
	
	@Override
	public Customer getCustomerFromBackEnd(String name) throws CustomerException {
		Customer customerFoundFromName = null;
		if(this.nameCustomerMap == null)
		{
			customerFoundFromName = backend.getCustomerDetails(name);
		}
		else 
		{
			if(this.nameCustomerMap.containsKey(name.toLowerCase()))
			{
				customerFoundFromName = this.nameCustomerMap.get(name.toLowerCase());
			}
		}
		if(customerFoundFromName == null)
		{
			throw new CustomerException("customer not found. Please register");
		}
		return customerFoundFromName;
	}
	
	public void showRegisteredCustomers()
	{
		if(this.nameCustomerMap == null)
		{
			this.nameCustomerMap = backend.getRegisteredCustomers();
		}
		for(Map.Entry<String, Customer> entry: nameCustomerMap.entrySet())
		{
			Customer customer = entry.getValue(); 
			CustomerType type = customer.getType();
			switch(type)
			{
			case REGULAR:
				logger.logMessage("Regular customer name:["+customer.getName()+"] age:["+customer.getAge()+"]");
			break;
			case SENIOR_CITIZEN:
				logger.logMessage("Senior citizen customer name:["+customer.getName()+"] age:["+customer.getAge()+"]");
				break;
			case EMPLOYEE:
				logger.logMessage("Employee name:["+customer.getName()+"], age:["+customer.getAge()+"]" );
				break;
			}
		}
	}

	@Override
	public boolean deleteEmployee(String name) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
