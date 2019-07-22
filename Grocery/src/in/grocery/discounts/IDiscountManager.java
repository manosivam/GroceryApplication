package in.grocery.discounts;

import in.grocery.customers.CustomerType;

/**
 * @author Manosivam
 * Note: accessible only in this package. The interface is not public. 
 */
interface IDiscountManager {
	
	double getDiscountPercent(CustomerType type);
	double getDiscountPercent(String itemTypeName, String itemName);
}
