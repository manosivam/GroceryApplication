package in.grocery.customers;

import java.util.HashMap;
import java.util.Map;

import in.grocery.backend.Backend;
import in.grocery.inventory.Item;
import in.grocery.inventory.ItemException;
import in.grocery.logs.Logger;

/**
 * @author Manosivam
 *
 */
public class Cart{
	
	private Map<Item, Integer> itemQuantityPair;
	double cartValue;//live cost of the items in the cart.
	
	Logger logger;
	
	public Cart(){
		itemQuantityPair = new HashMap<Item, Integer>();
		cartValue = 0;
		logger = Logger.getInstance();
	}
	
	public Map<Item, Integer> getItemQuantityPair()
	{
		return this.itemQuantityPair;
	}
	
	public void setItemCounts(Map<Item,Integer> itemCounts)
	{
		this.itemQuantityPair = itemCounts;
	}
	
	public double getCartValue()
	{
		return this.cartValue;
	}
	
	public double recomputeCartValueAfterApplyingDiscounts()
	{
		double finalPrize = this.cartValue;
		
		finalPrize =0;
		//apply discount. 
		for(Map.Entry<Item, Integer> entry: this.itemQuantityPair.entrySet())
		{
			Item item = entry.getKey();
			double mrpAfterDiscount = item.getMRP() - (item.getMRP() * item.getDiscountPercent()/100);
			//TODO: logs to check the values.
			finalPrize += (mrpAfterDiscount + item.getTax()) * entry.getValue(); 
		}
		
		return finalPrize;
	}
	
	/*
	 * Can load Items one by One.
	 */
	public void loadItem(Item item) throws ItemException
	{
		int count = 1,earlierCount;
		if(this.itemQuantityPair.containsKey(item))
		{
			earlierCount = this.itemQuantityPair.get(item);
			count+=earlierCount;
		}
		
		//for readability using == operator. 
		if(Backend.getInstance().isItemAvailableForTheGivenQuantityInStock(item,count) == false)
		{
			throw new ItemException("Item Not available.");
		}
		
		this.itemQuantityPair.put(item, count);
		this.cartValue+= (item.getMRP() + item.getTax()) * 1; 
	}
	
	/*
	 * can unload item one by one.
	 */
	public void unLoadItem(Item item)
	{
		this.cartValue-= (item.getMRP()+item.getTax()) *1;
		this.itemQuantityPair.remove(item);
	}
	
	public void seeAvailableItemsInCart()
	{
		logger.logMessage("Viewing items and quantity in cart..");
		if(this.itemQuantityPair!=null && this.itemQuantityPair.size()>0)
		{
			for(Map.Entry<Item, Integer> entry: itemQuantityPair.entrySet())
			{
				logger.logMessage("Item Id: ["+entry.getKey().getId()+"], item:["+ entry.getKey().getName() + "], Quantity[" +entry.getValue()+"]");
			}
		}
		else 
		{
			logger.logMessage("Your cart is empty");
		}
	}
	
	public void emptyCart() {
		this.cartValue = 0.0;
		this.itemQuantityPair.clear();
	}
	
	
	
}
