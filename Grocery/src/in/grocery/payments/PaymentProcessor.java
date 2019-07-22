package in.grocery.payments;

import in.grocery.payments.transactions.ITransaction;

/**
 * PaymentProcessor is a standalone unit. It don't need to know about outside world.
 * 
 * @author Manosivam
 *
 */ 
public class PaymentProcessor implements IPaymentProcessor{

	@Override
	public ITransaction processPayment(double billAmount, double cashPaid) {
		
		Payment paymentGateway = new CashPayment(cashPaid, billAmount);
		return paymentGateway.processPayment();
	}

	public ITransaction processPayment(double billAmount, String cardNumber, String pin) {

		Payment paymentGateway = new CardPayment(cardNumber, pin,billAmount);
		return paymentGateway.processPayment();
	}
	
}
