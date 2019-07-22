package in.grocery.Billing;

import in.grocery.customers.Cart;
import in.grocery.customers.Customer;
import in.grocery.payments.transactions.ITransaction;
import in.grocery.utils.DateUtil;

/**
 * @author Manosivam
 *
 */
public class BillGenerator implements IBillGenerator{

	@Override
	public Bill generateBill(Cart cart, String customerName, ITransaction transaction, int registerId, double billAmount) 
	{
		//TODO: handle null cases. Because BillGenerator can be tested separately. 
		return new Bill(cart,
				transaction.getTrasactionNumber(), 
				transaction.getTransactionMode().toString(),
				customerName,
				registerId,
				DateUtil.getCurrentDate(),
				billAmount);
	}

}
