package mainclasses;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import pojo.ItemDetail;
import pojo.Order;
import utility.CardValidation;
import utility.Constants.MenuType;
import utility.Constants.OrderStatus;

public class CustomerScreen extends JFrame implements ActionListener {

	// private DefaultListModel<ItemDetail> menuListModel;
	// private DefaultListModel<ItemDetail> selectedItemsListModel;

	// private JList<ItemDetail> menuList;
	// private JList<ItemDetail> selectedItemsList;

	private JScrollPane menuScrollPane;
	private JScrollPane selectedItemsScrollPane;
	private JScrollPane ordersScrollPane;

	private JTextField txtQuantity;

	private JLabel lblTotal;
	private JLabel lblQuantity;

	private JButton btnAddItem;
	private JButton btnProceedToPayment;
	private JButton btnDeleteSelectedItem;
	private JButton btnPlaceOrder;
	private JButton btnViewOrderItems;
	private JButton btnEditOrderItems;
	

	private float totalAmount;

	private DefaultTableModel menuTableModel;
	private DefaultTableModel selectedItemTableModel;
	private DefaultTableModel ordersTableModel;
	private JTable menuTable;
	private JTable selectedItemTable;
	private JTable ordersItemTable;

	List<ItemDetail> menuItems;
	List<ItemDetail> selectedItems;
	List<Order> myOrders;

	Order order;
	String cardNumber;
	ItemDetail itemDetail;
	CardValidation cardValidation;

	Client client;

	public CustomerScreen(Client client) {

		this.client = client;

		initializeUIComponents();
		doTheLayout();

		this.btnAddItem.addActionListener(this);
		this.btnProceedToPayment.addActionListener(this);
		this.btnDeleteSelectedItem.addActionListener(this);
		this.btnPlaceOrder.addActionListener(this);
		this.btnViewOrderItems.addActionListener(this);
		this.btnEditOrderItems.addActionListener(this);

		this.setTitle("Customer Screen - Place Order");
		//this.setSize(1300, 600);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Center the window

		displayMenu();
		loadMyOrders();

		this.selectedItems = new ArrayList<ItemDetail>();

	}

	public void initializeUIComponents() {
//		this.menuListModel = new DefaultListModel<>();
//		this.menuList = new JList<>(this.menuListModel);

		this.menuTableModel = new DefaultTableModel(new Object[] { "Item", "Type", "Price", "Description" }, 0);
		this.menuTable = new JTable(this.menuTableModel);
		this.menuTable.getColumnModel().getColumn(3).setCellRenderer(new MultiLineTableCellRenderer());
		this.menuTable.getColumnModel().getColumn(0).setPreferredWidth(30);
		this.menuTable.getColumnModel().getColumn(1).setPreferredWidth(10);
		this.menuTable.getColumnModel().getColumn(2).setPreferredWidth(5);
		this.menuTable.getColumnModel().getColumn(3).setPreferredWidth(50);
		this.menuTable.getColumnModel().getColumn(0).setCellRenderer(new TopAlignTableCellRenderer());
		this.menuTable.getColumnModel().getColumn(1).setCellRenderer(new TopAlignTableCellRenderer());
		this.menuTable.getColumnModel().getColumn(2).setCellRenderer(new TopAlignTableCellRenderer());
		// this.menuTable.getColumnModel().getColumn(3).setCellRenderer(new
		// TopAlignTableCellRenderer());

		this.menuScrollPane = new JScrollPane(this.menuTable);
		// this.menuScrollPane.setPreferredSize(new Dimension(300, 300));
		this.menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// this.selectedItemsListModel = new DefaultListModel<>();
		// this.selectedItemsList = new JList<>(this.selectedItemsListModel);

		this.selectedItemTableModel = new DefaultTableModel(new Object[] { "Item", "Quantity", "Price Per Unit" }, 0);
		this.selectedItemTable = new JTable(this.selectedItemTableModel);

		this.selectedItemsScrollPane = new JScrollPane(this.selectedItemTable);
		this.selectedItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.selectedItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.ordersTableModel = new DefaultTableModel(new Object[] { "OrderID", "Date", "Amount", "Status" }, 0);
		this.ordersItemTable = new JTable(this.ordersTableModel);
		this.ordersScrollPane = new JScrollPane(this.ordersItemTable);
		this.ordersScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.ordersScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.lblTotal = new JLabel("Total: $" + totalAmount);

		this.lblQuantity = new JLabel("Quantity:");
		this.txtQuantity = new JTextField("1");

		this.btnAddItem = new JButton("Add Item");
		this.btnProceedToPayment = new JButton("Proceed to Payment");
		this.btnDeleteSelectedItem = new JButton("Remove Item");
		this.btnPlaceOrder = new JButton("Place Order");
		this.btnPlaceOrder.setVisible(false);
		this.btnViewOrderItems = new JButton("View Order Items");
		this.btnEditOrderItems = new JButton("Edit Order");
		

	}

