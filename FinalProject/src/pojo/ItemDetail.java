package pojo;

import utility.Constants.MenuType;

public class ItemDetail extends BaseAttributes {
	private int itemId;
	private String name;
	private MenuType menuType;
	private String description;
	private float price;
	private int quantity;

	public ItemDetail() {
		super();
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
}
