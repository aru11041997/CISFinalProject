package mainclasses;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class ChefScreen extends JFrame implements ActionListener{

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
    
    private  JButton btnOrderReceived; 
    private  JButton btnOrderInProcess; 
    private  JButton btnOrderComplete; 
    
    ObjectOutputStream clientOutputStream;
	ObjectInputStream clientInputStream;
	Client client;
	
	
	public ChefScreen(ObjectOutputStream os, ObjectInputStream is, Client client) {
		// Create layout for Chef Screen

		this.clientOutputStream = os;
		this.clientInputStream = is;
		this.client = client;
		
		initializeUIComponents();
		doTheLayout();
		
		
        this.btnOrderComplete.addActionListener(this);
        this.btnOrderInProcess.addActionListener(this);
        this.btnOrderReceived.addActionListener(this);
        
        

        this.setTitle("Chef Screen - Manage Orders");
        this.setSize(1200, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window

       updateScreen();
	}
	
	public void initializeUIComponents() {
		
		  
        
        this.placedOrdersModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID/Name", "Items Ordered"}, 0);
        this.tablePlacedOrders = new JTable(this.placedOrdersModel);
        this.spPlacedOrders = new JScrollPane(this.tablePlacedOrders);
        
        this.receivedOrdersModel = new DefaultTableModel(new Object[]{"Order ID", "Customer ID/Name", "Items Ordered"}, 0);
        this.tableReceivedOrders = new JTable(this.receivedOrdersModel);
        this.spReceivedOrders = new JScrollPane(this.tableReceivedOrders);
        
        this.inProcessOrdersModel  = new DefaultTableModel(new Object[]{"Order ID", "Customer ID/Name", "Items Ordered"}, 0);
        this.tableInProcessOrders = new JTable(this.inProcessOrdersModel);
        this.spInProcessOrders = new JScrollPane(this.tableInProcessOrders);
        
        
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
        
        this.setLayout(new GridLayout(1,3));
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
		if(e.getSource() == this.btnOrderReceived) {
			MarkOrderAsReceived();
		}else if(e.getSource() == this.btnOrderInProcess) {
			MarkOderAsInProcess();
		}else if(e.getSource() == this.btnOrderComplete) {
			MarkOrderAsComplete();
		}
		
	}
	
	public void MarkOrderAsReceived() {
		//TODO
		System.out.println("MarkOrderAsReceived");
		
		int selectedRow = this.tablePlacedOrders.getSelectedRow();
		System.out.println("selectedRow = " + selectedRow);
        if (selectedRow != -1) {
            // Move data from placedOrdersModel to receivedOrdersModel
            Object[] rowData = new Object[3];
            for (int i = 0; i < 3; i++) {
                rowData[i] = placedOrdersModel.getValueAt(selectedRow, i);
            }
            receivedOrdersModel.addRow(rowData);
            placedOrdersModel.removeRow(selectedRow);
        }
        
	}

	public void MarkOderAsInProcess() {
		//TODO
		System.out.println("MarkOderAsInProcess");
		int selectedRow = this.tableReceivedOrders.getSelectedRow();
        if (selectedRow != -1) {
            // Move data from receivedOrdersModel to inProcessOrdersModel
            Object[] rowData = new Object[3];
            for (int i = 0; i < 3; i++) {
                rowData[i] = receivedOrdersModel.getValueAt(selectedRow, i);
            }
            inProcessOrdersModel.addRow(rowData);
            receivedOrdersModel.removeRow(selectedRow);
        }
	}
	
	public void MarkOrderAsComplete() {
		//TODO
		System.out.println("MarkOrderAsComplete");
		int selectedRow = this.tableInProcessOrders.getSelectedRow();
        if (selectedRow != -1) {
            // Remove data from inProcessOrdersModel
            inProcessOrdersModel.removeRow(selectedRow);
        }
	}
	
	public void updateScreen() {
		
		Object[] placedOrder1 = {"1", "101", "Pizza, Pasta"};
        Object[] placedOrder2 = {"2", "102", "Burger, Fries"};
        this.placedOrdersModel.addRow(placedOrder1);
        this.placedOrdersModel.addRow(placedOrder2);
        
        Object[] receivedOrder1 = {"3", "103", "Salad, Sandwich"};
        Object[] receivedOrder2 = {"4", "104", "Steak, Potatoes"};
        this.receivedOrdersModel.addRow(receivedOrder1);
        this.receivedOrdersModel.addRow(receivedOrder2);
        
        Object[] inProcessOrder1 = {"5", "105", "Sushi, Ramen"};
        Object[] inProcessOrder2 = {"6", "106", "Curry, Rice"};
        this.inProcessOrdersModel.addRow(inProcessOrder1);
        this.inProcessOrdersModel.addRow(inProcessOrder2);
        
	}
	
}
