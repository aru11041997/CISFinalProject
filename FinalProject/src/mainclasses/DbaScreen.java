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

	private JButton btnAddItem;
	private JButton btnUpdateItem;
	private JButton btnDeleteItem;
	private JButton btnClear;

	ObjectOutputStream clientOutputStream;
	ObjectInputStream clientInputStream;
	Client client;
	ItemDetail itemDetail;

	public DbaScreen(ObjectOutputStream os, ObjectInputStream is, Client client) {

		this.clientOutputStream = os;
		this.clientInputStream = is;
		this.client = client;

		initializeUIComponents();
		doTheLayout();

		this.btnAddItem.addActionListener(this);
		this.btnUpdateItem.addActionListener(this);
		this.btnDeleteItem.addActionListener(this);
		this.btnClear.addActionListener(this);

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

		this.btnAddItem = new JButton("Add Item");
		this.btnDeleteItem = new JButton("Delete Item");
		this.btnUpdateItem = new JButton("Update Item");
		this.btnClear = new JButton("Clear");

	}

	public void doTheLayout() {

		final JPanel leftPanel = new JPanel();

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

		JScrollPane scrollPane = new JScrollPane(this.menuTable);
		leftPanel.setLayout(new BorderLayout());
		leftPanel.add(scrollPane, BorderLayout.CENTER);

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

//	  private ArrayList<ItemDetail> getSampleItems() {
//	        ArrayList<ItemDetail> items = new ArrayList<>();
//
//	        items.add(new ItemDetail(1,"item 1", MenuType.VEG, "this is the description for 1st item which is a veg dish", 10.0f));
//	        items.add(new ItemDetail(2, "item 2", MenuType.VEGAN, "this is the description for 2nd item which is a vegan dish", 20.0f));
//	        items.add(new ItemDetail(3,"item 3", MenuType.NONVEG, "this is the description for 3rd item which is a non-veg dish", 15.0f));
//	        
//	        return items;
//	    }

	@SuppressWarnings("unchecked")
	public List<ItemDetail> getMenuItems() {

		List<ItemDetail> items = new ArrayList<>();
		this.itemDetail = new ItemDetail(0, "", null, "", 0.0f, 1, "");
		// public ItemDetail(int id, String name, MenuType menutype, String desc, float
		// price, int opType, String msg)

		items = (List<ItemDetail>) this.client.performAction(this.itemDetail);

		for (ItemDetail item : items) {
			System.out.println(item.toString());
		}

		return items;
	}

	public void updateMenuTable() {
		List<ItemDetail> items = getMenuItems();
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
	}

	public void AddItemButtonClicked() {
		System.out.println("AddItemButtonClicked");

		String name = this.txtName.getText();
		float price = Float.parseFloat(this.txtPrice.getText());
		String desc = this.textAreaDescription.getText();
		String type = this.cmbType.getSelectedItem().toString();
		// TODO
		// validations

		MenuType menutype = MenuType.valueOf(type);

		this.itemDetail = new ItemDetail(0, name, menutype, desc, price, 2, "");

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

		int itemID = Integer.parseInt(this.txtItemID.getText());
		// TODO
		// validation

		this.itemDetail = new ItemDetail(itemID, "", null, "", 0.0f, 4, "");
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
		
		System.out.println("DeleteItemButtonClicked");

		int itemID = Integer.parseInt(this.txtItemID.getText());
		String name = this.txtName.getText();
		float price = Float.parseFloat(this.txtPrice.getText());
		String desc = this.textAreaDescription.getText();
		String type = this.cmbType.getSelectedItem().toString();
		MenuType menutype = MenuType.valueOf(type);
		// TODO
		// validation

		this.itemDetail = new ItemDetail(itemID, name, menutype, desc,price, 3, "");
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
	
	
}
