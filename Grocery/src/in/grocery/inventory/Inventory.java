package in.grocery.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import in.grocery.backend.Backend;
import in.grocery.logs.Logger;

/**
 * @author Manosivam
 *
 */
public class Inventory implements IInventory{

	Logger logger;
	private static Inventory inventory;
	private Backend backend;

	private Map<Item, Integer> availableItemsQuantityMap;
	public Map<Integer, Item> itemIdItemMap;
	
	private ArrayList<ItemType> itemTypesAvailable;
	public Map<String,Double> taxItemTypePercentMap;

	private Map<String,Double> earningsPerDaySoFarMap;
	
	private Inventory() 
	{
		logger = Logger.getInstance();
		backend = Backend.getInstance(); //creates connectivity with backend layer.
		//all other data members will take null value by default.
	}
	
	public static Inventory getInstance() 
	{
		if(inventory == null)
		{
			inventory = new Inventory();
		}
		return inventory;
	}
	
	@Override
	public Item getItemFromId(int id) throws ItemException
	{
		Item item = null;
		if(itemIdItemMap == null)
		{
			item = backend.getItemFromId(id);
		}
		else 
		{
			if(itemIdItemMap.containsKey(id))
			{
				item = itemIdItemMap.get(id);
			}
		}
		if(item == null)
		{
			throw new ItemException("Item not available in inventory");
		}
		return item;
	}
	
	@Override
	public Map<Item, Integer> getItemsQuantityPairInInventory() 
	{
		if(availableItemsQuantityMap == null)
		{
			availableItemsQuantityMap = backend.getAvailableItemsQuantityPair();
		}
		return availableItemsQuantityMap;
	}

	@Override
	public double getTotalSaleHappenedForTheDay(String dateString) 
	{
		double totalSaleHappened = 0.0;
		if(earningsPerDaySoFarMap == null || !earningsPerDaySoFarMap.containsKey(dateString))
		{
			totalSaleHappened = backend.getTotalSaleHappened(dateString);
		}
		else 
		{
			if(earningsPerDaySoFarMap.containsKey(dateString))
			{
				totalSaleHappened= earningsPerDaySoFarMap.get(dateString);
			}
		}
		return totalSaleHappened;
	}

	/**
	 * will not print out of stock items.
	 */
	@Override
	public void printRemainingItemsQuantityDetailsInInventory() {
		Map<Item,Integer> availableItemDetails = getItemsQuantityPairInInventory();
		for(Map.Entry<Item,Integer> entry: availableItemDetails.entrySet())
		{
			if(entry.getValue() > 0)
			{
				logger.logMessage("Item id: ["+entry.getKey().getId()+"] Item name:["+entry.getKey().getName()+"], quantity:["+entry.getValue()+"].");
			}
		}
	}

	@Override
	public Map<Item,Integer> addItemsInInventory(String name, double mrp, String itemTypeName, int quantity) throws ItemTypeException, ItemException {
		Item itemToAdd = new Item(name, mrp,itemTypeName);
		return backend.addNewItemAndQuantityInDB(itemToAdd, quantity);
	}

	@Override
	public ItemType addNewItemTypeInInventory(String name) throws ItemTypeException
	{
		ItemType type = new ItemType(name); 
		//for understanding used multiple variables.
		ItemType itemTypeFromDB = backend.addItemTypeInDB(type);
		
		return itemTypeFromDB;
	}
	
	@Override
	public ItemType getItemTypeFromId(int id)
	{
		return backend.getItemTypeFromId(id); 
	}
	
	/**
	 * Taxes are subject to change, hence this method.
	 */
	@Override
	public ItemType updateTaxForItemType(ItemType type, double newTax)
	{
		//BackendLayer.updateTaxForItemTypeInDb(type, newTax);
		return null;
	}
	
	@Override
	public boolean updateSoldItemQuantityPair(Map<Item, Integer> itemQuantityMap) {
		return backend.updateSoldItemsDetails(itemQuantityMap);
	}

	@Override
	public void updateSalesPriceForTheDay(String date, double costToUpdate) {
		backend.updateSalesPriceForTheDay(date,costToUpdate);
		if(earningsPerDaySoFarMap == null)
		{
			earningsPerDaySoFarMap = new HashMap<String, Double>();
			earningsPerDaySoFarMap.put(date,costToUpdate);
		}
		else 
		{
			if(earningsPerDaySoFarMap.containsKey(date))
			{
				double previousEarnings = earningsPerDaySoFarMap.get(date);
				earningsPerDaySoFarMap.put(date, previousEarnings+costToUpdate);
			}
			else 
			{
				earningsPerDaySoFarMap.put(date, costToUpdate);
			}
		}
	}

	@Override
	public boolean isItemAvailableForTheGivenQuantityInStock(Item item,int quantity) {
		return backend.isItemAvailableForTheGivenQuantityInStock(item, quantity);
	}
	
}
