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
import utility.Constants.MenuType;

public class CustomerScreen extends JFrame implements ActionListener {

	// private DefaultListModel<ItemDetail> menuListModel;
	// private DefaultListModel<ItemDetail> selectedItemsListModel;

	// private JList<ItemDetail> menuList;
	// private JList<ItemDetail> selectedItemsList;

	private JScrollPane menuScrollPane;
	private JScrollPane selectedItemsScrollPane;

	private JTextField txtQuantity;

	private JLabel lblTotal;
	private JLabel lblQuantity;

	private JButton btnAddItem;
	private JButton btnProceedToPayment;
	private JButton btnDeleteSelectedItem;

	private double totalAmount;

	private DefaultTableModel menuTableModel;
	private DefaultTableModel selectedItemTableModel;
	private JTable menuTable;
	private JTable selectedItemTable;

	List<ItemDetail> menuItems;
	List<ItemDetail> selectedItems;

	Client client;
	ItemDetail itemDetail;

	public CustomerScreen(Client client) {

		this.client = client;

		initializeUIComponents();
		doTheLayout();

		this.btnAddItem.addActionListener(this);
		this.btnProceedToPayment.addActionListener(this);
		this.btnDeleteSelectedItem.addActionListener(this);

		this.setTitle("Customer Screen - Place Order");
		this.setSize(1200, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Center the window

		displayMenu();

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
		// this.menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		// this.selectedItemsListModel = new DefaultListModel<>();
		// this.selectedItemsList = new JList<>(this.selectedItemsListModel);

		this.selectedItemTableModel = new DefaultTableModel(new Object[] { "Item", "Quantity","Price Per Unit"}, 0);
		this.selectedItemTable = new JTable(this.selectedItemTableModel);

		this.selectedItemsScrollPane = new JScrollPane(this.selectedItemTable);
		this.selectedItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		// this.selectedItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.lblTotal = new JLabel("Total: $" + totalAmount);

		this.lblQuantity = new JLabel("Quantity:");
		this.txtQuantity = new JTextField("1");

		this.btnAddItem = new JButton("Add Item");
		this.btnProceedToPayment = new JButton("Proceed to Payment");
		this.btnDeleteSelectedItem = new JButton("Remove Item");

	}

	
	public void doTheLayout() {
		JPanel menuPanel = new JPanel();
		JPanel selectedItemsPanel = new JPanel();
		// JPanel bottomPanel = new JPanel();

		JPanel menuRow1 = new JPanel();
		JPanel menuRow2 = new JPanel();
		JPanel menuRow3 = new JPanel();
		JPanel itemRow1 = new JPanel();
		JPanel itemRow2 = new JPanel();
		JPanel itemRow2Inner = new JPanel();

		menuPanel.setLayout(new BorderLayout());
//		menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
//		menuPanel.add(this.menuScrollPane, BorderLayout.CENTER);

		// menuPanel.setLayout(new GridLayout(3,1));
		menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

		menuRow1.add(this.menuScrollPane);

		JPanel menuRowBottom = new JPanel(new GridLayout(2, 1));
		menuRow2.add(this.lblQuantity);
		menuRow2.add(this.txtQuantity);
		// menuRow3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		menuRow3.add(this.btnAddItem);
		menuRowBottom.add(menuRow2);
		menuRowBottom.add(menuRow3);

		menuPanel.add(new JPanel());
		menuPanel.add(menuRow1, BorderLayout.CENTER);
		menuPanel.add(menuRowBottom, BorderLayout.SOUTH);
		// menuPanel.add(menuRow3);

		itemRow1.add(this.selectedItemsScrollPane);
		itemRow2Inner.add(this.btnDeleteSelectedItem);
		itemRow2Inner.add(this.btnProceedToPayment);
		itemRow2.setLayout(new GridLayout(2, 1));
		itemRow2.add(this.lblTotal);
		itemRow2.add(itemRow2Inner);

		selectedItemsPanel.setLayout(new BorderLayout());
		selectedItemsPanel.setBorder(BorderFactory.createTitledBorder("Cart"));
		selectedItemsPanel.add(new JPanel());
		selectedItemsPanel.add(itemRow1, BorderLayout.CENTER);
		selectedItemsPanel.add(itemRow2, BorderLayout.SOUTH);

//		selectedItemsPanel.setLayout(new FlowLayout());
//		selectedItemsPanel.add(this.selectedItemsScrollPane);
//		selectedItemsPanel.add(this.lblTotal);
//		selectedItemsPanel.add(this.btnProceedToPayment);

//		bottomPanel.setLayout(new GridLayout(2,2));
//		bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
//		bottomPanel.add(this.lblQuantity);
//        bottomPanel.add(this.txtQuantity);
//        bottomPanel.add(this.btnAddItem);
//        bottomPanel.add(this.lblTotal);

//        this.setLayout(new BorderLayout());
//        this.add(menuPanel, BorderLayout.WEST);
//        this.add(selectedItemsPanel, BorderLayout.CENTER);
//        this.add(bottomPanel, BorderLayout.SOUTH);
//        this.add(this.btnProceedToPayment, BorderLayout.EAST);

		this.setLayout(new GridLayout(1, 2));
		this.add(menuPanel);
		this.add(selectedItemsPanel);

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
		}
	}

