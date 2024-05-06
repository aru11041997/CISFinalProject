package mainclasses;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import pojo.ItemDetail;
import pojo.Order;
import utility.Constants.OrderStatus;

public class ChefScreen extends JFrame implements ActionListener {

	private DefaultTableModel placedOrdersModel;
	private DefaultTableModel receivedOrdersModel;
	private DefaultTableModel inProcessOrdersModel;
	// private DefaultTableModel completedOrdersModel;

	private JTable tablePlacedOrders;
	private JTable tableReceivedOrders;
	private JTable tableInProcessOrders;

	private JScrollPane spPlacedOrders;
	private JScrollPane spReceivedOrders;
	private JScrollPane spInProcessOrders;

	private JButton btnOrderReceived;
	private JButton btnOrderInProcess;
	private JButton btnOrderComplete;

	Client client;
	Order order;
	List<Order> orderListPlaced;
	List<Order> orderListReceived;
	List<Order> orderListInProcess;
	List<Order> orderList;

	public ChefScreen(Client client) {
		// Create layout for Chef Screen

		this.client = client;
		System.out.println("usr type - " + this.client.getMainUserType() + "; user id - " + this.client.getMainUserId());

		initializeUIComponents();
		doTheLayout();

		this.btnOrderComplete.addActionListener(this);
		this.btnOrderInProcess.addActionListener(this);
		this.btnOrderReceived.addActionListener(this);

		this.setTitle("Chef Screen - Manage Orders");
		this.setSize(1200, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Center the window

		loadOrders();
	}

	public void initializeUIComponents() {

		this.placedOrdersModel = new DefaultTableModel(new Object[] { "Order ID", "Customer ID/Name", "Items Ordered" },
				0);
		this.tablePlacedOrders = new JTable(this.placedOrdersModel);
		this.spPlacedOrders = new JScrollPane(this.tablePlacedOrders);
		this.spPlacedOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.receivedOrdersModel = new DefaultTableModel(
				new Object[] { "Order ID", "Customer ID/Name", "Items Ordered" }, 0);
		this.tableReceivedOrders = new JTable(this.receivedOrdersModel);
		this.spReceivedOrders = new JScrollPane(this.tableReceivedOrders);
		this.spReceivedOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		this.inProcessOrdersModel = new DefaultTableModel(
				new Object[] { "Order ID", "Customer ID/Name", "Items Ordered" }, 0);
		this.tableInProcessOrders = new JTable(this.inProcessOrdersModel);
		this.spInProcessOrders = new JScrollPane(this.tableInProcessOrders);
		this.spInProcessOrders.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		this.btnOrderReceived = new JButton("Mark as Received");
		this.btnOrderInProcess = new JButton("Mark as In Process");
		this.btnOrderComplete = new JButton("Mark as Complete");

	}

	public void doTheLayout() {
//		JPanel panel = new JPanel();
//        panel.setLayout(new BorderLayout());
//
//        // Create a label for the Chef Screen
//        JLabel label = new JLabel("Chef Screen", SwingConstants.CENTER);
//        panel.add(label, BorderLayout.CENTER);
//        
//        // Add the panel to the ChefScreen
//        this.add(panel);
//        

		JPanel placedOrdersPanel = new JPanel();
		JPanel receivedOrdersPanel = new JPanel();
		JPanel inProcessOrdersPanel = new JPanel();

		placedOrdersPanel.setLayout(new BorderLayout());
		placedOrdersPanel.setBorder(BorderFactory.createTitledBorder("Placed Orders"));
		placedOrdersPanel.add(this.spPlacedOrders, BorderLayout.CENTER);
		placedOrdersPanel.add(this.btnOrderReceived, BorderLayout.SOUTH);

		receivedOrdersPanel.setLayout(new BorderLayout());
		receivedOrdersPanel.setBorder(BorderFactory.createTitledBorder("Received Orders"));
		receivedOrdersPanel.add(this.spReceivedOrders, BorderLayout.CENTER);
		receivedOrdersPanel.add(this.btnOrderInProcess, BorderLayout.SOUTH);

		inProcessOrdersPanel.setLayout(new BorderLayout());
		inProcessOrdersPanel.setBorder(BorderFactory.createTitledBorder("In Process Orders"));
		inProcessOrdersPanel.add(this.spInProcessOrders, BorderLayout.CENTER);
		inProcessOrdersPanel.add(this.btnOrderComplete, BorderLayout.SOUTH);

		this.setLayout(new GridLayout(1, 3));
		this.add(placedOrdersPanel);
		this.add(receivedOrdersPanel);
		this.add(inProcessOrdersPanel);
//        this.add(this.btnOrderReceived);
//        this.add(this.btnOrderInProcess);
//        this.add(this.btnOrderComplete);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnOrderReceived) {
			MarkOrderAsReceived();
		} else if (e.getSource() == this.btnOrderInProcess) {
			MarkOderAsInProcess();
		} else if (e.getSource() == this.btnOrderComplete) {
			MarkOrderAsComplete();
		}

	}

