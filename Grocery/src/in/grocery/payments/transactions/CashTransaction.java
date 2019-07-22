package in.grocery.payments.transactions;

import in.grocery.payments.PaymentMethod;

/**
 * @author Manosivam
 *
 */
public class CashTransaction extends Transaction{

	private double payBackCash;
	
	public CashTransaction() {
		super();
		this.payBackCash = 0.0;
		this.transactionMode = PaymentMethod.Cash;
	}
	
	public CashTransaction(String transactionNumber)
	{
		super(transactionNumber);
		this.transactionMode = PaymentMethod.Cash;
	}
	
	public void setPayBackCash(double payBackCash)
	{
		this.payBackCash = payBackCash;
	}

	public double getPayBackAmountIfAny() {
		return this.payBackCash;
	}
}
