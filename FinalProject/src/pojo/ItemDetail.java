package pojo;

import utility.Constants.MenuType;

public class ItemDetail extends BaseAttributes {
	private int itemId;
	private String name;
	private MenuType menuType;
	private String description;
	private float price;

	public ItemDetail() {
		super();
	}

	public ItemDetail(int id, String name, MenuType menutype, String desc, float price) {
		super();
		this.itemId = id;
		this.name = name;
		this.description = desc;
		this.price = price;
		this.menuType = menutype;
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

}