	public void MarkOrderAsReceived() {
		// TODO
		System.out.println("MarkOrderAsReceived");

		int selectedRow = this.tablePlacedOrders.getSelectedRow();
		System.out.println("selectedRow = " + selectedRow);
		int orderId;
		if (selectedRow != -1) {
			// Move data from placedOrdersModel to receivedOrdersModel
			Object[] rowData = new Object[3];

			for (int i = 0; i < 3; i++) {
				rowData[i] = placedOrdersModel.getValueAt(selectedRow, i);
			}
			orderId = (int) rowData[0];
			System.out.println("order id = " + orderId);
			Order orderObj = findOrderObject(orderId);
			System.out.println("orderObj = " + orderObj.toString());
			
			this.order = new Order();
			this.order.setOptType(5);
			this.order.setOrderId(orderObj.getOrderId());
			this.order.setOrderStatus(OrderStatus.RECEIVED);
			this.order.setMainUserId(this.client.getMainUserId());
			this.order.setMainUserType(this.client.getMainUserType());

			this.order = (Order) this.client.performAction(this.order);
			if (this.order != null && this.order.getOptType() > 0) {
				System.out.println("updated; message = " + this.order.getMessage());
				this.receivedOrdersModel.addRow(rowData);
				this.placedOrdersModel.removeRow(selectedRow);

				this.orderListReceived.add(orderObj);
				this.orderListPlaced.remove(orderObj);

			} else {
				JOptionPane.showMessageDialog(this, "Something went wrong");
			}

		}

	}

	public void MarkOderAsInProcess() {
		// TODO
		System.out.println("MarkOderAsInProcess");
		int selectedRow = this.tableReceivedOrders.getSelectedRow();
		int orderId;
		if (selectedRow != -1) {
			// Move data from receivedOrdersModel to inProcessOrdersModel
			Object[] rowData = new Object[3];
			for (int i = 0; i < 3; i++) {
				rowData[i] = receivedOrdersModel.getValueAt(selectedRow, i);
			}
			orderId = (int) rowData[0];

			Order orderObj = findOrderObject(orderId);
			
			this.order = new Order();
			this.order.setOptType(5);
			this.order.setOrderId(orderObj.getOrderId());
			this.order.setOrderStatus(OrderStatus.INPROCESS);
			this.order.setMainUserId(this.client.getMainUserId());
			this.order.setMainUserType(this.client.getMainUserType());

			this.order = (Order) this.client.performAction(this.order);
			
			if (this.order != null && this.order.getOptType() > 0) {
				System.out.println("updated; message = " + this.order.getMessage());

				this.inProcessOrdersModel.addRow(rowData);
				this.receivedOrdersModel.removeRow(selectedRow);

				
				this.orderListInProcess.add(orderObj);
				this.orderListReceived.remove(orderObj);
			}else {
				JOptionPane.showMessageDialog(this, "Something went wrong");
			}
			

		}
	}

