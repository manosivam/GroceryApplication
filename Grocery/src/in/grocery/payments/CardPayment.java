package in.grocery.payments;

import java.util.Random;

import in.grocery.customers.Card;
import in.grocery.payments.transactions.CardTransaction;
import in.grocery.payments.transactions.ITransaction;

/**
 * @author Manosivam
 *
 */
class CardPayment extends Payment{
	
	private Card card;
	private double billAmount;
	
	public CardPayment(String cardNumber, String pin, double billAmount) {
		super(PaymentMethod.Card);
		this.card = new Card(cardNumber,pin);
	}

	public Card getCard()
	{
		return this.card;
	}

	@Override
	public ITransaction processPayment() {
		ITransaction transactionOutput = new CardTransaction(String.valueOf(new Random().nextLong()));

		//Assuming pin and card details matches all the time.
		if(this.card.getCardNumber()==null && this.card.getPin()==null)
		{
			transactionOutput.setTransactionStatus(false);
		}
		else 
		{
			transactionOutput.setTransactionStatus(true);
		}
		return transactionOutput;
	}
}