	public void doTheLayout() {
		JPanel menuPanel = new JPanel();
		JPanel selectedItemsPanel = new JPanel();
		JPanel ordersPanel = new JPanel();
		// JPanel bottomPanel = new JPanel();

		JPanel menuRow1 = new JPanel();
		JPanel menuRow2 = new JPanel();
		JPanel menuRow3 = new JPanel();
		JPanel itemRow1 = new JPanel();
		JPanel itemRow2 = new JPanel();
		JPanel itemRow2Inner = new JPanel();

		JPanel ordersRowBottom = new JPanel();
		JPanel orderRow1 = new JPanel();
		JPanel orderRow2 = new JPanel();
		
		
		menuRow1.add(this.menuScrollPane);

		JPanel menuRowBottom = new JPanel(new GridLayout(2, 1));
		menuRow2.add(this.lblQuantity);
		menuRow2.add(this.txtQuantity);
		// menuRow3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		menuRow3.add(this.btnAddItem);
		menuRowBottom.add(menuRow2);
		menuRowBottom.add(menuRow3);

		menuPanel.setLayout(new BorderLayout());
		menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
		menuPanel.add(new JPanel());
		menuPanel.add(menuRow1, BorderLayout.CENTER);
		menuPanel.add(menuRowBottom, BorderLayout.SOUTH);
		// menuPanel.add(menuRow3);

		itemRow1.add(this.selectedItemsScrollPane);
		itemRow2Inner.add(this.btnDeleteSelectedItem);
		itemRow2Inner.add(this.btnProceedToPayment);
		itemRow2Inner.add(this.btnPlaceOrder);
		
		itemRow2.setLayout(new GridLayout(2, 1));
		itemRow2.add(this.lblTotal);
		itemRow2.add(itemRow2Inner);

		selectedItemsPanel.setLayout(new BorderLayout());
		selectedItemsPanel.setBorder(BorderFactory.createTitledBorder("Cart"));
		selectedItemsPanel.add(new JPanel());
		selectedItemsPanel.add(itemRow1, BorderLayout.CENTER);
		selectedItemsPanel.add(itemRow2, BorderLayout.SOUTH);

		
		orderRow1.add(this.btnEditOrderItems);
		orderRow2.add(this.btnViewOrderItems);
		
		ordersRowBottom.setLayout(new GridLayout(2,1));
		ordersRowBottom.add(orderRow1);
		ordersRowBottom.add(orderRow2);
		
		ordersPanel.setLayout(new BorderLayout());
		ordersPanel.setBorder(BorderFactory.createTitledBorder("My Orders"));
		ordersPanel.add(new JPanel());
		ordersPanel.add(this.ordersScrollPane, BorderLayout.CENTER);
		ordersPanel.add(ordersRowBottom, BorderLayout.SOUTH);
		
		this.setLayout(new GridLayout(1, 3));
		this.add(menuPanel);
		this.add(selectedItemsPanel);
		this.add(ordersPanel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == this.btnAddItem) {
			addItem();
		} else if (e.getSource() == this.btnProceedToPayment) {
			proceedToPayment();
		} else if (e.getSource() == this.btnDeleteSelectedItem) {
			deleteSelectedItemFromCart();
		} else if (e.getSource() == this.btnPlaceOrder) {
			placeOrder();
		} else if (e.getSource() == this.btnViewOrderItems) {
			ViewOrderItems();
		}else if (e.getSource() == this.btnEditOrderItems) {
			EditOrderItems();
		}
		

	}

