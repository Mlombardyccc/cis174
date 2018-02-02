package edu.yccc.cis174.michaellombard.bigproject1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author sqlitetutorial.net
 */
public class SQLiteJDBCDriverConnection {

	/**
	 * Connect to the database
	 * @return the Connection object
	 */
	private Connection connect() {
		// SQLite connection string
		String basepath = JavaExam.findPath();
		String url = "jdbc:sqlite:" + basepath + "exam.db";
		System.out.println("Accessing Database...");
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public void editQuestion(int id, String name, String description) throws ParseException {
		String sql = "UPDATE questions SET name = ? , descrip = ? WHERE id = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setInt(3, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}

	public void addQuestion(String name, String description) throws ParseException {
		String sql = "INSERT INTO questions(name, descrip, active) VALUES(?,?,?)";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setString(2, description);
			pstmt.setBoolean(3, true);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}

	public List<ExamQuestion> getQuestionList(){
		List<ExamQuestion> sqlValues = new ArrayList<ExamQuestion>();
		String sql = "SELECT questions.*,category.name AS catname FROM questions INNER JOIN category ON category.id = questions.category_id WHERE questions.active = 1";
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			// loop through the result set
		while (rs.next()) {
				sqlValues.add(new ExamQuestion(rs.getInt("id"),rs.getString("question"),rs.getString("catname"),rs.getInt("difficulty")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return sqlValues;
	}

	public String getQuestion(int id){
		String sqlValues = new String();
		String sql = "SELECT name FROM amenities WHERE id = " +  String.valueOf(id);
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

			// loop through the result set
		while (rs.next()) {
				sqlValues = rs.getString("name");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return sqlValues;
	}


	public static void main(String[] args) {
		SQLiteJDBCDriverConnection app = new SQLiteJDBCDriverConnection();
		List<ExamQuestion> sqlQuestionList = app.getQuestionList();
		for(int i = 0; i < sqlQuestionList.size(); i++) {
			System.out.println(sqlQuestionList.get(i));
		}

	}

}