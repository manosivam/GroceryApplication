package in.grocery.payments.transactions;

import in.grocery.payments.PaymentMethod;

/**
 * @author Manosivam
 *
 */
public interface ITransaction {

	boolean isTransactionSuccessful();

	String getTrasactionNumber();

	void setTransactionStatus(boolean status);

	void setTrasactionNumber(String transactionNumber);

	PaymentMethod getTransactionMode();

}