	public void deleteSelectedItemFromCart() {

		System.out.println("deleteSelectedItemFromCart");

		int selectedItemRow = this.selectedItemTable.getSelectedRow();
		// System.out.println("selectedItemRow = " +selectedItemRow);
		if (selectedItemRow != -1) {

			int numColumns = this.selectedItemTableModel.getColumnCount();
			Object[] rowData = new Object[numColumns];
			for (int i = 0; i < numColumns; i++) {
				rowData[i] = this.selectedItemTableModel.getValueAt(selectedItemRow, i);
			}

			String name = (String) rowData[0];
			int quantity = (int) rowData[1];
			float pricePerUnit = (float) rowData[2];

			this.selectedItemTableModel.removeRow(selectedItemRow);
			this.totalAmount -= (quantity * pricePerUnit);
			this.lblTotal.setText("Total: $" + totalAmount);

			deleteSelectedItemFromList(name);
		} else {
			JOptionPane.showMessageDialog(this, "Please select an item from the cart to remove.");
		}

	}

	public void deleteSelectedItemFromList(String name) {
		for (int i = 0; i < this.selectedItems.size(); i++) {

			if (this.selectedItems.get(i).getName().compareTo(name) == 0) {
				this.selectedItems.remove(i);
				break;
			}
		}

//		for(int i=0;i<this.selectedItems.size();i++) {
//			
//			System.out.println(this.selectedItems.get(i).toString());	
//		}
	}

