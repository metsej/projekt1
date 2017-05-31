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
import javax.swing.JPanel;
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

	public JFrame frame;
	private JTextField loginTextField;
	private AppController controller;
	private JPasswordField passwordField;
	private boolean didExitFromMenu;
	private static Rectangle bounds;
	private static boolean wasSomeWindowDisplayed = false;
	private JButton logInButton;

	public MainMenu(AppController contr) {
		this.controller = contr;
		initialize();
	}
	
	public static Rectangle getBounds() {
		return bounds;
	}

	public static void setBounds(Rectangle b) {
		bounds = b;
	}
	
	private void initialize() {

		setFrame();

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
		didExitFromMenu = false;

	}

	private void setFrame() {
		frame = new JFrame("Main menu");
		if (!wasSomeWindowDisplayed) {
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

	}

	private void setLoginActionListener() {
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

					} else if (controller.doWorkerExists(Worker.withLogin(login))) {
						Worker worker = controller.loadWorker(login);
						
						if (!controller.isValidLogNPass(pass, login)) {
							JOptionPane.showMessageDialog(null, "wrong password or login", "Alert",
							JOptionPane.WARNING_MESSAGE);
							controller.incrementFailedLoggingAttempt(worker);
							if (worker.getNumOfFailedLogingAttempts() >= 3) {
								controller.blockUser(worker);
								JOptionPane.showMessageDialog(null, "You gave 3 times wrong passwor. You accound have been blocked", "Alert",
								JOptionPane.WARNING_MESSAGE);
							}
						} else if (worker.getIsBlocked()) {
							JOptionPane.showMessageDialog(null, "You were blocked. Contact administrator", "Alert",
									JOptionPane.WARNING_MESSAGE);
							
						} else {
							MainMenu.setBounds(frame.getBounds());
							frame.dispose();
							if (!worker.getDidLogedForTheFirstTime()) {
								forceToChangePassord(worker);
							}
							
							if (!didExitFromMenu) {
							controller.logIn(worker);
							WorkerPanel workerPanel = new WorkerPanel(worker, controller);
							workerPanel.setVisible(true);
							}
						}
						

					} else {
						JOptionPane.showMessageDialog(null, "wrong password or login", "Alert",
								JOptionPane.WARNING_MESSAGE);
					}

				} catch (Exception exc) {

				}
			}
		});
		logInButton.setBounds(221, 273, 117, 25);
		frame.getContentPane().add(logInButton);
	}
	
	private void forceToChangePassord(Worker worker) {

		Boolean wasPassChanged = false;
		String newPass = " ";
		String oldPass;
		String login = worker.getLogin();

		while (!wasPassChanged && !didExitFromMenu ) {

			while (!didExitFromMenu) {
				newPass = getPasswordFromUser ("Oligatory password change", "Insert new password");
				if (controller.isPassValid(newPass) || didExitFromMenu) {
					break;
				} else {
					JOptionPane.showMessageDialog(frame, "Wrong password format", "Alert", JOptionPane.WARNING_MESSAGE);
				}
			}

			while ( !didExitFromMenu) {
				oldPass = getPasswordFromUser ("Oligatory password change","Insert your old password");
				if (controller.isValidLogNPass(oldPass, login) || didExitFromMenu) {
					wasPassChanged = true;
					break;
				} else {
					JOptionPane.showMessageDialog(frame, "Wrong password ", "Alert", JOptionPane.WARNING_MESSAGE);

				}
			}

		}
		if (!didExitFromMenu) {
		controller.setNewPass(worker, newPass);
		}

	}
	
	private String getPasswordFromUser (String title, String TextContent) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(TextContent);
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(frame, panel, title,
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[1]);
		if(option == 0) // pressing OK button
		{
		    return new String (pass.getPassword());
		   
		} else {
			frame.dispose();
			didExitFromMenu = true;
			bounds = frame.getBounds();
			MainMenu newMainMenu = new MainMenu (controller);
			newMainMenu.frame.setVisible(true);
			return " ";
		}
	}


}
