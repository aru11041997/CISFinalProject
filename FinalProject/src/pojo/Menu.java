package pojo;

import utility.Constants.MenuType;

public class Menu extends BaseAttributes {
	private int menuId;
	private String name;
	private MenuType menuType;
	private String description;

	public Menu() {
		super();
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
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

}
