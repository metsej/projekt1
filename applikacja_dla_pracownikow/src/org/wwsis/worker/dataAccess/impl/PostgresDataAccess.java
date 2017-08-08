package org.wwsis.worker.dataAccess.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
	
	String DELETE_LOGINS_STATEMENT = " DELETE FROM \"Logins\" WHERE logNum > ? ;";
	
	String EXISTS_WORKER_STATEMENT = "SELECT login FROM \"Worker\" WHERE login = ?";

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
	public Worker loadWorker(Worker p) {
		// TODO Auto-generated method stub
		return null;
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
			{
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
				ds.execute();
				conn.commit();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<Worker> getAllWorkers() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

}
