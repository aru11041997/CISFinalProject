package pojo;

import java.sql.Timestamp;
import java.util.List;

import utility.Constants.OrderStatus;

public class Order extends BaseAttributes {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ItemDetail> itemDetails;
	private int userId;
	private int orderId;
	private OrderStatus orderStatus;
	private float price;
	private String cardNumber;
	private Timestamp orderDate;

	public Order() {
		super();
	}

	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public Timestamp getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}

	@Override
	public String toString() {
		return "Order [itemDetails=" + itemDetails + ", userId=" + userId + ", orderId=" + orderId + ", orderStatus="
				+ orderStatus + ", price=" + price + ", cardNumber=" + cardNumber + ", orderDate=" + orderDate + ", optyp=" + this.getOptType() + "]";
	}

}
