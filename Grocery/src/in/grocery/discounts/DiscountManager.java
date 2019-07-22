package in.grocery.discounts;

import in.grocery.backend.Backend;
import in.grocery.customers.CustomerType;

/**
 * @author Manosivam
 *
 */
public class DiscountManager implements IDiscountManager{

	private static DiscountManager discountManager;
	private Backend backend;
	
	private DiscountManager() {
		backend = Backend.getInstance();//creates connectivity with backend layer.
	}
	
	public static DiscountManager getInstance()
	{
		if(discountManager == null)
		{
			discountManager = new DiscountManager();
		}
		return discountManager;
	}
	
	/**
	 *  @return denotes discounts in percentage. For convenience, I am considering it as int, rather than double.  
	 */
	@Override
	public double getDiscountPercent(CustomerType type) {
		
		return backend.getDiscountPercentForCustomerType(type);
	}

	@Override
	public double getDiscountPercent(String itemTypeName, String itemName) {
		return backend.getDiscountPercentForItemTypeOrItem(itemTypeName, itemName);
	}
	
}
