package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import pojo.ItemDetail;
import utility.Constants.MenuType;

public class ItemDetailDoa {
	public List<ItemDetail> getAllItem(final Connection conn, final ItemDetail itemDetail) {
		final List<ItemDetail> menus = new ArrayList<>();
		try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM itemdetail")) {
			final ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final ItemDetail menu2 = new ItemDetail();
				menu2.setItemId(resultSet.getInt("itemID"));
				menu2.setName(resultSet.getString("name"));
				final String menuType = resultSet.getString("type");

				MenuType type = null;
				if (menuType.equals("VEG"))
					type = MenuType.VEG;
				else if (menuType.equals("NONVEG"))
					type = MenuType.NONVEG;
				else if (menuType.equals("VEGAN"))
					type = MenuType.VEGAN;
				menu2.setMenuType(type);

				menu2.setDescription(resultSet.getString("description"));
				menu2.setPrice(resultSet.getFloat("price"));
				menus.add(menu2);
			}
		} catch (final Exception e) {

		}
		return menus;
	}

	public ItemDetail addItem(final Connection conn, final ItemDetail menu) {
		int count;
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO itemdetail(name, type, description, price) VALUES(?,?,?,?)")) {
			preparedStatement.setString(1, menu.getName());
			preparedStatement.setString(2, menu.getMenuType().toString());
			preparedStatement.setString(3, menu.getDescription());
			preparedStatement.setFloat(4, menu.getPrice());
			count = preparedStatement.executeUpdate();
			if (count == 1) {
				menu.setMessage("Insert Successfully");
			} else {
				menu.setMessage("Unable to insert.");
				menu.setOptType(-2);
			}
		} catch (final Exception e) {
			menu.setMessage(e.getMessage());
			menu.setOptType(-2);
		}
		return menu;
	}

	public ItemDetail updateItem(final Connection conn, final ItemDetail menu) {
		int count;
		try (PreparedStatement preparedStatement = conn.prepareStatement(
				"UPDATE itemdetail SET name = ?, type = ?, description = ?, , price = ? WHERE itemID = ?")) {
			preparedStatement.setString(1, menu.getName());
			preparedStatement.setString(2, menu.getMenuType().toString());
			preparedStatement.setString(3, menu.getDescription());
			preparedStatement.setFloat(4, menu.getPrice());
			preparedStatement.setInt(5, menu.getItemId());
			count = preparedStatement.executeUpdate();
			if (count == 1) {
				menu.setMessage("Update Successfully");
			} else {
				menu.setMessage("Unable to update.");
				menu.setOptType(-3);
			}
		} catch (final Exception e) {
			menu.setMessage(e.getMessage());
			menu.setOptType(-3);
		}

		return menu;
	}

	public ItemDetail deleteItem(final Connection conn, final ItemDetail menu) {
		int count;
		try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM itemdetail WHERE itemID = ?")) {
			preparedStatement.setInt(1, menu.getItemId());
			count = preparedStatement.executeUpdate();
			if (count == 1) {
				menu.setMessage("Delet Successfully");
			} else {
				menu.setMessage("Unable to delete.");
				menu.setOptType(-4);
			}

		} catch (final Exception exception) {
			menu.setMessage(exception.getMessage());
			menu.setOptType(-4);
		}
		return menu;
	}
}
