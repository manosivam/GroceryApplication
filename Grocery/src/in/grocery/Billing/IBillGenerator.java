package in.grocery.Billing;

import in.grocery.customers.Cart;
import in.grocery.payments.transactions.ITransaction;

/**
 * @author Manosivam
 *
 */
interface IBillGenerator {

	public Bill generateBill(Cart cart, String customerName, ITransaction transaction, int registerId, double billAmount);	
}
