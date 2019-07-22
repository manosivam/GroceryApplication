package in.grocery.taxes;

import in.grocery.inventory.ItemTypeException;

/**
 * @author Manosivam
 *
 */
public interface ITaxFinder {

	double getTaxBasedOnItemType(String itemTypeName) throws ItemTypeException;
	
}
