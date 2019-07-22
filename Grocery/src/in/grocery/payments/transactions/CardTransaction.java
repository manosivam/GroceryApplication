package in.grocery.payments.transactions;

import in.grocery.payments.PaymentMethod;

/**
 * @author Manosivam
 *
 */

public class CardTransaction extends Transaction{
	
	public CardTransaction() {
		super();
		this.transactionMode = PaymentMethod.Card;
	}
	
	public CardTransaction(String transactionNumber)
	{
		super(transactionNumber);
		this.transactionMode = PaymentMethod.Card;
	}
	
}