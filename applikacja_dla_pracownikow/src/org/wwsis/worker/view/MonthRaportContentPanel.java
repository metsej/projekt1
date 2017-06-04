package org.wwsis.worker.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;

public class MonthRaportContentPanel extends JScrollPane {
	
	String column[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private JTable table;
	private String[][] data;
	DefaultTableModel tableModel;
	AppController controller;
	Worker loggedWorker;
	
	public MonthRaportContentPanel() {

	}

}
