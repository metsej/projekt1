package org.wwsis.worker.view;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
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
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

public class AdministratorPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	DefaultTableModel tableModel;
	private AppController controller;
	private String[][] data;
	String column[] = { "LOGIN", "FIRST NAME", "LAST NAME", "PASSWORD", "STARTED WORK AT", "END WORK AT" };
	JMenuBar menuBar;
	private JMenu user, edit;
	private JMenuItem addNewWorker;
	private JMenuItem changePassword;
	private JMenuItem logOut;
	private JMenuItem blockUser;
	private JMenuItem unBlockUser;
	private JScrollPane contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataAccess dao = new JadisDataAccess("localhost");
					AppController controller = new AppController();
					controller.setDao(dao);
					AdministratorPanel frame = new AdministratorPanel(controller);
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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		setTitle("Administrator panel");
		setMenu();

	}

	private void setMenu() {
		menuBar = new JMenuBar();
		edit = new JMenu("Edit");
		user = new JMenu("User");

		assignAllJMenuItems();

		edit.add(addNewWorker);
		edit.add(changePassword);
		edit.add(blockUser);
		edit.add(unBlockUser);
		edit.add(changePassword);
		user.add(logOut);
		menuBar.add(edit);
		menuBar.add(user);

		add(menuBar);
		setJMenuBar(menuBar);

		table = new JTable();
		setBounds(MainMenu.getBounds());
		setSize(950, 300);
		tableModel = new DefaultTableModel(data, column) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// all cells false
				return false;
			}
		};

		updateTable();
		table.setModel(tableModel);
		ActionListener listener = new ActionListener() {
			 public void actionPerformed(ActionEvent event) {
			 doCopy();
			 }//end actionPerformed(ActionEvent)

		};

		final KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK, false);
		table.registerKeyboardAction(listener, "Copy", stroke, JComponent.WHEN_FOCUSED);
		contentPane = new JScrollPane(table);
		add(contentPane);

	}
		
	private void doCopy() {
		int col = table.getSelectedColumn();
		int row = table.getSelectedRow();
		if (col != -1 && row != -1) {
			Object value = table.getValueAt(row, col);
			String data;
			if (value == null) {
				data = "";
			} else {
				data = value.toString();
			} // end if

			final StringSelection selection = new StringSelection(data);

			final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
		} // end if
	}
		
	private void assignAllJMenuItems() {
		assingAddNewWorker();
		assingLogOut();
		assignChangePassword();
		assignUnBlockUser();
		assignBlockUser();
		assignUnBlockUser();
	}

	private void assingAddNewWorker() {

		addNewWorker = new JMenuItem("Add new user");
		addNewWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Enter Name");
				String lastname = JOptionPane.showInputDialog(null, "Enter Last name");

				if (name.equals("") || lastname.equals("") || name.contains(" ") || lastname.contains(" ") ) {
					JOptionPane.showMessageDialog(null, "Incorrect name or lastname", "Alert",
							JOptionPane.WARNING_MESSAGE);
				} else {

					Worker newWorker = controller.addAndGetNewWorker(name, lastname);

					JOptionPane.showMessageDialog(null, "You created user with login " + newWorker.getLogin()
							+ " and password: " + newWorker.getPassword());

					updateTable();
				}

			}
		});
	}

	private void assingLogOut() {

		logOut = new JMenuItem("Log out");
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainMenu.setBounds(getBounds());
				MainMenu window = new MainMenu(controller);
				window.frame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});

	}

	private void assignChangePassword() {
		changePassword = new JMenuItem("Change password of some user");
		changePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = JOptionPane.showInputDialog(null, "Insert login");
				Worker potencialWorker = Worker.withLogin(login);

				if (controller.doWorkerExists(potencialWorker)) {

					controller.generateNewPass(login);
					Worker actualWorker = controller.loadWorker(login);

					JOptionPane.showMessageDialog(null,
							"New paswword for user with login " + login + " is : " + actualWorker.getPassword());

					updateTable();
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect login", "Alert", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}
	
	private void assignBlockUser() {
		blockUser = new JMenuItem("Block user");
		blockUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = JOptionPane.showInputDialog(null, "Insert login");
				Worker potencialWorker = Worker.withLogin(login);

				if (controller.doWorkerExists(potencialWorker)) {

					
					Worker actualWorker = controller.loadWorker(login);
					controller.blockUser(actualWorker);

					JOptionPane.showMessageDialog(null,
							"User with login " + login + " is blocked now");

					updateTable();
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect login", "Alert", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}
	
	private void assignUnBlockUser() {
		unBlockUser = new JMenuItem("unblock user");
		unBlockUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login = JOptionPane.showInputDialog(null, "Insert login");
				Worker potencialWorker = Worker.withLogin(login);

				if (controller.doWorkerExists(potencialWorker)) {

					
					Worker actualWorker = controller.loadWorker(login);
					controller.unBlockUser(actualWorker);
					controller.resetFailedLoggingAttempt(actualWorker);

					JOptionPane.showMessageDialog(null,
							" User with login " + login + " is unblocked now");

					updateTable();
				} else {
					JOptionPane.showMessageDialog(null, "Incorrect login", "Alert", JOptionPane.WARNING_MESSAGE);
				}
			}
		});

	}

	private void updateTable() {
		int n = controller.getAllWorkers().size();
		data = new String[n][6];

		int i = 0;
		for (Worker curentWorker : controller.getAllWorkers()) {
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
