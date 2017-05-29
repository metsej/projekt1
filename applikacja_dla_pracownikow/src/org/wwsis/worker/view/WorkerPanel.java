package org.wwsis.worker.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;

import javax.swing.JLabel;
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
	private JButton btnLogOut;
	private JLabel lblLoggedAs;
	private JLabel loginDisplay;
	private JLabel lblLastTimeStart;
	private JLabel sWorkTimeLabbelDisplay;
	private JButton btnNewButton;

	
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
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		setLabelsAndButtons ();
		setLogOutActionListener();
		
		
	}
	
	private void setLogOutActionListener() {
		btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.logOut(loggedWorker);
				MainMenu.setBounds(getBounds());
				MainMenu window = new MainMenu(controller);
				window.frame.setVisible(true);
				setVisible(false);
				dispose();
			}
		});
		btnLogOut.setBounds(379, 306, 117, 25);
		contentPane.add(btnLogOut);
	}
	
	private void setLabelsAndButtons () {
		lblLoggedAs = new JLabel("Logged as: ");
		lblLoggedAs.setBounds(12, 35, 89, 33);
		contentPane.add(lblLoggedAs);
		
		 loginDisplay = new JLabel(loggedWorker.getLogin());
		loginDisplay.setBounds(100, 35, 269, 33);
		contentPane.add(loginDisplay);
		
	
		 lblLastTimeStart = new JLabel("Last time start work at: ");
		lblLastTimeStart.setBounds(12, 80, 177, 33);
		contentPane.add(lblLastTimeStart);
		
		String timeStart;
		if (loggedWorker.getStartTime() == null) {
			timeStart = " Haven't worked yet";
		} else {
			timeStart = loggedWorker.getStartTime();
		}
		
		sWorkTimeLabbelDisplay = new JLabel(timeStart);
		sWorkTimeLabbelDisplay.setBounds(204, 80, 231, 33);
		contentPane.add(sWorkTimeLabbelDisplay);
		
		JButton btnNewButton = new JButton("Start Work");
		btnNewButton.setBounds(48, 306, 117, 25);
		contentPane.add(btnNewButton);
		
	}

}
