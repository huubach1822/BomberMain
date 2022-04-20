package game;
import java.awt.BorderLayout;
import java.sql.*;

import javax.swing.JPanel;

public class DataBase {
	static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/boomgame";
	static final String USER = "root";
	static final String PASS = "";
	Connection conn = null;
	Statement stmt = null;

	private void connect() {
		try {
			Class.forName(DRIVER_CLASS);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object[][] readTable(String s) {		
		Object[][] data = null;
		connect();
	
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(s);
			data = new Object[getRowCount(rs)][getColumnCount(rs)];
			rs.beforeFirst();

			int i = 0;
			while (rs.next()) {
				int j = 0;
				data[i][j++] = rs.getInt("Id");
				data[i][j++] = rs.getString("Name");
				data[i][j++] = rs.getInt("Score");
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		close();
		return data;
	}
	
	public void updateData(String name, int score) {
		connect();
		
		String s = String.valueOf(score);  
		try {
		stmt = conn.createStatement();
		stmt.executeUpdate("Insert into player(ID,Name,Score) values(NULL,'" + name + "','" + s + "')");
		} catch (Exception e){
			e.printStackTrace();
		}
		
		close();
	}
	
	private int getRowCount(ResultSet rs) {
		try {
			if(rs != null) {				
				rs.last();		
				return rs.getRow(); 
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private int getColumnCount(ResultSet rs) {
		try {
			if(rs != null)
				return rs.getMetaData().getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
