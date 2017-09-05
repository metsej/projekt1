package org.wwsis.worker.view.jframe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.view.ViewUtils;

public class WorkerPanel extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7895070688066521718L;
	private AppController controller;
	private Worker loggedWorker;
	private JLabel lblLoggedAs;
	private JLabel loginDisplay;
	private JLabel lblLastTimeStart;
	private JLabel sWorkTimeLabbelDisplay;
	private JMenuBar menuBar;
	private JMenu user, view;
	private JMenuItem logOut, udate, monthReport, dayRaport, startWork;

	
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
		setSize(564, 300);
		setNormalContentPain();
		setMenu();
	}
	
		
	private void update(){
		sWorkTimeLabbelDisplay.setText(ViewUtils.minutesToHours((int)controller.getTodayWorkTime(loggedWorker)));
	}
	
	
	private void setLabelsAndButtons (JPanel contentPane ) {
		lblLoggedAs = new JLabel("Logged as: ");
		lblLoggedAs.setBounds(12, 35, 89, 33);
		contentPane.add(lblLoggedAs);
		
		 loginDisplay = new JLabel(loggedWorker.getLogin());
		loginDisplay.setBounds(126, 35, 269, 33);
		contentPane.add(loginDisplay);
		
	
		lblLastTimeStart = new JLabel("Worked today:");
		lblLastTimeStart.setBounds(12, 80, 177, 33);
		contentPane.add(lblLastTimeStart);
		
		
		
		sWorkTimeLabbelDisplay = new JLabel(ViewUtils.minutesToHours((int)controller.getTodayWorkTime(loggedWorker)));
		sWorkTimeLabbelDisplay.setBounds(126, 80, 97, 33);
		contentPane.add(sWorkTimeLabbelDisplay);
		
	}
	
	private void setMenu() {
		menuBar = new JMenuBar();
		view = new JMenu("View");
		user = new JMenu("User");
		assignAllJMenuItems();
		
		view.add(udate);
		view.add(monthReport);
		view.add(dayRaport);
		user.add(startWork);
		user.add(logOut);
		
		menuBar.add(view);
		menuBar.add(user);
		add(menuBar);
		setJMenuBar(menuBar);

		
	}
	
	private void assignAllJMenuItems() {
		assignUpdate();
		assignLogOut();
		assignViewMonthReport ();
		assignViewdayReport ();
		assignStartWork ();
		
	}
	
	private void assignStartWork () {
		startWork = new JMenuItem("start work");
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
	
	private void assignViewMonthReport () {
		monthReport = new JMenuItem("Monthly work time report");
		monthReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JScrollPane reportPanel = new MonthRaportContentPanel (controller, loggedWorker);
				reportPanel.setBounds(MainMenu.getBounds());
				reportPanel.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                        controller.getCurrentMonthTitlle(),
                        TitledBorder.CENTER,
                        TitledBorder.TOP));
				setBounds (MainMenu.getBounds());
				setSize(564, 170);
				setContentPane(reportPanel);
			}
		});
	}
	
	private void assignViewdayReport () {
		dayRaport = new JMenuItem("Dayly work time report");
		dayRaport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setNormalContentPain();
			}
		});
	}
	private void setNormalContentPain  () {
		JPanel dayRaport = new JPanel();
		dayRaport.setBorder(new EmptyBorder(5, 5, 5, 5));
		dayRaport.setLayout(null);
		setLabelsAndButtons (dayRaport);
		dayRaport.setBounds(MainMenu.getBounds());
		setBounds (MainMenu.getBounds());
		setSize(300, 200);
		setContentPane(dayRaport);
	}
}
