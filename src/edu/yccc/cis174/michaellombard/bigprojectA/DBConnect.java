package edu.yccc.cis174.michaellombard.bigprojectA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DBConnect {

	private static Connection conn = connect();
	
	public static void openConnection() {
		conn = connect();
	}

	public static void closeConnection() throws SQLException {
		conn.close();
	}
	

	//*****  Connect to the database and return the Connection object *****\\
	private static Connection connect() {
		// SQLite connection string
		String basepath = ExamFunction.findPath();
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
	
	public int checkLogin(String username, String password) {
		int isAuthorized = -1;
		String sql = "SELECT * FROM users WHERE username = '" +  username + "' AND password = '" + password + "'";
		try (		Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){

			// loop through the result set
			while (rs.next()) {
				isAuthorized = rs.getInt("id");
				}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return isAuthorized;
	}
	
	public ExamTaker getUserValues(int id) {
		ExamTaker result = new ExamTaker();
		String sql = "SELECT * FROM users WHERE active = 1 AND id = " +  id;
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			// loop through the result set
			while (rs.next()) {
				result.setId(rs.getInt("id"));
				result.setUserName(rs.getString("username"));
				result.setEmail(rs.getString("email"));
				result.setFirstName(rs.getString("firstname"));
				result.setLastName(rs.getString("lastname"));
				}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		return result;
	}
	
	public int addUser(String username, String password, String email, String lastname, String firstname) {
		String sql = "INSERT INTO users(username, password, email, lastname, firstname, active) VALUES(?,?,?,?,?,?)";
		int last_inserted_id = -1;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.setString(4, lastname);
			pstmt.setString(5, firstname);
			pstmt.setInt(6, 1);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();//determine id of inserted room
            if(rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return last_inserted_id;
	}

	public int addExamHistory(ExamTaker examtaker) {

		return addExamStore(examtaker.getId(), 1, examtaker.getAttemptTimeStamp(), examtaker.getAttemptDuration(), examtaker.getAttemptScore());
	}

	
	public void editQuestion(int id, String question, int categoryid, int difficulty, int active) throws ParseException {
		String sql = "UPDATE questions SET question = ? , category_id = ? , difficulty = ? , active = ? WHERE id = ?";
		try ( PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
		try (
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
		try (
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
		try (
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
		try (
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
	
	public HashMap< String,String> getSettingValues() {
		HashMap< String,String> value = new HashMap< String,String>();
		String sql = "SELECT * FROM examsettings";
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			// loop through the result set
		while (rs.next()) {
				value.put(rs.getString("name"), rs.getString("value"));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return value;
		
	}
	
	public List<ExamAnswer> getAnswerList(){
		List<ExamAnswer> sqlValues = new ArrayList<ExamAnswer>();
		String sql = "SELECT * FROM answers WHERE active = 1";
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			// loop through the result set
		while (rs.next()) {
				sqlValues.add(new ExamAnswer(rs.getInt("id"),rs.getInt("question_id"),rs.getString("name"),rs.getBoolean("iscorrect")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return sqlValues;
	}

	public Boolean storeExam(List<QuestionWithAnswer> Test, List<String> selectedAnswers, ExamTaker examtaker, int examid){
		Boolean sqlValues = true;
		int examstoreID = addExamStore(examtaker.id, examid, examtaker.attemptTimeStamp, examtaker.attemptDuration, examtaker.attemptScore);
		if (examstoreID > 0) {
			int qo = 0;
			for (QuestionWithAnswer qa:Test) {
				qo++;
				int examstorequestionID = addExamStoreQuestion(examstoreID, qa.getQuestion(), false, qo);
				if (examstorequestionID > 0) {
					int ao = 0;
					boolean answerchosen = false;
					for (ExamAnswer answer:qa.getAnswerList()) {
						ao++;
						if (selectedAnswers.get(qo-1).equalsIgnoreCase(answer.getAnswer())) {
							answerchosen = true;
						} else {
							answerchosen = false;
						}
						int examstoreanswerID = addExamStoreAnswer(examstorequestionID, answer.getAnswer(), answer.getIsCorrect(), answerchosen, ao);
						if (examstoreanswerID < 0) {
							sqlValues = false;
						}
					}
				} else {
					sqlValues = false;	
				}
			}
		} else {
			sqlValues = false;
		}
		return sqlValues;
	}

	private int addExamStoreAnswer(int examstorequestions_id, String answertext, boolean answercorrect, boolean answerchosen, int answerorder) {
		String sql = "INSERT INTO examstoreanswers(examstorequestions_id, answertext, answercorrect, answerchosen, answerorder) VALUES(?,?,?,?,?)";
		int last_inserted_id = -1;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, examstorequestions_id);
			pstmt.setString(2, answertext);
			pstmt.setBoolean(3, answercorrect);
			pstmt.setBoolean(4, answerchosen);
			pstmt.setInt(5, answerorder);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();//determine id of inserted exam
            if(rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return last_inserted_id;
	}

	
	private int addExamStoreQuestion(int examstore_id, String questiontext, boolean questioncorrect, int questionorder) {
		String sql = "INSERT INTO examstorequestions(examstore_id, questiontext, questioncorrect, questionorder) VALUES(?,?,?,?)";
		int last_inserted_id = -1;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, examstore_id);
			pstmt.setString(2, questiontext);
			pstmt.setBoolean(3, questioncorrect);
			pstmt.setInt(4, questionorder);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();//determine id of inserted exam
            if(rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return last_inserted_id;
	}
	
	private int addExamStore(int users_id, int examid, String startTime, int duration, int score) {
		String sql = "INSERT INTO examstore(users_id, preparedexams_id, starttime, duration, score, active) VALUES(?,?,?,?,?,?)";
		int last_inserted_id = -1;
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, users_id);
			pstmt.setInt(2, examid);
			pstmt.setString(3, startTime);
			pstmt.setInt(4, duration);
			pstmt.setInt(5, score);
			pstmt.setBoolean(6, true);
			pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();//determine id of inserted exam
            if(rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return last_inserted_id;
	}
	
	public List<AvailableExam> getExamsAvailable(int userid) {
		List<AvailableExam> value = new ArrayList<AvailableExam>();
		String sql = "SELECT preparedexams.*, preparedexams_users.remainingattempts  FROM preparedexams LEFT JOIN preparedexams_users ON preparedexams.id = preparedexams_users.preparedexams_id WHERE preparedexams_users.remainingattempts > 0 AND preparedexams_users.active = 1 AND preparedexams_users.users_id = " + userid;
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			// loop through the result set
		while (rs.next()) {
			
				value.add(new AvailableExam(rs.getInt("id"), rs.getString("examname"), rs.getInt("numberofquestions"), rs.getInt("maxanswers"), rs.getInt("allowedattempts"),rs.getInt("remainingattempts"), rs.getInt("timelimit")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return value;
		
	}

	public List<CompletedExam> getExamsTaken(int userid) {
		List<CompletedExam> value = new ArrayList<CompletedExam>();
		String sql = "SELECT examstore.*, preparedexams.examname AS examname, preparedexams.numberofquestions AS numquestions FROM examstore LEFT JOIN preparedexams ON examstore.preparedexams_id = preparedexams.id WHERE examstore.active = 1 AND examstore.users_id = " + userid;
		try (
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			// loop through the result set
		while (rs.next()) {
				value.add(new CompletedExam(rs.getInt("id"), rs.getInt("preparedexams_id"), rs.getString("examname"), rs.getString("starttime"), rs.getInt("duration"), rs.getInt("score"), rs.getInt("numquestions")));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return value;
		
	}

	
	
	
	
	

	//***************** main method included for diagnostics *****************\\
	public static void main(String[] args) {
		DBConnect app = new DBConnect();
		
		List<ExamQuestion> sqlQuestionList = app.getQuestionList();
		for(int i = 0; i < sqlQuestionList.size(); i++) {
			System.out.println(sqlQuestionList.get(i));
		}

		List<ExamAnswer> sqlAnswerList = app.getAnswerList();
		for(int i = 0; i < sqlAnswerList.size(); i++) {
			System.out.println(sqlAnswerList.get(i));
		}

	}

}