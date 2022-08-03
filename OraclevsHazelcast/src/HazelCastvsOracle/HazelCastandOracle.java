package HazelCastvsOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelCastandOracle {

	public static void main(String[] args) {
		HazelcastInstance hz = HazelcastClient.newHazelcastClient();

		HazelCastandOracle dbHelper = new HazelCastandOracle();
		 System.out.println("Put 20.000 data to HazelCast " +
		 calculatePutElapsedTime(hz, 20000) / 1000F + "s");
		 System.out.println("Get 20.000 data from HazelCast " +
		 calculateGetElapsedTime(hz, 20000)  / 1000F+ "s\n");

		 System.out.println("Put 100.000 data to HazelCast " +
		 calculatePutElapsedTime(hz, 100000) / 1000F + "s");
		 System.out.println("Get 100.000 data from HazelCast " +
		 calculateGetElapsedTime(hz, 100000)  / 1000F+ "s\n");

		dbHelper.createHazelcastTable();
		System.out.println("Insert 20000 data to Oracle " + dbHelper.insertData(20000) / 1000F + "s");
		System.out.println("Select 20000 data from Oracle " + dbHelper.selectData(20000) / 1000F + "s\n");
		dbHelper.truncateTable();
		System.out.println("Insert 100000 data to Oracle " + dbHelper.insertData(100000) / 1000F + "s");
		System.out.println("Select 100000 data from Oracle " + dbHelper.selectData(100000) / 1000F + "s\n");
		dbHelper.truncateTable();
		
		
	}
	
	private static long calculatePutElapsedTime(HazelcastInstance hz, int numbers) {

		IMap<Integer, Integer> map = hz.getMap("put " + numbers + " numbers");
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		Random rand = new Random();
		for (int i = 0; i < numbers; i++) {
			map.put(i, rand.nextInt());
		}
		endTime = System.currentTimeMillis();

		return endTime - startTime;
	}

	private static long calculateGetElapsedTime(HazelcastInstance hz, int numbers) {
		IMap<Integer, Integer> map = hz.getMap("get " + numbers + " numbers");
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		for (int i = 0; i < numbers; i++) {
			//map.put(i, i);
			map.get("get " + numbers + " numbers");
		}
		endTime = System.currentTimeMillis();

		return endTime - startTime;
	}

	private String dbURL = "jdbc:oracle:thin:@localhost:1521:TEST";
	private String username = "HAMZA";
	private String password = "123";
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet results;
	private String query;

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbURL, username, password);
	}

	public void showErrorMessages(SQLException exception) {
		System.out.println("Error: " + exception.getMessage());
		System.out.println("Error code: " + exception.getErrorCode());
	}

	public void createHazelcastTable() {
		conn = null;
		// DbHelper helper = new DbHelper();
		stmt = null;
		query = "";
		try {
			conn = getConnection();
			if (conn == null) {
				System.out.println("There is an connecting error");
				return;
			}
			stmt = conn.createStatement();

			query = "DECLARE\r\n" + "sql_stmt long;\r\n" + "BEGIN\r\n"
					+ " sql_stmt:='CREATE TABLE Hazelcast (Numbers INT)';\r\n" + " EXECUTE IMMEDIATE sql_stmt;\r\n"
					+ " EXCEPTION\r\n" + " WHEN OTHERS THEN\r\n" + " IF SQLCODE = -955 THEN\r\n" + " 	NULL;\r\n"
					+ " ELSE\r\n" + " 	RAISE;\r\n" + " END IF;\r\n" + "END;";
			stmt.execute(query);

			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showErrorMessages(e);
		}
	}

	public long insertData(int numbersOfData) {
		conn = null;
		// DbHelper helper = new DbHelper();
		pstmt = null;
		long startTime, endTime, elapsedTime = 0;
		query = "";

		try {
			conn = getConnection();
			query = "INSERT INTO Hazelcast VALUES(?)";
			pstmt = conn.prepareStatement(query);
			startTime = System.currentTimeMillis();
			for (int i = 0; i < numbersOfData; i++) {
				Random rand = new Random();
				pstmt.setInt(1, rand.nextInt());
				pstmt.executeUpdate();
			}
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;

			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showErrorMessages(e);
		}
		return elapsedTime;
	}

	public long selectData(int numbersOfData) {
		conn = null;
		// DbHelper helper = new DbHelper();
		stmt = null;
		results = null;
		long startTime, endTime, elapsedTime = 0;
		query = "";
		try {
			conn = getConnection();
			stmt = conn.createStatement();

			query = "SELECT Numbers FROM Hazelcast";
			results = stmt.executeQuery(query);

			ArrayList<Integer> arrayListNumber = new ArrayList<Integer>();
			startTime = System.currentTimeMillis();
			while (results.next()) {
				arrayListNumber.add(results.getInt(1));
			}
			endTime = System.currentTimeMillis();
			elapsedTime = endTime - startTime;
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showErrorMessages(e);
		}
		return elapsedTime;
	}

	public void dropTableFromDb() {
		conn = null;
		// DbHelper helper = new DbHelper();
		stmt = null;
		query = "";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			query = "DROP TABLE Hazelcast";
			stmt.execute(query);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			showErrorMessages(e);
		}
	}

	public void truncateTable() {
		conn = null;
		// DbHelper helper = new DbHelper();
		stmt = null;
		query = "";
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			query = "TRUNCATE TABLE Hazelcast";
			stmt.execute(query);

			stmt.close();
			conn.close();
		} catch (SQLException e) {
			showErrorMessages(e);
		}
	}

}
