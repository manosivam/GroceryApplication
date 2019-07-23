package in.grocery.Drivers;

import in.grocery.backend.Backend;
import in.grocery.backend.DB;
import in.grocery.customers.Cart;
import in.grocery.customers.Customer;
import in.grocery.customers.CustomerType;
import in.grocery.inventory.Inventory;
import in.grocery.inventory.ItemException;
import in.grocery.logs.Logger;
import in.grocery.payments.PaymentMethod;
import in.grocery.payments.transactions.CashTransaction;
import in.grocery.payments.transactions.ITransaction;
import in.grocery.registers.IRegister;
import in.grocery.utils.DateUtil;
import in.grocery.utils.Utils;

/**
 * @author Manosivam
 *
 */
public class DriverUtils {

	private static DriverUtils automationUtils;
	private Logger logger;
	
	private DriverUtils() {
		logger  = Logger.getInstance();
	}
	
	public static DriverUtils getInstance()
	{
		if(automationUtils == null)
		{
			automationUtils = new DriverUtils();
		}
		return automationUtils;
	}

	private void printRemainingItemInInventory()
	{
		logger.logMessage("---------------------------------------------------------");
		Inventory.getInstance().printRemainingItemsQuantityDetailsInInventory();		
		logger.logMessage("-----------------------------------------------------");
	}

	public void displayItemsInInventoryToCustomer() {
		logger.logMessage("Showing available Items in the inventory to the customer...");
		printRemainingItemInInventory();
	}
	
	public void displayRemainingItemInInventory()
	{
		logger.logMessage("Showing Remaining Items in the inventory....");
		printRemainingItemInInventory();
	}

	public void printTotalSaleforToday() {
		logger.logMessage("Total sale happened today:[" +Utils.roundOffMoney(Inventory.getInstance().getTotalSaleHappenedForTheDay(DateUtil.getCurrentDate()))+"]");
	}

	public ITransaction readPaymentInputFromCustomerAndInitiateTransaction(IRegister register, double billAmount, Customer customer) {
		
		logger.logMessage("How do you want to pay the bill? Cash or card?"
				+ "\nType cash<space>amount that you are paying OR "
				+ "\ncard<space>card number<space>pin to pay");
		
		String cashFromCustomer= null, cardNumber = null, pin = null;
		ITransaction transaction;
		
		for(;;)
		{
			String paymentInputFromCustomer = InputReader.getInstance().readALine();
			
			if(paymentInputFromCustomer.startsWith("cash "))
			{
				cashFromCustomer = paymentInputFromCustomer.split(" ")[1];
				break;
			}
			else if(paymentInputFromCustomer.startsWith("card ")) 
			{
				String splitedString[] = paymentInputFromCustomer.split(" ");
				cardNumber = splitedString[1];
				pin = splitedString[2];
				break;
			}
			else
			{
				logger.logMessage("Sorry, Please enter valid input in the mentioned format.");
				continue;
			}
		}
		if(cashFromCustomer!=null)
		{
			transaction= register.acceptPaymentAndProcessTheOrderForCustomer(billAmount,Double.parseDouble(cashFromCustomer), customer);
		}
		else 
		{
			transaction = register.acceptPaymentAndProcessTheOrderForCustomer(billAmount, cardNumber, pin, customer);
		}
		customer.getCart().emptyCart();//Since the Customer object is a static one in DBLayer, this has to be called for the same customer to re-enter the shop.
		//However, this may not be required in production environment where static variables will not be maintained.
		
		return transaction;
	}

	/**
	 * @param transaction
	 */
	public void verifyTransactionStatus(ITransaction transaction) {
		if(transaction!=null && transaction.isTransactionSuccessful())
		{
			if(transaction.getTransactionMode() == PaymentMethod.Cash)
			{
				double payBackAmount = Utils.roundOffMoney(((CashTransaction)transaction).getPayBackAmountIfAny());
				if(payBackAmount>0)
				{
					logger.logMessage("Please take the change of \""+payBackAmount+"\" rupees.");
				}
			}
		}
		else 
		{
			logger.logMessage("Transaction unsuccessful. please enter valid payment details.");
		}
	}

	/**
	 * @param register 
	 * @param customerType 
	 * @return
	 */
	public double applyDiscountsByDefaultAndGetFinalBillAmount(Cart cart, CustomerType customerType, double customerBasedDiscount,  IRegister register) {
		
		double billAmount = 0.0;
		
		boolean applyDiscountsOnCustomerType = (customerType == CustomerType.SENIOR_CITIZEN || customerType== CustomerType.EMPLOYEE);
		boolean applyDisccountsOnItemOrItemType = true;
		
		if(customerType == CustomerType.SENIOR_CITIZEN)
		{
			logger.logMessage("Applying Senior citizen discount percent of " + customerBasedDiscount);
		}
		else if(customerType== CustomerType.EMPLOYEE)
		{
			logger.logMessage("Applying Employee discount percent of " + customerBasedDiscount);
		}
		else 
		{
			logger.logMessage("Applying Item/Item type based discount percent if any.");
		}
		
		billAmount = register.applyDiscountAndGetFinalPriceToPay(cart, customerBasedDiscount, applyDiscountsOnCustomerType, applyDisccountsOnItemOrItemType);
		billAmount = Utils.roundOffMoney(billAmount);
		double amountSaved= cart.getCartValue() - billAmount;
		
		logger.logMessage("Bill amount is: "+ billAmount);
		if(cart.getCartValue() > billAmount)
		{
			logger.logMessage("Congratulation, You have saved ["+Utils.roundOffMoney(amountSaved)+"] in this purchase");
		}
		return billAmount;
	}

	/**
	 * @param cart
	 */
	public void readItemsAsInputAndLoadCart(Cart cart) {
		
		logger.logMessage("Please load the cart by typing in item Id.. Refer the above item list. "
				+ "\nNote: Items can be loaded one by one. "
				+ "If multiple items are needed, please load the same item for the required quantity by repeating the Item Id. "
				+ "Thank you for your understanding. "
				+ "\nType done to place the order.");
		
		for(;;)
		{
			String itemIdToLoadTheCartOrInputToPlaceTheOrder = InputReader.getInstance().readItemIdToLoadTheCartOrInputToPlaceTheOrder();
			if(itemIdToLoadTheCartOrInputToPlaceTheOrder.equalsIgnoreCase(DriverConstants.DONE_COMMAND))
			{
				break;
			}
			try {
				cart.loadItem(Inventory.getInstance().getItemFromId(Integer.parseInt(itemIdToLoadTheCartOrInputToPlaceTheOrder)));
			} catch (ItemException | NumberFormatException e) {
				logger.logMessage("Invalid item. Please try loading from the available item list..");
			}
		}
	}
	
	public void bootstrapApplication()
	{
		Logger logger  = Logger.getInstance();
		logger.logMessage("Bootstrapping Grocery application...");
		logger.logMessage("---------------------------------------------------");
		Backend backend = Backend.getInstance();
		
		logger.logMessage("adding few item types...");
		backend.addFewItems();
		logger.logMessage("Item types added.");
		logger.logMessage("---------------------------------------------------");
		
		logger.logMessage("adding few items to inventory...");
		backend.addFewItemTypes();
		logger.logMessage("Items added to inventory");
		logger.logMessage("---------------------------------------------------");
		
		logger.logMessage("adding few customers...");
		backend.addFewCustomers();
		logger.logMessage("customers added");
		logger.logMessage("---------------------------------------------------");
	}
}
