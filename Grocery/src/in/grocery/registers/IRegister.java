package in.grocery.registers;

import in.grocery.customers.Cart;
import in.grocery.customers.Customer;
import in.grocery.payments.transactions.ITransaction;

/**
 * @author Manosivam
 *
 */
public interface IRegister {

	int getRegisterId();

	double applyDiscountAndGetFinalPriceToPay(Cart cart, double customerBasedDiscount,
			boolean applyDiscountsOnCustomerType, boolean applyDisccountsOnItemOrItemType);

	ITransaction acceptPaymentAndProcessTheOrderForCustomer(double billAmount, String cardNumber, String pin,
			Customer purchaseAndCustomerDetailsForBillingAndInventory);

	ITransaction acceptPaymentAndProcessTheOrderForCustomer(double billAmount, double cashPaid,
			Customer purchaseAndCustomerDetailsForBillingAndInventory);

}