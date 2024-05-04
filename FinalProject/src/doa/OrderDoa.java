package doa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

import pojo.ItemDetail;
import pojo.Order;

public class OrderDoa {

	public Order addOrder(final Connection conn, final Order order) {
		int count;

		try (PreparedStatement preparedStatement = conn
				.prepareStatement("INSERT INTO order(userid, orderDate, amount, status) VALUES (?,?,?,?)")) {
			conn.setAutoCommit(false);
			final float amount = (float) order.getItemDetails().stream().mapToDouble(ItemDetail::getPrice).sum();
			Date today = new Date();
			Timestamp timestamp = new Timestamp(today.getTime());
			preparedStatement.setInt(1, order.getUserId());
			preparedStatement.setTimestamp(2, timestamp);
			preparedStatement.setFloat(3, amount);
			preparedStatement.setString(4, order.getOrderStatus().toString());
			count = preparedStatement.executeUpdate();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			int id = -1;
			if (resultSet.next()) {
				id = resultSet.getInt(1);
			}
			if (count == 1) {
				PreparedStatement preparedStatement1 = conn
						.prepareStatement("INSERT INTO itemlist(orderid, itemid, quantity) VALUES(?,?,?)");
				order.setMessage("Insert Successfully");
				for (ItemDetail detail : order.getItemDetails()) {
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

		} catch (Exception e) {
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
				for (ItemDetail detail : order.getItemDetails()) {
					preparedStatement1.setInt(1, order.getOrderId());
					preparedStatement1.setInt(2, detail.getItemId());
					preparedStatement1.setInt(3, detail.getQuantity());
					preparedStatement1.executeUpdate();
				}
				preparedStatement1.close();
				final float amount = (float) order.getItemDetails().stream().mapToDouble(ItemDetail::getPrice).sum();
				preparedStatement1 = conn.prepareStatement("UPDATE ORDER SET amount = ? WHERE orderid = ?");
				preparedStatement1.setFloat(1, amount);
				preparedStatement1.setInt(2, order.getOrderId());
				preparedStatement.executeUpdate();
				preparedStatement1.close();
			}
			conn.commit();
			conn.setAutoCommit(true);
			order.setMessage("Order updated.");
		} catch (Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-3);
		}
		return order;
	}

	public Order deleteOrder(final Connection conn, final Order order) {
		try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM order WHERE orderid = ?")) {
			preparedStatement.setInt(1, order.getOrderId());
			int count = preparedStatement.executeUpdate();
			if (count >= 1) {
				order.setMessage("Order deleted.");
			} else {
				order.setMessage("Unable to delete.");
				order.setOptType(-4);
			}
		} catch (Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-4);
		}
		return order;
	}

	public void getAllOrder(final Connection conn, final Order order) {
		try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM order where orderid  = ?")) {
			preparedStatement.setInt(1, order.getOrderId());
		} catch (Exception e) {
			order.setMessage(e.getMessage());
			order.setOptType(-1);
		}
	}

}
