package pojo;

import java.io.Serializable;

import utility.Constants.MenuType;
import utility.Constants.UserType;

public class ItemDetail extends BaseAttributes implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int itemId;
	private String name;
	private MenuType menuType;
	private String description;
	private float price;
	private int quantity;

	public ItemDetail() {
		super();
	}

	public ItemDetail(int id, String name, MenuType menutype, String desc, float price, int opType, String msg, int userId, UserType type) {
		super(opType,msg,userId,type);
		this.itemId = id;
		this.name = name;
		this.description = desc;
		this.price = price;
		this.menuType = menutype;
		this.quantity =0;
	}
	
	public int getItemId() {
		return itemId;
	}

	public void setItemId(int menuId) {
		this.itemId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuType getMenuType() {
		return menuType;
	}

	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	@Override
	public String toString() {
		return "ItemID: " + this.itemId + "; Name: "+ this.name + "; Type: " + this.menuType.toString() + "; Price: " + this.price + "; Quantity: " + this.quantity + "; Description: " + this.description + this.getOptType() + this.getMessage();
		
	}
}
