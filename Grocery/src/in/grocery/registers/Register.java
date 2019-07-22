package in.grocery.registers;

import in.grocery.Billing.Bill;
import in.grocery.Billing.BillGenerator;
import in.grocery.customers.Cart;
import in.grocery.customers.Customer;
import in.grocery.inventory.IInventory;
import in.grocery.inventory.Inventory;
import in.grocery.logs.Logger;
import in.grocery.payments.PaymentProcessor;
import in.grocery.payments.transactions.ITransaction;
import in.grocery.utils.DateUtil;

/**
 * Is more like a counters in supermarket. This will be contacting point
 * 1. accepting order and payments.
 * 2. applying discount of the ordered cart. 
 * 3. generating bill. 
 * 
 * @author Manosivam
 *
 */
public class Register implements IRegister {

	Logger logger = Logger.getInstance();
	
	int registerId; 
	PaymentProcessor paymentProcessor;
	
	public Register(int registerId){
		this.registerId = registerId;
		this.paymentProcessor = new PaymentProcessor();
	}
	
	@Override
	public int getRegisterId()
	{
		return this.registerId;
	}

	/** 
	 * @param applyDiscountsOnCustomerType - based on customer type on the final prize. 
	 * @param applyDisccountsOnItemOrItemType - based on Item or Item type on each of the items in the cart.
	 *	Either applyDiscountOnCustomerType or applyDisccountsOnItemOrItemType can be applied. 
	 *  applyDiscountOnCustomerType takes higher precedence if both are true.
	 */ 
	@Override
	public double applyDiscountAndGetFinalPriceToPay(Cart cart, double customerBasedDiscount, boolean applyDiscountsOnCustomerType, 
		boolean applyDisccountsOnItemOrItemType) {
	
		double amountToPay = 0;
		if(applyDiscountsOnCustomerType)
		{
			amountToPay = cart.getCartValue() - (cart.getCartValue() * customerBasedDiscount/100);
		}
		else if(applyDisccountsOnItemOrItemType)
		{
			amountToPay = cart.recomputeCartValueAfterApplyingDiscounts();
		}
		else 
		{
			amountToPay = cart.getCartValue();
		}
		return amountToPay;
	}
	
	/**
	 * @param cart
	 * @param card
	 * @param pin
	 * @param applyDiscount
	 * @return 
	 */
	@Override
	public ITransaction acceptPaymentAndProcessTheOrderForCustomer(double billAmount, String cardNumber, String pin, Customer purchaseAndCustomerDetailsForBillingAndInventory)
	{	
		ITransaction transaction = this.paymentProcessor.processPayment(billAmount, cardNumber, pin);
		
		if(transaction.isTransactionSuccessful())
		{
			logger.logMessage("Transaction is successful. Generating bill.. please wait..");
			generateBillAndNotifyCustomer(purchaseAndCustomerDetailsForBillingAndInventory, transaction, billAmount);
			//update the inventory and print it.
			logger.logMessage("Updating Inventory items...");
			IInventory inventory= Inventory.getInstance();
			inventory.updateSoldItemQuantityPair(purchaseAndCustomerDetailsForBillingAndInventory.getCart().getItemQuantityPair());
			inventory.updateSalesPriceForTheDay(DateUtil.getCurrentDate(), billAmount);
			
		}
		return transaction;
	}

	@Override
	public ITransaction acceptPaymentAndProcessTheOrderForCustomer(double billAmount, double cashPaid, Customer purchaseAndCustomerDetailsForBillingAndInventory) {
		
		ITransaction transaction = this.paymentProcessor.processPayment(billAmount, cashPaid);
		if(transaction.isTransactionSuccessful())
		{
			logger.logMessage("transaction is successful.");
			generateBillAndNotifyCustomer(purchaseAndCustomerDetailsForBillingAndInventory, transaction, billAmount);
			//update the inventory and print it after giving payBackAmount to customer.
			logger.logMessage("Updating Inventory items...");
			IInventory inventory= Inventory.getInstance();
			inventory.updateSoldItemQuantityPair(purchaseAndCustomerDetailsForBillingAndInventory.getCart().getItemQuantityPair());
			inventory.updateSalesPriceForTheDay(DateUtil.getCurrentDate(), billAmount);
		}
		return transaction;
	}
	
	private Bill generateBillAndNotifyCustomer(Customer customer, ITransaction transaction, double billAmount)
	{
		Bill generatedBill = new BillGenerator().generateBill(customer.getCart(), customer.getName(), transaction, this.registerId, billAmount);
		
		//TODO: log the bill details in output.
		
		/*
		2. print - new Printer().print(Bill bill) 
		3. notify customer, if email is set in our DB 
			new NotificationLayer().notifyAsync(Customer customer, Bill bill) 
		*/
		return generatedBill;
	}
	

}
