package in.grocery.Billing;

import java.util.Map;

import in.grocery.customers.Cart;
import in.grocery.inventory.Item;

/**
 * @author Manosivam
 *
 */
public class Bill {

	/**
	1. with items in the cart.
	2. Transaction number
	3. purchase date. Current date.
	4. Customer name. 
	5. Register id. 
	*/
	Map<Item, Integer> itemsPurchased;
	String transactionNumber;
	String paymentMethod;
	String customerName;
	int registerId;
	String purchaseDateString;
	double billAmount;
	
	public Bill(Cart cart,String transactionNumber,String paymentMethod,
			String customerName,int registerId,String purchaseDateString, double billAmount)
	{
		this.itemsPurchased = cart.getItemQuantityPair();
		this.transactionNumber= transactionNumber; 
		this.paymentMethod = paymentMethod;
		this.customerName = customerName;
		this.registerId = registerId;
		this.purchaseDateString = purchaseDateString;
		this.billAmount = billAmount;
	}
	
}
