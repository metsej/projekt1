package org.wwsis.worker.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import javax.swing.JPasswordField;
import java.awt.Font;

public class MainMenu {

	public JFrame frame;
	private JTextField loginTextField;
	private AppController controller;
	private final Action action = new SwingAction();
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the application.
	 */
	public MainMenu(AppController contr) {
		this.controller = contr;
		initialize();
	}
	
	public MainMenu() {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 564, 415);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		loginTextField = new JTextField();
		loginTextField.setBounds(149, 127, 261, 46);
		frame.getContentPane().add(loginTextField);
		loginTextField.setColumns(10);
		
		
		JLabel lblLogin = new JLabel("           Login");
		lblLogin.setBounds(52, 127, 99, 45);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("  Password");
		lblPassword.setBounds(62, 184, 89, 37);
		frame.getContentPane().add(lblPassword);
		
		JButton logInButton = new JButton("Log in");
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String login = loginTextField.getText();
					@SuppressWarnings("deprecation")
					String pass = passwordField.getText();
					
					if (controller.isAdmin(login, pass)) {

						frame.dispose();
						AdministratorPanel adminPanel = new AdministratorPanel(controller);
						adminPanel.setVisible(true);
						
					} else if (controller.isValidLogNPass(pass, login)) {
						frame.dispose();
						WorkerPanel workerPanel = new WorkerPanel(login, controller);
						workerPanel.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "wrong password or login");
					}
					
					} catch (Exception exc) {
					
				}
			}
		});
		logInButton.setBounds(221, 273, 117, 25);
		frame.getContentPane().add(logInButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 185, 261, 46);
		frame.getContentPane().add(passwordField);
		
		JLabel Banner = new JLabel("Main menu");
		Banner.setFont(new Font("Dialog", Font.BOLD, 19));
		Banner.setBounds(209, 35, 143, 37);
		frame.getContentPane().add(Banner);
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}
