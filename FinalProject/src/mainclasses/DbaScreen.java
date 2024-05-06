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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import pojo.ItemDetail;
import utility.Constants;
import utility.Constants.MenuType;
import utility.Constants.UserType;

public class DbaScreen extends JFrame implements ActionListener {

	private JLabel lblName;
	private JLabel lblType;
	private JLabel lblDescription;
	private JLabel lblPrice;
	private JLabel lblMenu;
	private JLabel lblItemID;

	private JTextField txtName;
	private JTextField txtPrice;
	private JTextField txtItemID;

	private JComboBox cmbType;

	private JTextArea textAreaDescription;
//	private JTextArea textAreaMenu;
//	private JScrollPane jp;

	private JTable menuTable;
	private DefaultTableModel tableModel;
	private JScrollPane menuScrollPane;

	private JButton btnAddItem;
	private JButton btnUpdateItem;
	private JButton btnDeleteItem;
	private JButton btnClear;
	//private JButton btnViewItemDetails;
	

	Client client;
	ItemDetail itemDetail;

	public DbaScreen( Client client) {
		System.out.println("DBA constructor");
		this.client = client;
		System.out.println("usr type - " + this.client.getMainUserType() + "; user id - " + this.client.getMainUserId());
		
		initializeUIComponents();
		doTheLayout();

		this.btnAddItem.addActionListener(this);
		this.btnUpdateItem.addActionListener(this);
		this.btnDeleteItem.addActionListener(this);
		this.btnClear.addActionListener(this);
		//this.btnViewItemDetails.addActionListener(this);

		this.setTitle("DBA Screen - menu management");
		// this.setSize(400, 400);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Center the window

		updateMenuTable();

	}

	public void initializeUIComponents() {

		this.lblName = new JLabel("Item Name");
		this.lblType = new JLabel("Type");
		this.lblPrice = new JLabel("Price");
		this.lblDescription = new JLabel("Description");
		this.lblMenu = new JLabel("Menu details");
		this.lblItemID = new JLabel("Item ID");

		this.txtName = new JTextField(10);
		this.txtPrice = new JTextField(5);
		this.txtItemID = new JTextField(5);
		this.textAreaDescription = new JTextArea(2, 20);

		String[] menuTypes = getMenuTypes();
		this.cmbType = new JComboBox<String>(menuTypes);

//		this.textAreaMenu = new JTextArea("Menu Details",10,50);
//		this.textAreaMenu.setEditable(false);
//		this.jp = new JScrollPane(textAreaMenu);
//		this.jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		this.jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.tableModel = new DefaultTableModel(new Object[] { "Item ID", "Name", "Price", "Type", "Description" }, 0);
		this.menuTable = new JTable(tableModel);
		this.menuScrollPane = new JScrollPane(this.menuTable);
		this.menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		

		this.btnAddItem = new JButton("Add Item");
		this.btnDeleteItem = new JButton("Delete Item");
		this.btnUpdateItem = new JButton("Update Item");
		this.btnClear = new JButton("Clear");
		//this.btnViewItemDetails = new JButton("View Details");

	}

	public void doTheLayout() {

		final JPanel leftPanel = new JPanel();
		final JPanel leftPanelTop = new JPanel();
		final JPanel leftPanelBottom = new JPanel();

		final JPanel rightPanelTop = new JPanel();
		final JPanel rightPanelBottom = new JPanel();
		final JPanel rightPanel = new JPanel();

		rightPanelTop.setLayout(new GridLayout(5, 2));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rightPanelTop.add(this.lblItemID);
		rightPanelTop.add(this.txtItemID);

		rightPanelTop.add(this.lblName);
		rightPanelTop.add(this.txtName);

		rightPanelTop.add(this.lblPrice);
		rightPanelTop.add(this.txtPrice);

		rightPanelTop.add(this.lblType);
		rightPanelTop.add(this.cmbType);

		rightPanelTop.add(this.lblDescription);
		rightPanelTop.add(this.textAreaDescription);

		rightPanelBottom.add(this.btnAddItem);
		rightPanelBottom.add(this.btnUpdateItem);
		rightPanelBottom.add(this.btnDeleteItem);
		rightPanelBottom.add(this.btnClear);

		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(new JPanel(), BorderLayout.NORTH);
		rightPanel.add(rightPanelTop, BorderLayout.CENTER);
		rightPanel.add(rightPanelBottom, BorderLayout.SOUTH);

		leftPanelTop.add(this.menuScrollPane);
		//leftPanelBottom.add(this.btnViewItemDetails);
		
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createTitledBorder("Menu"));

		leftPanel.add(leftPanelTop, BorderLayout.CENTER);
		leftPanel.add(leftPanelBottom, BorderLayout.SOUTH);

