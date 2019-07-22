package in.grocery.payments;

import in.grocery.payments.transactions.ITransaction;

/**
 * @author Manosivam
 *
 */
public abstract class Payment {

	PaymentMethod paymentMethod;
	
	public Payment(PaymentMethod method)
	{
		this.paymentMethod = method;
	}
	
	public abstract ITransaction processPayment();
}
