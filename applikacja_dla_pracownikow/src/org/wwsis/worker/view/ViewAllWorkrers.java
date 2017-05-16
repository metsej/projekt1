package org.wwsis.worker.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;

import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewAllWorkrers extends JFrame {

	private JPanel contentPane;
	private AppController controller;
	private JList namesList;
	private JList latNamesList;
	private JList loginList; 
	private JList passwordList;
	private JList startList;
	private JList endList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewAllWorkrers frame = new ViewAllWorkrers(null);
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
	public ViewAllWorkrers( AppController contr) {
		
		this.controller = contr;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 950, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		 namesList = new JList();
		namesList.setBounds(19, 25, 128, 472);
		contentPane.add(namesList);
		
		latNamesList = new JList();
		latNamesList.setBounds(159, 25, 164, 472);
		contentPane.add(latNamesList);
		
		loginList = new JList();
		loginList.setBounds(335, 25, 167, 472);
		contentPane.add(loginList);
		
		passwordList = new JList();
		passwordList.setBounds(514, 25, 101, 472);
		contentPane.add(passwordList);
		
		startList = new JList();
		startList.setBounds(624, 25, 150, 472);
		contentPane.add(startList);
		
		endList = new JList();
		endList.setBounds(786, 25, 150, 472);
		contentPane.add(endList);
		
		updateLists();
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 10));
		lblNewLabel.setBounds(19, -2, 84, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setFont(new Font("Dialog", Font.BOLD, 10));
		lblLastName.setBounds(159, -2, 84, 15);
		contentPane.add(lblLastName);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 10));
		lblPassword.setBounds(514, -2, 84, 15);
		contentPane.add(lblPassword);
		
		JLabel lblLstTimeStatret = new JLabel("start work at");
		lblLstTimeStatret.setFont(new Font("Dialog", Font.BOLD, 10));
		lblLstTimeStatret.setBounds(624, -1, 84, 12);
		contentPane.add(lblLstTimeStatret);
		
		JLabel lblEndWorkAt = new JLabel("end work at");
		lblEndWorkAt.setFont(new Font("Dialog", Font.BOLD, 10));
		lblEndWorkAt.setBounds(786, -2, 84, 15);
		contentPane.add(lblEndWorkAt);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(492, 376, 58, -17);
		contentPane.add(btnNewButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateLists();
			}
		});
		updateButton.setBounds(819, 538, 117, 25);
		contentPane.add(updateButton);
		
	
		
		JLabel lblLogin = new JLabel("login");
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 10));
		lblLogin.setBounds(335, -2, 84, 15);
		contentPane.add(lblLogin);
	}
	
	private void updateLists() {
		namesList.setModel(getListModel("name"));
		latNamesList.setModel(getListModel("last_name"));
		loginList.setModel(getListModel("login"));
		passwordList.setModel(getListModel("pass"));
		startList.setModel(getListModel("start"));
		endList.setModel(getListModel("stop"));
	}
	
	private DefaultListModel<String> getListModel (String fieldName) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		
			if (fieldName.equals("name")) {
				for (Worker s : controller.getAllWorkers())  {
					listModel.addElement(s.getName());
				}	
			}
			
			if (fieldName.equals("last_name")) {
				for (Worker s : controller.getAllWorkers())  {
					listModel.addElement(s.getLastName());
				}	
			}
			
			if (fieldName.equals("login")) {
				for (Worker s : controller.getAllWorkers())  {
					listModel.addElement(s.getLogin());
				}	
			}
			
			if (fieldName.equals("pass")) {
				for (Worker s : controller.getAllWorkers())  {
					listModel.addElement(s.getPassword());
				}	
			}
			
			if (fieldName.equals("start")) {
				for (Worker s : controller.getAllWorkers())  {
					if (s.getStartTime() != null) {
						listModel.addElement(s.getStartTime());
					} else {
						listModel.addElement("    ");
					}
					
				}	
			}
			
			if (fieldName.equals("stop")) {
				for (Worker s : controller.getAllWorkers())  {
					if (s.getEndTime() != null) {
					listModel.addElement(s.getEndTime());
					} else {
						listModel.addElement("    ");
					}
					
				}	
			}
			
			return listModel;		
	}
}
