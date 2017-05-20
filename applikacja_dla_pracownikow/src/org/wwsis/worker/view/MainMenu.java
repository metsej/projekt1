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
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;
import org.wwsis.worker.dataAccess.impl.JadisDataAccess;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Rectangle;

public class MainMenu {

	public  JFrame frame;
	private JTextField loginTextField;
	private AppController controller;
	private JPasswordField passwordField;
	private static Rectangle bounds;
	private static boolean wasSomeWindowDisplayed = false;
	private JButton logInButton;
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
		frame = new JFrame("Main menu");
		if (!wasSomeWindowDisplayed){
			frame.setBounds(100, 100, 564, 415);
		} else {
			frame.setBounds(bounds);
			frame.setSize(564, 415);
		}
		
		frame.getBounds();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		wasSomeWindowDisplayed = true;
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
					
				bounds = frame.getBounds();
			}
		});
		
		loginTextField = new JTextField();
		loginTextField.setBounds(149, 127, 261, 46);
		frame.getContentPane().add(loginTextField);
		loginTextField.setColumns(10);
		
		setLoginActionListener();
		
		
		JLabel lblLogin = new JLabel("           Login");
		lblLogin.setBounds(52, 127, 99, 45);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("  Password");
		lblPassword.setBounds(62, 184, 89, 37);
		frame.getContentPane().add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(149, 185, 261, 46);
		frame.getContentPane().add(passwordField);
		
	}
	
	public void setLoginActionListener() {
		logInButton = new JButton("Log in");
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String login = loginTextField.getText();
					@SuppressWarnings("deprecation")
					String pass = passwordField.getText();
					
					if (controller.isAdmin(login, pass)) {

						MainMenu.setBounds(frame.getBounds());
						frame.setVisible(false);
						frame.dispose();
						AdministratorPanel adminPanel = new AdministratorPanel(controller);
						adminPanel.setVisible(true);
						
					} else if (controller.isValidLogNPass(pass, login)) {
						MainMenu.setBounds(frame.getBounds());
						frame.dispose();
						Worker loggedWorker = controller.loadWorker(login);
						controller.saveStartTime(loggedWorker);
						WorkerPanel workerPanel = new WorkerPanel(loggedWorker, controller);
						workerPanel.setVisible(true);
					
					} else {
						JOptionPane.showMessageDialog(null, "wrong password or login", "Alert", JOptionPane.WARNING_MESSAGE);
					}
					
					} catch (Exception exc) {
					
				}
			}
		});
		logInButton.setBounds(221, 273, 117, 25);
		frame.getContentPane().add(logInButton);
	}
	
	public static Rectangle getBounds( ) {
		return bounds;
	}
	
	public static void setBounds (Rectangle b) {
		bounds = b;
	}
	

}
