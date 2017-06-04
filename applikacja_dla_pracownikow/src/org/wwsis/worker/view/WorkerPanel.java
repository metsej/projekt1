package org.wwsis.worker.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class WorkerPanel extends JFrame {

	private JPanel contentPane;
	private AppController controller;
	private Worker loggedWorker;
	private JLabel lblLoggedAs;
	private JLabel loginDisplay;
	private JLabel lblLastTimeStart;
	private JLabel sWorkTimeLabbelDisplay;
	private JMenuBar menuBar;
	private JMenu user, view;
	private JMenuItem logOut, udate;

	
	public WorkerPanel(Worker logWor, AppController contr) {
	
		this.controller = contr;
		loggedWorker = logWor;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {

					controller.logOut(loggedWorker);
					MainMenu.setBounds(getBounds());
			}
		});
		
		setTitle("Worker panel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(MainMenu.getBounds());
		setSize(564, 200);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLabelsAndButtons ();
		setMenu();
		
		
	}
	
		
	private void update(){
		sWorkTimeLabbelDisplay.setText(controller.getTodayWorkTime(loggedWorker));
	}
	
	
	private void setLabelsAndButtons () {
		lblLoggedAs = new JLabel("Logged as: ");
		lblLoggedAs.setBounds(12, 35, 89, 33);
		contentPane.add(lblLoggedAs);
		
		 loginDisplay = new JLabel(loggedWorker.getLogin());
		loginDisplay.setBounds(126, 35, 269, 33);
		contentPane.add(loginDisplay);
		
	
		lblLastTimeStart = new JLabel("Worked today:");
		lblLastTimeStart.setBounds(12, 80, 177, 33);
		contentPane.add(lblLastTimeStart);
		
		
		
		sWorkTimeLabbelDisplay = new JLabel(controller.getTodayWorkTime(loggedWorker));
		sWorkTimeLabbelDisplay.setBounds(126, 80, 97, 33);
		contentPane.add(sWorkTimeLabbelDisplay);
		
	}
	
	private void setMenu() {
		menuBar = new JMenuBar();
		view = new JMenu("View");
		user = new JMenu("User");
		assignAllJMenuItems();
		
		view.add(udate);
		user.add(logOut);
		menuBar.add(view);
		menuBar.add(user);
		add(menuBar);
		setJMenuBar(menuBar);

		
	}
	
	private void assignAllJMenuItems() {
		assignUpdate();
		assignLogOut();
		
	}

	
	
	private void assignUpdate() {
		udate = new JMenuItem("update");
		udate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
	}
	
	private void assignLogOut () {
		logOut = new JMenuItem("Log out");
		
		logOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//controller.logOut(loggedWorker);
				MainMenu.setBounds(getBounds());
				MainMenu window = new MainMenu(controller);
				window.frame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});

	} 
}
