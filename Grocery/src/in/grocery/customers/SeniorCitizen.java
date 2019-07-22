package in.grocery.customers;

import in.grocery.discounts.DiscountManager;

/**
 * @author Manosivam
 *
 */
public class SeniorCitizen extends Customer{

	/**
	 * @param name
	 * @throws CustomerException 
	 */
	public SeniorCitizen(String name, int age) throws CustomerException {
		super(name,age);
		this.type = CustomerType.SENIOR_CITIZEN;
		this.discountPercent = getDiscountPercent();
	}

	@Override
	public double getDiscountPercent() {
		return DiscountManager.getInstance().getDiscountPercent(this.type);
	}
}