	public void deleteSelectedItemFromCart() {

		System.out.println("deleteSelectedItemFromCart");

		int selectedItemRow = this.selectedItemTable.getSelectedRow();
		System.out.println("selectedItemRow = " +selectedItemRow);
		if (selectedItemRow != -1) {
			
			int numColumns = this.selectedItemTableModel.getColumnCount();
			Object[] rowData = new Object[numColumns];
			for (int i = 0; i < numColumns; i++) {
			    rowData[i] = this.selectedItemTableModel.getValueAt(selectedItemRow, i);
			}
			
			String name = (String)rowData[0];
			int quantity = (int)rowData[1];
			float pricePerUnit = (float)rowData[2];
			
			this.selectedItemTableModel.removeRow(selectedItemRow);
			this.totalAmount -= (quantity * pricePerUnit);
			this.lblTotal.setText("Total: $" + totalAmount);
			
			deleteSelectedItemFromList(name);
		}else {
			JOptionPane.showMessageDialog(this, "Please select an item from the cart to remove.");
		}
		
	}
	
	public void deleteSelectedItemFromList(String name) {		
		for(int i=0;i<this.selectedItems.size();i++) {
	
			if(this.selectedItems.get(i).getName().compareTo(name)==0) {
				this.selectedItems.remove(i);
				break;
			}		
		}
		
		for(int i=0;i<this.selectedItems.size();i++) {
			
			System.out.println(this.selectedItems.get(i).toString());	
		}
	}

	public void addItem() {

		int selectedItemRow = this.menuTable.getSelectedRow();
		if (selectedItemRow != -1) {
			ItemDetail selectedItem = this.menuItems.get(selectedItemRow);
			if (selectedItem != null) {
				
				
				
				System.out.println("selected item =" + selectedItem.getName());
				int quantity = Integer.parseInt(this.txtQuantity.getText());
				selectedItem.setQuantity(quantity);
				
				Object[] rowData = new Object[3];
				rowData[0] = selectedItem.getName();
				rowData[1] = quantity;
				rowData[2] = selectedItem.getPrice();

				this.selectedItemTableModel.addRow(rowData);
				this.totalAmount += (selectedItem.getPrice() * quantity);
				this.lblTotal.setText("Total: $" + totalAmount);
				
				System.out.println("selected item === " + selectedItem.toString());
				this.selectedItems.add(selectedItem);
				
				for(int i=0;i<this.selectedItems.size();i++) {				
					System.out.println(this.selectedItems.get(i).toString());	
				}
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select an item from the menu.");
		}

	}

	public void proceedToPayment() {
//		PaymentScreen paymentScreen = new PaymentScreen(totalAmount);
//        paymentScreen.setVisible(true);
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
