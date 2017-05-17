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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkerPanel frame = new WorkerPanel(null, null);
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
	public WorkerPanel(Worker logWor, AppController contr) {
	
		this.controller = contr;
		loggedWorker = logWor;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
					System.out.print("aaa");
					controller.logOut(loggedWorker);
				
			}
		});
		
		setTitle("Worker panel");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 564, 415);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoggedAs = new JLabel("Logged as: ");
		lblLoggedAs.setBounds(12, 35, 89, 33);
		contentPane.add(lblLoggedAs);
		
		JLabel loginDisplay = new JLabel(loggedWorker.getLogin());
		loginDisplay.setBounds(100, 35, 269, 33);
		contentPane.add(loginDisplay);
		
	
		JLabel lblLastTimeStart = new JLabel("Last time start work at: ");
		lblLastTimeStart.setBounds(12, 80, 177, 33);
		contentPane.add(lblLastTimeStart);
		
		String timeStart;
		if (loggedWorker.getStartTime() == null) {
			timeStart = " Haven't worked yet";
		} else {
			timeStart = loggedWorker.getStartTime();
		}
		
		JLabel sWorkTimeLabbelDisplay = new JLabel(timeStart);
		sWorkTimeLabbelDisplay.setBounds(204, 80, 231, 33);
		contentPane.add(sWorkTimeLabbelDisplay);
		
		JButton btnNewButton = new JButton("Start Work");
		btnNewButton.setBounds(48, 306, 117, 25);
		contentPane.add(btnNewButton);
		
		JButton btnLogOut = new JButton("Log out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.logOut(loggedWorker);
				MainMenu window = new MainMenu(controller);
				window.frame.setVisible(true);
				setVisible(false);
			}
		});
		btnLogOut.setBounds(379, 306, 117, 25);
		contentPane.add(btnLogOut);
	}

}
