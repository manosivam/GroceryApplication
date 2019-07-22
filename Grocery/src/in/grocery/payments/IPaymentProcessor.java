package in.grocery.payments;

import in.grocery.payments.transactions.ITransaction;

/**
 * @author Manosivam
 *
 */
public interface IPaymentProcessor {

	public ITransaction processPayment(double billAmount, double cashPaid);
	
	public ITransaction processPayment(double billAmount, String cardNumber, String pin);
	
}
