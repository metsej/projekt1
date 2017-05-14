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

public class SwingView {

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
					SwingView window = new SwingView();
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
	
	public SwingView(AppController contr) {
		this.controller = contr;
		initialize();
	}
	
	public SwingView() {
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
					
					if (controller.czyAdmin(login, pass)) {

						JOptionPane.showMessageDialog(null, "witaj w panelu administratora");
						
					} else if (controller.czyPoprawneHasloiLogin(pass, login)) {
						JOptionPane.showMessageDialog(null, "witaj w panelu użytkownika");
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
		
		JLabel Banner = new JLabel(" Application for employee");
		Banner.setFont(new Font("Dialog", Font.BOLD, 17));
		Banner.setBounds(142, 39, 268, 37);
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