package org.wwsis.worker.view.jframe;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.wwsis.worker.controller.AppController;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.view.ViewUtils;

public class MonthRaportContentPanel extends JScrollPane {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4337253061639143620L;
	String column[] = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
	private JTable table;
	private String[][] data;
	DefaultTableModel tableModel;
	AppController controller;
	Worker loggedWorker;
	

	
	public MonthRaportContentPanel(AppController controller, Worker loggedWorker) {
		
		this.controller = controller;
		this.loggedWorker = loggedWorker;

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
		getViewport ().add (table);
	}
	


	
	private List<String> formattedMonthReport () {
		SortedMap<LocalDate, Float> calendar =  controller.getMonthRaport(loggedWorker);
		List<String> result = new ArrayList<String>();
		DayOfWeek numOfFirstDayOfMonth = calendar.firstKey().getDayOfWeek();
		for (int i = 0; i < numOfFirstDayOfMonth.ordinal(); i++) {
			result.add(0, " ");
		}
		int dayNum = 1;
		for (float d : calendar.values()) {
			result.add(dayNum + " " + ViewUtils.minutesToHours((int)d));
			dayNum ++;
		}
		return result;
	}
	
	private void updateTable() {
		List <String> list = formattedMonthReport();
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
