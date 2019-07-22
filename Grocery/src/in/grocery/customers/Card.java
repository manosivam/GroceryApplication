package in.grocery.customers;

/**
 * @author Manosivam
 *
 */
public class Card {

	private String cardNumber;
	private String pin;
	
	public Card()
	{
		cardNumber = null;
	}
	
	public Card(String cardNumber, String pin)
	{
		this.cardNumber = cardNumber;
	}
	
	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}
	
	public String getCardNumber()
	{
		return this.cardNumber;
	}
	
	public void setPin(String pin)
	{
		this.pin = pin;
	}
	
	public String getPin()
	{
		return this.pin;
	}
}
