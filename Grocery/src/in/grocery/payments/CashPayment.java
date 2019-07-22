package in.grocery.payments;

import java.util.Random;

import in.grocery.payments.transactions.CashTransaction;
import in.grocery.payments.transactions.ITransaction;

/**
 * @author Manosivam
 *
 */
public class CashPayment extends Payment{

	private double amountPaid;
	private double billAmount;
	
	public CashPayment(double amountPaid, double billAmount) {
		super(PaymentMethod.Cash);
		this.amountPaid = amountPaid;
		this.billAmount = billAmount;
	}

	public double getAmountPaid()
	{
		return this.amountPaid;
	}

	@Override
	public ITransaction processPayment() {
		CashTransaction transactionDetails = new CashTransaction();
		transactionDetails.setTrasactionNumber(String.valueOf(new Random().nextLong()));
		
		if(amountPaid < billAmount)
		{
			transactionDetails.setTransactionStatus(false);
		}
		else 
		{
			//if paid amount is valid, assuming the transaction to be successful. 
			transactionDetails.setPayBackCash(amountPaid - billAmount);
			transactionDetails.setTransactionStatus(true);
		}
		return transactionDetails;
	}
	
}
