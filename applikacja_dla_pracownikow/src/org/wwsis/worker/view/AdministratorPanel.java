package org.wwsis.worker.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.wwsis.worker.controller.AppController;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdministratorPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private AppController controller;
	private JTextField nameTextField;
	private JTextField lastNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorPanel frame = new AdministratorPanel(null);
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
	public AdministratorPanel(AppController contr) {
		
		this.controller = contr;
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 564, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAdministratorPanel = new JLabel("  Administrator Panel");
		lblAdministratorPanel.setFont(new Font("Dialog", Font.BOLD, 19));
		lblAdministratorPanel.setBounds(139, -49, 276, 118);
		contentPane.add(lblAdministratorPanel);
		
		JButton addNewEmployeeButton = new JButton("Add");
		addNewEmployeeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name = nameTextField.getText();
				String lastname = lastNameTextField.getText();
				
				if (name.equals("") || lastname.equals("")) {
					JOptionPane.showMessageDialog(null, "You must insert name and lastname");
				} else {
					controller.dodajPracownika(name, lastname);
				}
				
			}
		});
		addNewEmployeeButton.setBounds(364, 138, 117, 25);
		contentPane.add(addNewEmployeeButton);
		
		nameTextField = new JTextField();
		nameTextField.setColumns(10);
		nameTextField.setBounds(91, 100, 180, 26);
		contentPane.add(nameTextField);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(301, 100, 180, 26);
		contentPane.add(lastNameTextField);
		
		JLabel lblImie = new JLabel("Name");
		lblImie.setFont(new Font("DejaVu Serif", Font.BOLD, 14));
		lblImie.setBounds(91, 73, 70, 15);
		contentPane.add(lblImie);
		
		JLabel lblNazwisko = new JLabel("Last name");
		lblNazwisko.setFont(new Font("DejaVu Serif", Font.BOLD, 14));
		lblNazwisko.setBounds(301, 73, 96, 15);
		contentPane.add(lblNazwisko);
		
		JLabel lblNewLabel = new JLabel("Add new employee");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(172, 38, 202, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblListAllEmployee = new JLabel("List all employee");
		lblListAllEmployee.setFont(new Font("Dialog", Font.BOLD, 16));
		lblListAllEmployee.setBounds(192, 175, 202, 31);
		contentPane.add(lblListAllEmployee);
		
		JButton listAllButton = new JButton("List all");
		listAllButton.setBounds(364, 218, 117, 25);
		contentPane.add(listAllButton);
		
		JButton logOutButton = new JButton("Log out");
		logOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				SwingView window = new SwingView(controller);
				window.frame.setVisible(true);
				//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
		});
		logOutButton.setBounds(210, 305, 117, 25);
		contentPane.add(logOutButton);
	}
	
	

}
