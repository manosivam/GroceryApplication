package in.grocery.inventory;

import in.grocery.discounts.DiscountManager;
import in.grocery.taxes.TaxFinder;

/**
 * @author Manosivam
 *
 */
public class Item {

	int id;
	String name; 
	double mrp; 
	ItemType type;
	double tax; 
	double itemDiscountPercent;
	
	Item(){
	}
	
	public Item(String name, double mrp,String itemTypeName) throws ItemTypeException, ItemException
	{
		if(name == null || mrp<0 || name.trim().isEmpty())
		{
			throw new ItemException("invalid name or mrp");
		}
		this.name = name;
		this.mrp = mrp;
		this.type = new ItemType(itemTypeName);//If item type is not added, then backend can it seamlessly, however it is required.
		double taxPercent = TaxFinder.getInstance().getTaxBasedOnItemType(this.type.getName());
		this.tax = mrp*taxPercent/100;
		this.itemDiscountPercent = getDiscountPercent();
	}
	
	public double getDiscountPercent()
	{
		return DiscountManager.getInstance().getDiscountPercent(type.getName(), name);
	}
	
	@Override
	public int hashCode()
	{
		String itemName = name.toLowerCase();
		char[] arr = itemName.toCharArray();
		int hashCode =0;
		for(int i=0;i<arr.length;i++)
		{
			hashCode+= arr[i];
		}
		return hashCode;
	}
	
	@Override
	public boolean equals(Object objectToCompare)
	{
		if(this == objectToCompare)
			return true;
		if(objectToCompare == null || this.getClass() != objectToCompare.getClass())
			return false;
		Item comparingItem = (Item)objectToCompare;
		if(this.name.equalsIgnoreCase(comparingItem.getName()))
		{
			return false;
		}
		return true;
	}
	
	public double getTax()
	{
		return this.tax;
	}
	
	public double getItemDiscount()
	{
		return this.itemDiscountPercent;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public double getMRP()
	{
		return this.mrp;
	}
	
	public ItemType getItemType()
	{
		return this.type;
	}
	
	public void setName(String name)
	{
		this.name = name; 
	}
	
	public void setId(int id )
	{
		this.id = id; 
	}
	
	public void setMRP(double mrp)
	{
		this.mrp = mrp; 
	}
	
	public void setType(ItemType type)
	{
		this.type = type; 
	}
	
	public void setTax(double tax)
	{
		this.tax = tax;
	}
}
