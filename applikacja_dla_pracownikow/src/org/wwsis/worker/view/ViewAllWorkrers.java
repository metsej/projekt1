package org.wwsis.worker.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Pracownik;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList namesList = new JList();
		namesList.setBounds(19, 25, 108, 472);
		contentPane.add(namesList);
		namesList.setModel(getListModel("name"));
		
		JList latNamesList = new JList();
		latNamesList.setBounds(139, 25, 108, 472);
		contentPane.add(latNamesList);
		latNamesList.setModel(getListModel("last_name"));
		
		JList loginList = new JList();
		loginList.setBounds(259, 25, 108, 472);
		contentPane.add(loginList);
		loginList.setModel(getListModel("login"));
		
		JList passwordList = new JList();
		passwordList.setBounds(379, 25, 108, 472);
		contentPane.add(passwordList);
		passwordList.setModel(getListModel("pass"));
		
		JList startList = new JList();
		startList.setBounds(499, 25, 154, 472);
		contentPane.add(startList);
		startList.setModel(getListModel("start"));
		
		JList endList = new JList();
		endList.setBounds(665, 25, 154, 472);
		contentPane.add(endList);
		endList.setModel(getListModel("stop"));
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 10));
		lblNewLabel.setBounds(19, -2, 84, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setFont(new Font("Dialog", Font.BOLD, 10));
		lblLastName.setBounds(139, -2, 84, 15);
		contentPane.add(lblLastName);
		
		JLabel lblPassword = new JLabel("password");
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 10));
		lblPassword.setBounds(379, -2, 84, 15);
		contentPane.add(lblPassword);
		
		JLabel lblLstTimeStatret = new JLabel("start work at");
		lblLstTimeStatret.setFont(new Font("Dialog", Font.BOLD, 10));
		lblLstTimeStatret.setBounds(499, 1, 84, 12);
		contentPane.add(lblLstTimeStatret);
		
		JLabel lblEndWorkAt = new JLabel("end work at");
		lblEndWorkAt.setFont(new Font("Dialog", Font.BOLD, 10));
		lblEndWorkAt.setBounds(665, 0, 84, 15);
		contentPane.add(lblEndWorkAt);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(492, 376, 58, -17);
		contentPane.add(btnNewButton);
		
		JButton goBackButton = new JButton("Go back");
		goBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdministratorPanel adminPanel = new AdministratorPanel(controller);
				adminPanel.setVisible(true);
				setVisible(false);
			}
		});
		goBackButton.setBounds(702, 538, 117, 25);
		contentPane.add(goBackButton);
		
	
		
		JLabel lblLogin = new JLabel("login");
		lblLogin.setFont(new Font("Dialog", Font.BOLD, 10));
		lblLogin.setBounds(259, -2, 84, 15);
		contentPane.add(lblLogin);
	}
	
	private DefaultListModel<String> getListModel (String fieldName) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		
			if (fieldName.equals("name")) {
				for (Pracownik s : controller.listaPracownikow())  {
					listModel.addElement(s.getImie());
				}	
			}
			
			if (fieldName.equals("last_name")) {
				for (Pracownik s : controller.listaPracownikow())  {
					listModel.addElement(s.getNazwisko());
				}	
			}
			
			if (fieldName.equals("login")) {
				for (Pracownik s : controller.listaPracownikow())  {
					listModel.addElement(s.getLogin());
				}	
			}
			
			if (fieldName.equals("pass")) {
				for (Pracownik s : controller.listaPracownikow())  {
					listModel.addElement(s.getHaslo());
				}	
			}
			
			if (fieldName.equals("start")) {
				for (Pracownik s : controller.listaPracownikow())  {
					listModel.addElement(s.getCzasRozpoczecia());
				}	
			}
			
			if (fieldName.equals("stop")) {
				for (Pracownik s : controller.listaPracownikow())  {
					listModel.addElement(s.getCzasZakonczenia());
					
				}	
			}
			
			return listModel;		
	}
}
