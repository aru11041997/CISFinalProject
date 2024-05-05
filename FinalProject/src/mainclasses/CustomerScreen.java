package mainclasses;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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

	//private DefaultListModel<ItemDetail> menuListModel;
	//private DefaultListModel<ItemDetail> selectedItemsListModel;
	
	//private JList<ItemDetail> menuList;
	//private JList<ItemDetail> selectedItemsList;
	
	private JScrollPane menuScrollPane;
	private JScrollPane selectedItemsScrollPane;
	
	private JTextField txtQuantity;
	
	private JLabel lblTotal;
	private JLabel lblQuantity;
	
	private JButton btnAddItem;
	private JButton btnProceedToPayment;
	
	private double totalAmount;

	private DefaultTableModel menuTableModel;
	private DefaultTableModel selectedItemTableModel;
	private JTable menuTable;
	private JTable selectedItemTable;
	
	ArrayList<ItemDetail> menuItems;
	
	
	public CustomerScreen() {
		initializeUIComponents();
		doTheLayout();
		
		this.btnAddItem.addActionListener(this);
		this.btnProceedToPayment.addActionListener(this);

		this.setTitle("Customer Screen - Place Order");
		this.setSize(1000, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null); // Center the window

		displayMenu();
		
	}

	public void initializeUIComponents() {
//		this.menuListModel = new DefaultListModel<>();
//		this.menuList = new JList<>(this.menuListModel);
		
		this.menuTableModel = new DefaultTableModel(new Object[] {"Item", "Type", "Price", "Description"},0);
		this.menuTable = new JTable(this.menuTableModel);
        this.menuTable.getColumnModel().getColumn(3).setCellRenderer(new MultiLineTableCellRenderer());
        this.menuTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        this.menuTable.getColumnModel().getColumn(1).setPreferredWidth(10);
        this.menuTable.getColumnModel().getColumn(2).setPreferredWidth(10);
        this.menuTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        this.menuTable.getColumnModel().getColumn(0).setCellRenderer(new TopAlignTableCellRenderer());
        this.menuTable.getColumnModel().getColumn(1).setCellRenderer(new TopAlignTableCellRenderer());
        this.menuTable.getColumnModel().getColumn(2).setCellRenderer(new TopAlignTableCellRenderer());
        //this.menuTable.getColumnModel().getColumn(3).setCellRenderer(new TopAlignTableCellRenderer());
        
        
		this.menuScrollPane = new JScrollPane(this.menuTable);
		//this.menuScrollPane.setPreferredSize(new Dimension(300, 300));
		//this.menuScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.menuScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//this.selectedItemsListModel = new DefaultListModel<>();
        //this.selectedItemsList = new JList<>(this.selectedItemsListModel);
       
		this.selectedItemTableModel = new DefaultTableModel(new Object[] {"Item", "Quantity"},0);
		this.selectedItemTable = new JTable(this.selectedItemTableModel);
		
        this.selectedItemsScrollPane = new JScrollPane(this.selectedItemTable);
        this.selectedItemsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.selectedItemsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        
        this.lblTotal = new JLabel("Total: $" + totalAmount);
        
        this.lblQuantity = new JLabel("Quantity:");
        this.txtQuantity = new JTextField("1");
        
        this.btnAddItem = new JButton("Add Item");
        this.btnProceedToPayment = new JButton("Proceed to Payment");
        
        
	}

	public void doTheLayout() {
		JPanel menuPanel = new JPanel();
		JPanel selectedItemsPanel = new JPanel();
		//JPanel bottomPanel = new JPanel();
		
		JPanel menuRow1 = new JPanel();
		JPanel menuRow2 = new JPanel();
		JPanel menuRow3 = new JPanel();

		
		menuPanel.setLayout(new BorderLayout());
//		menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
//		menuPanel.add(this.menuScrollPane, BorderLayout.CENTER);
		
		//menuPanel.setLayout(new GridLayout(3,1));
		menuPanel.setBorder(BorderFactory.createTitledBorder("Menu"));
		
		menuRow1.add(this.menuScrollPane);
		
		JPanel menuRowBottom = new JPanel(new GridLayout(2,1));
		menuRow2.add(this.lblQuantity);
		menuRow2.add(this.txtQuantity);
		//menuRow3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		menuRow3.add(this.btnAddItem);
		menuRowBottom.add(menuRow2);
		menuRowBottom.add(menuRow3);
		
		menuPanel.add(new JPanel());
		menuPanel.add(menuRow1, BorderLayout.CENTER);
		menuPanel.add(menuRowBottom, BorderLayout.SOUTH);
		//menuPanel.add(menuRow3);
		

		//selectedItemsPanel.setLayout(new BorderLayout());
		
		selectedItemsPanel.setLayout(new GridLayout(3,1));
		selectedItemsPanel.setBorder(BorderFactory.createTitledBorder("Selected Items"));
		selectedItemsPanel.add(this.selectedItemsScrollPane);
		selectedItemsPanel.add(this.lblTotal);
		selectedItemsPanel.add(this.btnProceedToPayment);
		
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
        
		
		this.setLayout(new GridLayout(1,2));
		this.add(menuPanel);
		this.add(selectedItemsPanel);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.btnAddItem) {
			addItem();
		}else if(e.getSource() == this.btnProceedToPayment) {
			proceedToPayment();
		}
	}

	public void addItem() {
//		ItemDetail selectedItem = menuList.getSelectedValue();
//		
//        if (selectedItem != null) {
//        	System.out.println("selected item =" + selectedItem.getName());
//            int quantity = Integer.parseInt(this.txtQuantity.getText());
//            selectedItem.setQuantity(quantity);
//            this.selectedItemsListModel.addElement(selectedItem);
//            this.totalAmount += (selectedItem.getPrice() * quantity);
//            this.lblTotal.setText("Total: $" + totalAmount);
//        } else {
//            JOptionPane.showMessageDialog(this, "Please select an item from the menu.");
//        }
//        
        
        int selectedItemRow = this.menuTable.getSelectedRow();
        if (selectedItemRow != -1) {
        	ItemDetail selectedItem = this.menuItems.get(selectedItemRow);
            if(selectedItem!=null) {
            	System.out.println("selected item =" + selectedItem.getName());
            	 int quantity = Integer.parseInt(this.txtQuantity.getText());
                 
                 
            	 Object[] rowData = new Object[2];
            	 rowData[0] = selectedItem.getName();
            	 rowData[1] = quantity;
            	 
            	 this.selectedItemTableModel.addRow(rowData);
            	 this.totalAmount += (selectedItem.getPrice() * quantity);
                 this.lblTotal.setText("Total: $" + totalAmount);
            }
        }else {
            JOptionPane.showMessageDialog(this, "Please select an item from the menu.");
        }
        
        
        
	}
	public void proceedToPayment() {
//		PaymentScreen paymentScreen = new PaymentScreen(totalAmount);
//        paymentScreen.setVisible(true);
	}
	
	public void displayMenu() {
		this.menuItems = new ArrayList<>();
		
		menuItems.add(new ItemDetail(1, "Pepperoni Pizza", MenuType.NONVEG,"This is the 1st menu option - Pepporoni pizza of non veg category", 10.0f));
        menuItems.add(new ItemDetail(2, "Veg Extravaganza Pizza", MenuType.VEG,"This is the 2nd menu option - Veg Extravaganza of veg category", 5.0f));
        menuItems.add(new ItemDetail(3, "Cheese Pizza", MenuType.VEG,"This is the 3rd menu option - Margerita pizza of Veg category", 15.0f));
        menuItems.add(new ItemDetail(4, "Paneer Pizza", MenuType.VEGAN,"This is the 4th menu option - Paneer pizza of veg category", 18.0f));
        menuItems.add(new ItemDetail(4, "Paneer Pizza", MenuType.VEGAN,"This is the 4th menu option - Paneer pizza of veg category", 18.0f));
        menuItems.add(new ItemDetail(4, "Paneer Pizza", MenuType.VEGAN,"This is the 4th menu option - Paneer pizza of veg category", 18.0f));
        menuItems.add(new ItemDetail(3, "Cheese Pizza", MenuType.VEG,"This is the 3rd menu option - Margerita pizza of Veg category", 15.0f));
        menuItems.add(new ItemDetail(3, "Cheese Pizza", MenuType.VEG,"This is the 3rd menu option - Margerita pizza of Veg category", 15.0f));
        menuItems.add(new ItemDetail(3, "Cheese Pizza", MenuType.VEG,"This is the 3rd menu option - Margerita pizza of Veg category", 15.0f));

        
       
        
        for (ItemDetail item : menuItems) {
          
        	Object[] itemObject = new Object[4];
        	itemObject[0] = item.getName();
        	itemObject[1] = item.getMenuType();
        	itemObject[2] = item.getPrice();
        	itemObject[3] = item.getDescription();
        	
            this.menuTableModel.addRow(itemObject);
        }
        
	}
}

 class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
	 public MultiLineTableCellRenderer() {
	        setLineWrap(true);
	        setWrapStyleWord(true);
	        setOpaque(true);
	    }
	 public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	        ((JLabel) c).setHorizontalAlignment(SwingConstants.LEFT);
	        ((JLabel) c).setVerticalAlignment(SwingConstants.TOP);
	        return c;
	    }
	}
