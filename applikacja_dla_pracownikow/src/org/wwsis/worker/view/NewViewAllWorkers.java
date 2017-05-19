package org.wwsis.worker.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

import javax.swing.JTable;

public class NewViewAllWorkers extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	DefaultTableModel tableModel;
	private AppController controller;
	private String[][] data;
	String column[]={"LOGIN","NAME","LAST NAME", "PASSWORD", "STARTED WORK AT", "END WORK AT"}; 
	JMenuBar menuBar;   
	private JMenu user, edit;
	JMenuItem addNewWorker,changePassword, logOut;  
	private JScrollPane contentPane;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataAccess dao = new JadisDataAccess("localhost");
					AppController controller = new AppController ();
					controller.setDao(dao);
					NewViewAllWorkers frame = new NewViewAllWorkers(controller);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewViewAllWorkers(AppController contr) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				AdministratorPanel.setIsViewAllWorkersDispalyed(false);
			}
		});
		
		this.controller = contr;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		
		 menuBar = new JMenuBar();    
		 edit = new JMenu("Edit"); user = new JMenu("User");
		 addNewWorker = new JMenuItem("Add new user"); changePassword = new JMenuItem("Change password of some user"); 
		 new JMenuItem("Change password of some user");
		 logOut = new JMenuItem( "Log out");  
		 
		 
		 addNewWorker.addActionListener (new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					 String name = JOptionPane.showInputDialog(null,"Enter Name"); 
						String lastname = JOptionPane.showInputDialog(null,"Enter Last name"); 
						
						if (name.equals("") || lastname.equals("")) {
							JOptionPane.showMessageDialog(null, "You must insert name and lastname","Alert", JOptionPane.WARNING_MESSAGE);
						} else {
							
							Worker newWorker = controller.addAndGetNewWorker(name, lastname);
							
							JOptionPane.showMessageDialog(null, "You created user with login " + newWorker.getLogin()+ " and password: " + newWorker.getPassword());
							
							updateTable();
						}
						
				}
			});  
				
				
		 edit.add(addNewWorker); edit.add(changePassword); user.add(logOut);
		 menuBar.add(edit);  menuBar.add(user);
		
		 add(menuBar); 
		 setJMenuBar(menuBar);

		
		table = new JTable();
		table.setBounds(12, 0, 950, 200);
		setBounds(table.getBounds());
		
		//instance table model
		 tableModel = new DefaultTableModel(data, column) {

		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		updateTable ();
		table.setModel(tableModel);
		
		contentPane = new JScrollPane(table);
		
		add(contentPane);      
	}
	
	
	private void updateTable () {
		int n = controller.getAllWorkers().size();
		data = new String[n ][6];
		
		int i = 0;
		for (Worker curentWorker: controller.getAllWorkers())  {
			data[i][0] = curentWorker.getLogin();
			data[i][1] = curentWorker.getName();
			data[i][2] = curentWorker.getLastName();
			data[i][3] = curentWorker.getPassword();
			data[i][4] = curentWorker.getStartTime();
			data[i][5] = curentWorker.getEndTime();
			i++;
		}
		tableModel.setDataVector(data, column);
		
		
	}
		
		   
		
		
		
	
	
	
	
	
}
