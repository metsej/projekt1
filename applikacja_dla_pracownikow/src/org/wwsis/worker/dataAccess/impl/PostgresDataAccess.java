package org.wwsis.worker.dataAccess.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

public class PostgresDataAccess implements DataAccess {

	Connection conn = null;
	Statement stmt = null;

	String INSERT_WORKER_STATEMENT = "INSERT INTO \"Worker\""
			+ "(login, name, lastname, password, islogged, isblocked, didlogedforthefirsttime,"
			+ "  timeofstart, timeofend, numoffailedlogingattempts)"
			+ "VALUES (?,?,?,?,?,?,?,?,?,?)" + "ON CONFLICT (login) DO UPDATE SET"
			+ "(name, lastname, password, islogged, isblocked, didlogedforthefirsttime,"
			+ "  timeofstart, timeofend, numoffailedlogingattempts)" + " = "
			+ "(?,?,?,?,?,?,?,?,?) ";

	String INSERT_LOGIN_STATEMENT = "INSERT INTO \"Logins\"( logNum,  userlogin, timeOfLog) " + " VALUES ( ?, ?, ?) "
	+ " ON CONFLICT (logNum, userLogin) DO UPDATE SET timeOfLog = ?;";
	
	String GET_WORKER_STATEMENT = "SELECT * FROM \"Worker\" WHERE login = ? ";
	
	String GET_LOGINS_STATEMENT = "SELECT * FROM  \"Logins\" WHERE userLogin = ?";
	
	String DELETE_LOGINS_STATEMENT = " DELETE FROM \"Logins\" WHERE logNum > ? AND userLogin = ? ;";
	
	String EXISTS_WORKER_STATEMENT = "SELECT login FROM \"Worker\" WHERE login = ?";
	
	String GET_ALL_WORKERS_LOGINS_STATEMENT = " SELECT login FROM \"Worker\";";
	
	String DEPOPULATE_DATABASE_STATEMENT = "DELETE  FROM \"Logins\"; DELETE FROM \"Worker\";";

	public PostgresDataAccess() {

		String JDBC_DRIVER = "org.postgresql.Driver";
		String DB_URL = "jdbc:postgresql://localhost/Pracownicy";

		// Database credentials
		String USER = "postgres";
		String PASS = "urukhai22";

		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS); 
			conn.setAutoCommit(false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean doWorkerExists(Worker p) {
		
		try {
			PreparedStatement ps = conn.prepareStatement(EXISTS_WORKER_STATEMENT);
			ps.setString(1, p.getLogin());
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	@Override
	public Worker loadWorkerWithoutLogs(Worker p) {
		Worker result = new Worker();
		try {
			PreparedStatement ps = conn.prepareStatement(GET_WORKER_STATEMENT);
			
			ps.setString(1, p.getLogin());
			ResultSet rs = ps.executeQuery();
			if (!rs.next() ) {
				return null;
			} else {
				result.setLogin( rs.getString(1));
				result.setName( rs.getString(2));
				result.setLatName( rs.getString(3));
				result.setPassword(rs.getString(4));
				result.setIsLogged(rs.getBoolean(5));
				result.setIsBlocked(rs.getBoolean(6));
				result.setDidLogedForTheFirstTime(rs.getBoolean(7));
				result.setStartTime(rs.getTimestamp(8).toLocalDateTime());
				result.setEndTime(rs.getTimestamp(9).toLocalDateTime());
				result.setNumOfFailedLogingAttempts(rs.getInt(10));
				
			} 
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Worker loadWorker(Worker p) {
		
		Worker result = loadWorkerWithoutLogs(p);
		
		try {
			
		PreparedStatement ls = conn.prepareStatement(GET_LOGINS_STATEMENT);
		ls.setString(1, p.getLogin());
		
		ResultSet rs2 = ls.executeQuery();
		List<LocalDateTime> listOfLogs = new LinkedList<LocalDateTime>();
		
		while (rs2.next() ) {
			listOfLogs.add(rs2.getTimestamp(2).toLocalDateTime());
		}
		
		result.setListOfLogs(listOfLogs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void saveWorker(Worker p) {

		try {
			{
				PreparedStatement ps = conn.prepareStatement(INSERT_WORKER_STATEMENT);
				ps.setString(1, p.getLogin());
				
				ps.setString(2, p.getName());
				ps.setString(11, p.getName());
				
				ps.setString(3, p.getLastName());
				ps.setString(3+9, p.getLastName());
				
				ps.setString(4, p.getPassword());
				ps.setString(4+9, p.getPassword());
				
				ps.setBoolean(5, p.getIsLogged());
				ps.setBoolean(5+9, p.getIsLogged());
				
				ps.setBoolean(6, p.getIsBlocked());
				ps.setBoolean(6+9, p.getIsBlocked());
				
				ps.setBoolean(7, p.getDidLogedForTheFirstTime());
				ps.setBoolean(7+9, p.getDidLogedForTheFirstTime());
				
				ps.setTimestamp(8, Timestamp.valueOf(p.getStartTime()));
				ps.setTimestamp(8+9, Timestamp.valueOf(p.getStartTime()));
				
				ps.setTimestamp(9, Timestamp.valueOf(p.getEndTime()));
				ps.setTimestamp(9+9, Timestamp.valueOf(p.getEndTime()));
				
				ps.setInt(10, 0);
				ps.setInt(10+9, 0);
				
				ps.execute();
			}
			if (p.getListOfLogs() != null) {
					
				
				int index = 1;
				for (LocalDateTime log : p.getListOfLogs()) {

					PreparedStatement ps = conn.prepareStatement(INSERT_LOGIN_STATEMENT);
					ps.setInt(1, index);
					ps.setString(2, p.getLogin());
					ps.setTimestamp(3, Timestamp.valueOf(log));
					ps.setTimestamp(4, Timestamp.valueOf(log));
					ps.execute();
					index ++;
				}
				PreparedStatement ds = conn.prepareStatement(DELETE_LOGINS_STATEMENT);
				
				ds.setInt(1, index - 1);
				ds.setString(2, p.getLogin());
				ds.execute();
			}
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Worker> getAllWorkersWithoutLogs() {
		List <Worker> result = new LinkedList<>();
		try {
			PreparedStatement ds = conn.prepareStatement(GET_ALL_WORKERS_LOGINS_STATEMENT);
			ResultSet rs = ds.executeQuery();
			
			while (rs.next()) {
				Worker temp = new Worker();
				temp.setLogin(rs.getString(1));
				result.add(loadWorkerWithoutLogs(temp));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void setExpireTimeForWorker(Worker w, int seconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void erase() {
		try {
			PreparedStatement ds = conn.prepareStatement(DEPOPULATE_DATABASE_STATEMENT);
			ds.execute();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
