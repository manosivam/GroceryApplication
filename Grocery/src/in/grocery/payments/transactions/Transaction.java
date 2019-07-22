package in.grocery.payments.transactions;

import in.grocery.payments.PaymentMethod;

/**
 * @author Manosivam
 *
 */
public class Transaction implements ITransaction {

	private boolean status;
	private String transactionNumber;
	protected PaymentMethod transactionMode;
	
	public Transaction()
	{
		status = false; 
		transactionNumber = "";
	}
	
	public Transaction(String transactionNumber)
	{
		this.transactionNumber = transactionNumber;
		status = false; 
	}
	
	@Override
	public boolean isTransactionSuccessful()
	{
		return this.status;
	}
	
	@Override
	public String getTrasactionNumber()
	{
		return this.transactionNumber;
	}
	
	@Override
	public void setTransactionStatus(boolean status)
	{
		this.status = status;
	}
	
	@Override
	public void setTrasactionNumber(String transactionNumber)
	{
		this.transactionNumber = transactionNumber;
	}
	
	@Override
	public PaymentMethod getTransactionMode()
	{
		return transactionMode;
	}
	
}
