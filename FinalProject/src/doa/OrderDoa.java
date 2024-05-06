package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pojo.ItemDetail;
import pojo.Order;
import utility.Constants.MenuType;
import utility.Constants.OrderStatus;

public class OrderDoa {

	public Order addOrder(final Connection conn, final Order order) {
		int count;
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO `order`(userid, orderDate, amount, status) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
			conn.setAutoCommit(false);
			final float amount = (float) order.getPrice();
			final Date today = new Date();
			final Timestamp timestamp = new Timestamp(today.getTime());
			preparedStatement.setInt(1, order.getUserId());
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setFloat(3, amount);
			preparedStatement.setString(4, order.getOrderStatus().toString());
			count = preparedStatement.executeUpdate();
			final ResultSet resultSet = preparedStatement.getGeneratedKeys();
			int id = -1;
			if (resultSet.next()) {
				id = resultSet.getInt(1);
				order.setOrderId(id);
				
			}
			if (count == 1) {
				final PreparedStatement preparedStatement1 = conn
						.prepareStatement("INSERT INTO itemlist(orderid, itemid, quantity) VALUES(?,?,?)");
				order.setMessage("Insert Successfully");
				for (final ItemDetail detail : order.getItemDetails()) {
					preparedStatement1.setInt(1, id);
					preparedStatement1.setInt(2, detail.getItemId());
					preparedStatement1.setInt(3, detail.getQuantity());
					preparedStatement1.executeUpdate();
				}
				preparedStatement1.close();
				order.setMessage("Order placed");
			} else {
				order.setMessage("Unable to insert.");
				order.setOptType(-2);
			}
			conn.commit();
			conn.setAutoCommit(true);

		} catch (final Exception e) {
			e.printStackTrace();
			order.setMessage(e.getMessage());
			order.setOptType(-2);
		}
		return order;
	}

	public Order updateOrder(final Connection conn, final Order order) {
		int count;
		try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM itemlist WHERE orderid = ?")) {
			preparedStatement.setInt(1, order.getOrderId());
			count = preparedStatement.executeUpdate();
			conn.setAutoCommit(false);
			if (count >= 1) {
				PreparedStatement preparedStatement1 = conn
						.prepareStatement("INSERT INTO itemlist(orderid, itemid, quantity) VALUES(?,?,?)");
				order.setMessage("Insert Successfully");
				for (final ItemDetail detail : order.getItemDetails()) {
					preparedStatement1.setInt(1, order.getOrderId());
					preparedStatement1.setInt(2, detail.getItemId());
					preparedStatement1.setInt(3, detail.getQuantity());
					preparedStatement1.executeUpdate();
				}
				preparedStatement1.close();
				final float amount = order.getPrice();//(float) order.getItemDetails().stream().mapToDouble(ItemDetail::getPrice).sum();
				preparedStatement1 = conn.prepareStatement("UPDATE `ORDER` SET amount = ? WHERE orderid = ?");
				preparedStatement1.setFloat(1, amount);
				preparedStatement1.setInt(2, order.getOrderId());
				preparedStatement1.executeUpdate();
				preparedStatement1.close();
			}
			conn.commit();
			conn.setAutoCommit(true);
			order.setMessage("Order updated.");
		} catch (final Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-3);
			e.printStackTrace();
		}
		return order;
	}

	public Order deleteOrder(final Connection conn, final Order order) {
		try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM `order` WHERE orderid = ?")) {
			preparedStatement.setInt(1, order.getOrderId());
			final int count = preparedStatement.executeUpdate();
			if (count >= 1) {
				order.setMessage("Order deleted.");
			} else {
				order.setMessage("Unable to delete.");
				order.setOptType(-4);
			}
		} catch (final Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-4);
		}
		return order;
	}

	public List<Order> getAllOrder(final Connection conn, final Order order) {
		final List<Order> orders = new ArrayList<>();
		try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM `order` where userid  = ? order by orderId desc")) {
			preparedStatement.setInt(1, order.getUserId());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Order order2 = new Order();
				order2.setOrderId(resultSet.getInt("orderid"));
				order2.setOrderDate(resultSet.getTimestamp("orderdate"));
				order2.setPrice(resultSet.getFloat("amount"));
				order2.setUserId(resultSet.getInt("userId"));
				order2.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status")));
				orders.add(order2);
			}
			resultSet.close();

			final PreparedStatement preparedStatement2 = conn
					.prepareStatement("SELECT * FROM itemlist WHERE orderId = ?");
			final PreparedStatement preparedStatement3 = conn
					.prepareStatement("SELECT * FROM itemdetail WHERE itemID = ?");
			for (final Order order2 : orders) {
				preparedStatement2.setInt(1, order2.getOrderId());
				resultSet = preparedStatement2.executeQuery();
				final List<ItemDetail> itemDetails = new ArrayList<>();

				while (resultSet.next()) {
					final int itemId = resultSet.getInt("itemId");
					final int quantity = resultSet.getInt("quantity");
					preparedStatement3.setInt(1, itemId);
					final ResultSet set = preparedStatement3.executeQuery();
					if (set.next()) {
						final ItemDetail detail = new ItemDetail();
						detail.setItemId(set.getInt("itemId"));
						detail.setDescription(set.getString("description"));
						detail.setPrice(set.getFloat("price"));
						detail.setName(set.getString("name"));

						final String menuType = set.getString("type");
						MenuType type = null;
						if (menuType.equals("VEG"))
							type = MenuType.VEG;
						else if (menuType.equals("NONVEG"))
							type = MenuType.NONVEG;
						else if (menuType.equals("VEGAN"))
							type = MenuType.VEGAN;

						detail.setMenuType(type);
						detail.setQuantity(quantity);

						itemDetails.add(detail);

					}
				}
				order2.setItemDetails(itemDetails);
			}

		} catch (final Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-1);
		}

		return orders;
	}

	public List<Order> getOrdersForChef(final Connection conn, final Order order) {
		final List<Order> orders = new ArrayList<>();
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("SELECT * FROM `order` WHERE status != 'COMPLETED'")) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				final Order order2 = new Order();
				order2.setOrderId(resultSet.getInt("orderid"));
				order2.setUserId(resultSet.getInt("userId"));
				order2.setOrderDate(resultSet.getTimestamp("orderdate"));
				order2.setPrice(resultSet.getFloat("amount"));
				order2.setOrderStatus(OrderStatus.valueOf(resultSet.getString("status")));
				orders.add(order2);
			}
			resultSet.close();

			final PreparedStatement preparedStatement2 = conn
					.prepareStatement("SELECT * FROM itemlist WHERE orderId = ?");
			final PreparedStatement preparedStatement3 = conn
					.prepareStatement("SELECT * FROM itemdetail WHERE itemID = ?");
			for (final Order order2 : orders) {
				preparedStatement2.setInt(1, order2.getOrderId());
				resultSet = preparedStatement2.executeQuery();
				final List<ItemDetail> itemDetails = new ArrayList<>();

				while (resultSet.next()) {
					final int itemId = resultSet.getInt("itemId");
					final int quantity = resultSet.getInt("quantity");
					preparedStatement3.setInt(1, itemId);
					final ResultSet set = preparedStatement3.executeQuery();
					if (set.next()) {
						final ItemDetail detail = new ItemDetail();
						detail.setItemId(set.getInt("itemId"));
						detail.setDescription(set.getString("description"));
						detail.setPrice(set.getFloat("price"));
						detail.setName(set.getString("name"));

						final String menuType = set.getString("type");
						MenuType type = null;
						if (menuType.equals("VEG"))
							type = MenuType.VEG;
						else if (menuType.equals("NONVEG"))
							type = MenuType.NONVEG;
						else if (menuType.equals("VEGAN"))
							type = MenuType.VEGAN;

						detail.setMenuType(type);
						detail.setQuantity(quantity);

						itemDetails.add(detail);

					}
				}
				order2.setItemDetails(itemDetails);
			}

		} catch (final Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-1);
		}

		return orders;
	}

	public Order updateStatus(final Connection conn, final Order order) {
		try (PreparedStatement preparedStatement = conn
				.prepareStatement("UPDATE `order` SET status = ? WHERE orderid = ?")) {
			preparedStatement.setString(1, order.getOrderStatus().toString());
			preparedStatement.setInt(2, order.getOrderId());
			int count = preparedStatement.executeUpdate();
			if (count >= 1) {
				order.setMessage("Order updated.");
			} else {
				order.setMessage("Unable to update.");
				order.setOptType(-5);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			order.setMessage(e.getMessage());
			order.setOptType(-5);
		}
		return order;
	}

}