	public void MarkOrderAsComplete() {
		// TODO
		System.out.println("MarkOrderAsComplete");
		int selectedRow = this.tableInProcessOrders.getSelectedRow();
		if (selectedRow != -1) {

			int orderId = (int) inProcessOrdersModel.getValueAt(selectedRow, 0);

			// Remove data from inProcessOrdersModel
			inProcessOrdersModel.removeRow(selectedRow);

			Order orderObj = findOrderObject(orderId);
			
			this.order = new Order();
			this.order.setOptType(5);
			this.order.setOrderId(orderObj.getOrderId());
			this.order.setOrderStatus(OrderStatus.COMPLETED);
			this.order.setMainUserId(this.client.getMainUserId());
			this.order.setMainUserType(this.client.getMainUserType());

			this.order = (Order) this.client.performAction(this.order);
			if (this.order != null && this.order.getOptType() > 0) {
				System.out.println("updated; message = " + this.order.getMessage());
				
				this.orderList.remove(orderObj);
				this.orderListInProcess.remove(orderObj);
			}else {
				JOptionPane.showMessageDialog(this, "Something went wrong");
			}
			

		}
	}

	public Order findOrderObject(int orderId) {
		Order orderObj = null;

		for (Order order : this.orderList) {
			if (order.getOrderId() == orderId) {
				orderObj = order;
				break;
			}
		}

		return orderObj;
	}

//	@SuppressWarnings("unchecked")
	public void loadOrders() {

		this.order = new Order();
		this.order.setOptType(6);
		this.order.setMainUserId(this.client.getMainUserId());
		this.order.setMainUserType(this.client.getMainUserType());

		//this.orderList = (List<Order>) this.client.performAction(this.order);
		this.orderList = new ArrayList<Order>();
		
		Object object = this.client.performAction(this.order);
		if (object instanceof List<?>) {
			List<?> orderlist = (List<?>) object;
			for (Object obj : orderlist) {
				if (obj instanceof Order) {
					this.orderList.add((Order) obj);
				}
			}
		}
		

		this.orderListInProcess = new ArrayList<Order>(0);
		this.orderListPlaced = new ArrayList<Order>(0);
		this.orderListReceived = new ArrayList<Order>(0);

		// order id, customer id, items
		for (Order orderObj : this.orderList) {

			// Object[] rowOrder = {orderObj.getOrderId(), orderObj.getUserId(), }
			Object[] rowObject = new Object[3];
			rowObject[0] = orderObj.getOrderId();
			rowObject[1] = orderObj.getUserId();

			String itemsOrdered = "";
			List<ItemDetail> items = orderObj.getItemDetails();

			for (int i = 0; i < items.size(); i++) {
				itemsOrdered = itemsOrdered + items.get(i).getQuantity() + " x " + items.get(i).getName() + "\n";
			}
			rowObject[2] = itemsOrdered;

			if (orderObj.getOrderStatus().equals(OrderStatus.PLACED)) {
				this.orderListPlaced.add(orderObj);
				this.placedOrdersModel.addRow(rowObject);

			} else if (orderObj.getOrderStatus().equals(OrderStatus.RECEIVED)) {
				this.orderListReceived.add(orderObj);
				this.receivedOrdersModel.addRow(rowObject);

			} else if (orderObj.getOrderStatus().equals(OrderStatus.INPROCESS)) {
				this.orderListInProcess.add(orderObj);
				this.inProcessOrdersModel.addRow(rowObject);
			}

		}

//		Object[] placedOrder1 = {"1", "101", "Pizza, Pasta"};
//        Object[] placedOrder2 = {"2", "102", "Burger, Fries"};
//        this.placedOrdersModel.addRow(placedOrder1);
//        this.placedOrdersModel.addRow(placedOrder2);
//        
//        Object[] receivedOrder1 = {"3", "103", "Salad, Sandwich"};
//        Object[] receivedOrder2 = {"4", "104", "Steak, Potatoes"};
//        this.receivedOrdersModel.addRow(receivedOrder1);
//        this.receivedOrdersModel.addRow(receivedOrder2);
//        
//        Object[] inProcessOrder1 = {"5", "105", "Sushi, Ramen"};
//        Object[] inProcessOrder2 = {"6", "106", "Curry, Rice"};
//        this.inProcessOrdersModel.addRow(inProcessOrder1);
//        this.inProcessOrdersModel.addRow(inProcessOrder2);

	}

}
