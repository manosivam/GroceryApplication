package in.grocery.customers;

import in.grocery.discounts.DiscountManager;

/**
 * @author Manosivam
 *
 */
public class RegularCustomer extends Customer{

	/**
	 * @param name
	 * @throws CustomerException 
	 */
	public RegularCustomer(String name, int age) throws CustomerException {
		super(name,age);
		this.type = CustomerType.REGULAR;
		this.discountPercent = getDiscountPercent();
	}
	
	@Override
	public double getDiscountPercent() {
		return DiscountManager.getInstance().getDiscountPercent(this.type);
	}
	//If RegularCustomer wants to have a discount, DiscountManager will take care of that. 
	
}
