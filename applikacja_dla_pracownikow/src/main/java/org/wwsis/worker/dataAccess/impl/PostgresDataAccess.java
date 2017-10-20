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

import org.wwsis.worker.data.Session;
import org.wwsis.worker.data.Worker;
import org.wwsis.worker.dataAccess.DataAccess;

public class PostgresDataAccess implements DataAccess {
	static final String JDBC_DRIVER = "org.postgresql.Driver";

	static final String INSERT_WORKER_STATEMENT = "INSERT INTO \"Worker\""
			+ "(login, name, lastname, password, islogged, isblocked, didlogedforthefirsttime,"
			+ "numoffailedlogingattempts)" + "VALUES (?,?,?,?,?,?,?,?)"
			+ "ON CONFLICT (login) DO UPDATE SET"
			+ "(name, lastname, password, islogged, isblocked, didlogedforthefirsttime,"
			+ " numoffailedlogingattempts) = (?,?,?,?,?,?,?); ";

	static final String INSERT_LOGIN_STATEMENT = "INSERT INTO \"Logins\"( logNum, timeOfLogin, timeOfLogout, userlogin) "
			+ " VALUES ( ?, ?, ?, ?)  ON CONFLICT (logNum, userLogin) DO UPDATE SET (timeOfLogin, timeOfLogout) = (?, ?) ;";

	static final String GET_WORKER_STATEMENT = "SELECT * FROM \"Worker\" WHERE login = ? ";

	static final String GET_LOGINS_STATEMENT = "SELECT * FROM  \"Logins\" WHERE userLogin = ?";

	static final String DELETE_LOGINS_STATEMENT = " DELETE FROM \"Logins\" WHERE logNum > ? AND userLogin = ? ;";

	static final String EXISTS_WORKER_STATEMENT = "SELECT login FROM \"Worker\" WHERE login = ?";

	static final String GET_ALL_WORKERS_LOGINS_STATEMENT = " SELECT login FROM \"Worker\";";

	static final String DEPOPULATE_DATABASE_STATEMENT = "DELETE  FROM \"Logins\"; DELETE FROM \"Worker\";";

	Connection conn = null;
	Statement stmt = null;

	public PostgresDataAccess(String dbUrl, String userName, String password) {

		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(dbUrl, userName, password);
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
			if (!rs.next()) {
				return null;
			} else {
				result.setLogin(rs.getString(1));
				result.setName(rs.getString(2));
				result.setLatName(rs.getString(3));
				result.setPassword(rs.getString(4));
				result.setIsLogged(rs.getBoolean(5));
				result.setIsBlocked(rs.getBoolean(6));
				result.setDidLogedForTheFirstTime(rs.getBoolean(7));
				result.setNumOfFailedLogingAttempts(rs.getInt(8));
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
			List<Session> listOfLogs = new LinkedList<Session>();

			while (rs2.next()) {
				Timestamp startTs = rs2.getTimestamp(2);
				Timestamp endTs = rs2.getTimestamp(3);
				LocalDateTime startDate = startTs.toLocalDateTime();
				LocalDateTime endDate = endTs != null ? endTs.toLocalDateTime() : null;
				Session currentSession = Session.forDates(startDate, endDate);
				listOfLogs.add(currentSession);
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
				ps.setString(2 + 7, p.getName());

				ps.setString(3, p.getLastName());
				ps.setString(3 + 7, p.getLastName());

				ps.setString(4, p.getPassword());
				ps.setString(4 + 7, p.getPassword());

				ps.setBoolean(5, p.getIsLogged());
				ps.setBoolean(5 + 7, p.getIsLogged());

				ps.setBoolean(6, p.getIsBlocked());
				ps.setBoolean(6 + 7, p.getIsBlocked());

				ps.setBoolean(7, p.getDidLogedForTheFirstTime());
				ps.setBoolean(7 + 7, p.getDidLogedForTheFirstTime());

				ps.setInt(8, p.getNumOfFailedLogingAttempts());
				ps.setInt(8 + 7, p.getNumOfFailedLogingAttempts());

				ps.execute();
			}
			if (p.getListOfLogs() != null) {

				int index = 1;
				for (Session session : p.getListOfLogs()) {
					
					Timestamp start = Timestamp.valueOf(session.getStartTime());
					Timestamp stop = session.getEndTime() != null ? Timestamp.valueOf(session.getEndTime()): null;
					PreparedStatement ps = conn.prepareStatement(INSERT_LOGIN_STATEMENT);
					ps.setInt(1, index);
					ps.setTimestamp(2, start);
					ps.setTimestamp(3, stop);
					ps.setString(4, p.getLogin());
					ps.setTimestamp(5, start);
					ps.setTimestamp(6, stop);
					ps.execute();
					index++;
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
		List<Worker> result = new LinkedList<>();
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