	public void addItem() {

		int selectedItemRow = this.menuTable.getSelectedRow();
		if (selectedItemRow != -1) {
			ItemDetail selectedItem = this.menuItems.get(selectedItemRow);
			if (selectedItem != null) {

				// System.out.println("selected item =" + selectedItem.getName());
				int quantity = Integer.parseInt(this.txtQuantity.getText());
				selectedItem.setQuantity(quantity);

				Object[] rowData = new Object[3];
				rowData[0] = selectedItem.getName();
				rowData[1] = quantity;
				rowData[2] = selectedItem.getPrice();

				this.selectedItemTableModel.addRow(rowData);
				this.totalAmount += (selectedItem.getPrice() * quantity);
				this.lblTotal.setText("Total: $" + totalAmount);

				// System.out.println("selected item === " + selectedItem.toString());
				this.selectedItems.add(selectedItem);

//				for(int i=0;i<this.selectedItems.size();i++) {				
//					System.out.println(this.selectedItems.get(i).toString());	
//				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an item from the menu.");
		}

	}

	public void proceedToPayment() {
		boolean isValidCreditCard = false;
		if (this.selectedItems.size() == 0) {
			JOptionPane.showMessageDialog(this, "Please add items to your cart first");
			return;
		}

		while (!isValidCreditCard) {
			String creditCardNumber = JOptionPane.showInputDialog(this, "Please enter your credit card number:");
			// TODO
			// check for number format exception

			if (creditCardNumber != null) {
				this.cardValidation = new CardValidation();
				boolean isValid = this.cardValidation.aValidNumber(creditCardNumber);
				if (isValid) {
					this.cardNumber = creditCardNumber;
					this.btnProceedToPayment.setVisible(false);
					this.btnPlaceOrder.setVisible(true);
					isValidCreditCard = true;
					JOptionPane.showMessageDialog(this,
							"Credit Card details have been verified. Please proceed to place the order");
				} else {
					JOptionPane.showMessageDialog(this, "Invalid Credit Card number. Please enter again.");
				}

			} else {
				System.out.println("card string null");
			}
		}

	}

	public void placeOrder() {
		System.out.println("placeOrder");

		if (this.selectedItems.size() == 0) {
			JOptionPane.showMessageDialog(this, "Please add items to your cart first");
			return;
		}

		// details needed ---> user id, card number, total amount, selected items.
		this.order = new Order();
		this.order.setCardNumber(this.cardNumber);
		this.order.setItemDetails(this.selectedItems);
		this.order.setPrice(this.totalAmount);
		this.order.setOptType(2);
		this.order.setOrderStatus(OrderStatus.PLACED);
		// TODO:
		// update user id
		this.order.setUserId(2);

		System.out.println("order = " + this.order.toString());

		this.order = (Order) this.client.performAction(this.order);

		if (this.order != null && this.order.getOptType() > 0) {
			System.out.println("Message = " + this.order.getMessage());
			System.out.println("order id = " + this.order.getOrderId());
			JOptionPane.showMessageDialog(this, "Order Placed. Your unique order id = " + this.order.getOrderId());
			loadMyOrders();
		} else {
			JOptionPane.showMessageDialog(null, "Order failed. try again");

		}
	}

	public void displayMenu() {
		// this.menuItems = new ArrayList<>();

		this.menuItems = getMenuItems();

		for (ItemDetail item : menuItems) {

			Object[] itemObject = new Object[4];
			itemObject[0] = item.getName();
			itemObject[1] = item.getMenuType();
			itemObject[2] = item.getPrice();
			itemObject[3] = item.getDescription();

			this.menuTableModel.addRow(itemObject);
		}

	}

	@SuppressWarnings("unchecked")
	public List<ItemDetail> getMenuItems() {

		List<ItemDetail> items = new ArrayList<>();
		this.itemDetail = new ItemDetail(0, "", null, "", 0.0f, 1, "");
		items = (List<ItemDetail>) this.client.performAction(this.itemDetail);
		System.out.println("items list size = " + items.size());
		return items;
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getMyOrders(){
		List<Order> ordersList = new ArrayList<Order>();
		Order orderobj = new Order();
		orderobj.setOptType(1);
		//TODO - set user id
		orderobj.setUserId(2);
		
		ordersList = (List<Order>) this.client.performAction(orderobj);
		System.out.println("orders list size = " + ordersList.size());
		return ordersList;
	}

	public void ViewOrderItems() {
		System.out.println("ViewOrderItems");
	}

	public void EditOrderItems() {
		System.out.println("EditOrderItems");
	}
	public void loadMyOrders() {
		// this.menuItems = new ArrayList<>();

		this.myOrders = getMyOrders();
		this.ordersTableModel.setRowCount(0);
		for (Order order : myOrders) {
//		this.ordersTableModel = new DefaultTableModel(new Object[] { "OrderID", "Date", "Amount", "Status" }, 0);

			Object[] orderObject = new Object[4];
			orderObject[0] = order.getOrderId();
			orderObject[1] = order.getOrderDate();
			orderObject[2] = order.getPrice();
			orderObject[3] = order.getOrderStatus().toString();

			this.ordersTableModel.addRow(orderObject);
		}
	}

}

class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
	public MultiLineTableCellRenderer() {
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		setText(value != null ? value.toString() : "");
		adjustRowHeight(table, row, column);
		return this;
	}

	private void adjustRowHeight(JTable table, int row, int column) {
		int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
		setSize(new Dimension(cWidth, 1000)); // Set an arbitrary height to calculate the preferred height
		int prefH = getPreferredSize().height;
		if (table.getRowHeight(row) != prefH) {
			table.setRowHeight(row, prefH);
		}
	}
}

class TopAlignTableCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
		((JLabel) c).setVerticalAlignment(SwingConstants.TOP);
		return c;
	}
}
