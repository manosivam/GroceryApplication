package in.grocery.inventory;

import java.util.Map;

/**
 * @author Manosivam
 *
 */
public interface IInventory {

	public ItemType addNewItemTypeInInventory(String name) throws ItemTypeException;
	
	public Map<Item, Integer> addItemsInInventory(String name, double mrp, String itemTypeName, int quantity)
			throws ItemTypeException, ItemException;
	
	public Item getItemFromId(int id) throws ItemException;
	
	public ItemType getItemTypeFromId(int id);
	
	/**
	 * will not print out of stock items.
	 */
	public void printRemainingItemsQuantityDetailsInInventory();
	
	public Map<Item, Integer> getItemsQuantityPairInInventory();
	
	public boolean updateSoldItemQuantityPair(Map<Item,Integer> itemsCount);
	
	public void updateSalesPriceForTheDay(String date, double costToGrocery);
	
	public double getTotalSaleHappenedForTheDay(String dateString);
	
	public boolean isItemAvailableForTheGivenQuantityInStock(Item item,int quantity);
	
	/**
	 * Taxes are subject to change, hence this method.
	 */
	public ItemType updateTaxForItemType(ItemType type, double newTax);
	
}
