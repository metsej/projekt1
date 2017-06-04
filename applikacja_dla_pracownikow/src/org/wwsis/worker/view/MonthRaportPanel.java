package org.wwsis.worker.view;

import java.awt.BorderLayout;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class MonthRaportPanel extends JFrame {

	private JScrollPane contentPane;
	String column[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private JTable table;
	private String[][] data;
	DefaultTableModel tableModel;
	AppController controller;
	Worker loggedWorker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		DataAccess dao = new JadisDataAccess("localhost");
		AppController controller = new AppController(dao);
		Worker loggedWorker = controller.loadWorker("gklimek");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					controller.logIn(loggedWorker);
					MonthRaportPanel frame = new MonthRaportPanel(controller, loggedWorker);
					frame.setVisible(true);
					controller.logOut(loggedWorker);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MonthRaportPanel(AppController controller, Worker loggedWorker) {
		
		this.controller = controller;
		this.loggedWorker = loggedWorker;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTable();
		
	}
	
	private void setTable() {
		table = new JTable();
		setSize(600, 300);
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
		contentPane = new JScrollPane(table);
		add(contentPane);
		setContentPane(contentPane);
	}
	
	private void updateTable() {
		List <String> list = controller.getMonthRaport(loggedWorker);
		int numOfRows = 6;
		int numOfColumns = 7;
		data = new String[numOfRows][numOfColumns];
		
		int listIter = 0;
		for (int i = 0; i < numOfRows; i++  ) {
			
			if (listIter == list.size()) {
				break;
			}
			
			for (int j = 0; j < numOfColumns; j++) {
				if (listIter >= list.size() ) {
					break;
				} else {
					data[i][j] = list.get(listIter);
					listIter ++;
				}
				
			}
			
		}
		
		tableModel.setDataVector(data, column);
	}

}
