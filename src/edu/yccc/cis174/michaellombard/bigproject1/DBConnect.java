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


public class DBConnect {

	//*****  Connect to the database and return the Connection object *****\\
	private Connection connect() {
		// SQLite connection string
		String basepath = JavaExam.findPath();
		String url = "jdbc:sqlite:" + basepath + "exam.db";
		System.out.println("Accessing Database...");//Testing code to verify database access, Remove for production
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}
	
	public void editQuestion(int id, String question, int categoryid, int difficulty, int active) throws ParseException {
		String sql = "UPDATE questions SET question = ? , category_id = ? , difficulty = ? , active = ? WHERE id = ?";
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, question);
			pstmt.setInt(2, categoryid);
			pstmt.setInt(3, difficulty);
			if (active == 1) {
				pstmt.setBoolean(4, true);
			} else {
				pstmt.setBoolean(4, false);
			}
			pstmt.setInt(5, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
	}

	public void addQuestion(ExamQuestion question) throws ParseException {
		String sql = "INSERT INTO questions(question, category_id, difficulty, active) VALUES(?,?,?,?)";
		int categoryid = getCategory(question.getCategory());
		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, question.getQuestion());
			pstmt.setInt(2, categoryid);
			pstmt.setInt(3, question.getDifficulty());
			pstmt.setBoolean(4, true);
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

	public ExamQuestion getQuestion(int id){
		ExamQuestion sqlValues = new ExamQuestion();
		String sql = "SELECT * FROM questions WHERE id = " +  String.valueOf(id);
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

			// loop through the result set
			rs.next();
			sqlValues = new ExamQuestion(rs.getInt("id"),rs.getString("question"),rs.getString("catname"),rs.getInt("difficulty"));
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return sqlValues;
	}

	public int getCategory(String name){
		int sqlValues = -1;
		String sql = "SELECT id FROM category WHERE name = " +  name;
		try (Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

			// loop through the result set
			while (rs.next()) {
				sqlValues = rs.getInt("id");
			}			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return sqlValues;
	}

	public String getCategory(int id){
		String sqlValues = null;
		String sql = "SELECT name FROM category WHERE id = " +  Integer.toString(id);
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

	//***************** main method included for diagnostics *****************\\
	public static void main(String[] args) {
		DBConnect app = new DBConnect();
		
		List<ExamQuestion> sqlQuestionList = app.getQuestionList();
		for(int i = 0; i < sqlQuestionList.size(); i++) {
			System.out.println(sqlQuestionList.get(i));
		}

	}

}