		this.setLayout(new GridLayout(1, 2));
		this.add(leftPanel);
		this.add(rightPanel);

	}

	public String[] getMenuTypes() {
		Constants.MenuType[] menuTypes = Constants.MenuType.values();
		String[] menuTypeStrings = new String[menuTypes.length];
		for (int i = 0; i < menuTypes.length; i++) {
			menuTypeStrings[i] = menuTypes[i].toString();
		}
		System.out.println(menuTypeStrings.toString());
		return menuTypeStrings;
	}



	@SuppressWarnings("unchecked")
	public List<ItemDetail> getMenuItems() {

		List<ItemDetail> items = new ArrayList<>();
		this.itemDetail = new ItemDetail(0, "", null, "", 0.0f, 1, "", this.client.getMainUserId(), this.client.getMainUserType());
		//items = (List<ItemDetail>) this.client.performAction(this.itemDetail);
		
		Object object = this.client.performAction(this.itemDetail);
		if (object instanceof List<?>) {
			List<?> itemList = (List<?>) object;
			//System.out.println("object list size = " + itemList.size());
			for (Object obj : itemList) {
				if (obj instanceof ItemDetail) {
					//System.out.println(obj.toString());
					items.add((ItemDetail) obj);
				}
			}
		}
		
		//items = getSampleItems();
		System.out.println("items list size = " + items.size());

		return items;
	}

	public void updateMenuTable() {
		System.out.println("updateMenuTable");
		List<ItemDetail> items = getMenuItems();
		tableModel.setRowCount(0);
		for (ItemDetail item : items) {
			Object[] rowData = { item.getItemId(), item.getName(), item.getPrice(), item.getMenuType(),
					item.getDescription() };
			tableModel.addRow(rowData);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == this.btnAddItem) {
			AddItemButtonClicked();
		} else if (e.getSource() == this.btnDeleteItem) {
			DeleteItemButtonClicked();
		} else if (e.getSource() == this.btnUpdateItem) {
			UpdateItemButtonClicked();
		} else if (e.getSource() == this.btnClear) {
			ClearButtonClicked();
		}
//		else if (e.getSource() == this.btnViewItemDetails) {
//			ViewItemDetails();
//		}
		
	}
	

	public void AddItemButtonClicked() {
		System.out.println("AddItemButtonClicked");

		
		
		if(!this.txtName.getText().trim().matches("^[a-zA-Z0-9\\s-_]+$")) {
			JOptionPane.showMessageDialog(this.txtName, "Invalid Name Format");//first parameter is the corresponding text field - parent component parameter for this method.
			this.txtName.setText("");
			return;
		}
		String name = this.txtName.getText().trim();
		
		float price;
		try {
			price = Float.parseFloat(this.txtPrice.getText().trim());
			if(!this.txtPrice.getText().trim().matches("^\\d+(\\.\\d{1,2})?$"))
				throw new Exception();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.txtPrice, "Invalid Number Format");
			this.txtPrice.setText("");
			return;
		}
		
		
		if(this.textAreaDescription.getText().trim()==null || this.textAreaDescription.getText().trim().equalsIgnoreCase("")) {
			JOptionPane.showMessageDialog(this.textAreaDescription, "Please provide a small description");
			this.textAreaDescription.setText("");
			return;
		}
		String desc = this.textAreaDescription.getText().trim();
			
		String type = this.cmbType.getSelectedItem().toString();
		MenuType menutype = MenuType.valueOf(type);

		this.itemDetail = new ItemDetail(0, name, menutype, desc, price, 2, "",this.client.getMainUserId(), this.client.getMainUserType());

		this.itemDetail = (ItemDetail) this.client.performAction(this.itemDetail);

		if (this.itemDetail != null && this.itemDetail.getOptType() > 0) {
			System.out.println("message = " + this.itemDetail.getMessage());
			JOptionPane.showMessageDialog(null, "Item Added Successfully");
			ClearButtonClicked();
			updateMenuTable();
		} else {
			JOptionPane.showMessageDialog(null, "Item Addition Failed");
		}

	}

	public void DeleteItemButtonClicked() {
		System.out.println("DeleteItemButtonClicked");

		int itemID;
		try {
			itemID = Integer.parseInt(this.txtItemID.getText().trim());
			if(!this.txtItemID.getText().trim().matches("^\\d+$"))
				throw new Exception();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.txtItemID, "Invalid Number Format");
			this.txtItemID.setText("");
			return;
		}

		this.itemDetail = new ItemDetail(itemID, "", null, "", 0.0f, 4, "",this.client.getMainUserId(), this.client.getMainUserType());
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you wish to delete the menu item with Id = " + itemID, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (option == JOptionPane.YES_NO_OPTION) {
			// proceed with deletion
			this.itemDetail = (ItemDetail) this.client.performAction(this.itemDetail);

			if(this.itemDetail!=null && this.itemDetail.getOptType()>0) {
				
				System.out.println(this.itemDetail.getMessage());
				
				JOptionPane.showMessageDialog(null, "Delete Successful.");
				
				ClearButtonClicked();
				//call clear text fields method.
				updateMenuTable();
			}
		} else if (option == JOptionPane.NO_OPTION) {
			// stop the deletion
			System.out.println("deleted stopped");
			

		} else if (option == JOptionPane.CANCEL_OPTION) {
			System.out.println("delete cancelled");
		} else {
			System.out.println("Dialog closed without a choice.");
		}

	}

	public void UpdateItemButtonClicked() {
		System.out.println("UpdateItemButtonClicked");

		int itemID;
		try {
			itemID = Integer.parseInt(this.txtItemID.getText().trim());
			if(!this.txtItemID.getText().trim().matches("^\\d+$"))
				throw new Exception();
		}catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this.txtItemID, "Invalid Number Format");
			this.txtItemID.setText("");
			return;
		}
		
		String name="";
		if(this.txtName.getText().trim()!=null && !this.txtName.getText().trim().equalsIgnoreCase("")) {
			if(!this.txtName.getText().trim().matches("^[a-zA-Z0-9\\s-_]+$")) {
				JOptionPane.showMessageDialog(this.txtName, "Invalid Name Format");//first parameter is the corresponding text field - parent component parameter for this method.
				this.txtName.setText("");
				return;
			}
			name = this.txtName.getText().trim();
		}
		
		float price=0;
		if(this.txtPrice.getText().trim()!=null && !this.txtPrice.getText().trim().equalsIgnoreCase("")) {
			try {
				price = Float.parseFloat(this.txtPrice.getText().trim());
				if(!this.txtPrice.getText().trim().matches("^\\d+(\\.\\d{1,2})?$"))
					throw new Exception();
			}catch(Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this.txtPrice, "Invalid Number Format");
				this.txtPrice.setText("");
				return;
			}
		}
		
			
		String desc="";
		if(this.textAreaDescription.getText().trim()!=null && !this.textAreaDescription.getText().trim().equalsIgnoreCase("")) {
			desc = this.textAreaDescription.getText().trim();
		}
		
		String type = this.cmbType.getSelectedItem().toString();
		MenuType menutype = MenuType.valueOf(type);
		

		this.itemDetail = new ItemDetail(itemID, name, menutype, desc,price, 3, "",this.client.getMainUserId(), this.client.getMainUserType());
		int option = JOptionPane.showConfirmDialog(null, "Are you sure you wish to update the menu item with Id = " + itemID, "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (option == JOptionPane.YES_NO_OPTION) {
			// proceed with deletion
			this.itemDetail = (ItemDetail) this.client.performAction(this.itemDetail);

			if(this.itemDetail!=null && this.itemDetail.getOptType()>0) {
				
				System.out.println(this.itemDetail.getMessage());
				
				JOptionPane.showMessageDialog(null, "Update Successful.");
				
				
				//call clear text fields method.
				ClearButtonClicked();
				
				updateMenuTable();
			}
		} else if (option == JOptionPane.NO_OPTION) {
			// stop the deletion
			System.out.println("update stopped");		

		} else if (option == JOptionPane.CANCEL_OPTION) {
			System.out.println("update cancelled");
		} else {
			System.out.println("Dialog closed without a choice.");
		}
	}

	public void ClearButtonClicked() {
		System.out.println("ClearButtonClicked");
		
		this.txtItemID.setText("");
		this.txtName.setText("");
		this.txtPrice.setText("");
		this.textAreaDescription.setText("");
		this.cmbType.setSelectedIndex(0);
		
	}
	
//	public void ViewItemDetails() {
//		System.out.println("ViewItemDetails");
//		
//		int selectedItemRow = this.menuTable.getSelectedRow();
//		if(selectedItemRow!=-1) {
//			
//			//{ "Item ID", "Name", "Price", "Type", "Description" }
//			
//			int itemId = (int) this.tableModel.getValueAt(selectedItemRow, 0);
//			String name = (String) this.tableModel.getValueAt(selectedItemRow, 1);
//			float price = (float) this.tableModel.getValueAt(selectedItemRow, 2);
//			MenuType type = (MenuType) this.tableModel.getValueAt(selectedItemRow, 3);
//			String desc = (String) this.tableModel.getValueAt(selectedItemRow, 4);
//			
//			this.txtItemID.setText(String.valueOf(itemId));
//			this.txtName.setText(name);
//			this.txtPrice.setText(String.valueOf(price));
//			this.textAreaDescription.setText(desc);
//
//			String menutype = type.toString();
//			this.cmbType.setSelectedItem(menutype);
//			
//			
//		}else {
//			JOptionPane.showMessageDialog(this, "Please select an item from the menu to view its details");
//
//		}
//	}
	
}
