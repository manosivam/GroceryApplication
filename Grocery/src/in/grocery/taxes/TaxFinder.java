package in.grocery.taxes;

import in.grocery.backend.Backend;
import in.grocery.inventory.ItemTypeException;

/**
 * <p>
 * used to find taxes of the item. 
 * <p>
 * @author Manosivam
 *
 */
public class TaxFinder implements ITaxFinder{

	private static TaxFinder taxFinder;
	private Backend backend;
	
	private TaxFinder()
	{
		backend = Backend.getInstance();//create connectivity with backend.
	}
	
	public static TaxFinder getInstance()
	{
		if(taxFinder == null)
		{
			taxFinder = new TaxFinder();
		}
		return taxFinder;
	}
	
	public double getTaxBasedOnItemType(String itemTypeName) throws ItemTypeException {
		return backend.getTaxPercentForItemTypeString(itemTypeName);
	}
	
}
