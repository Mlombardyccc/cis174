package edu.yccc.cis174.michaellombard.bigprojectA;

public class CompletedExam {
	private int id;
	private int examID;
	private String examName;
	private String startTime;
	private int duration;
	private int score;
	private int numQuestions;
	
	public String toString() {
		String thisString = "ID: " + id + " |Exam ID: " + examID + " |Exam Name: " + examName + " |Exam Start Time: " + startTime + " |Duration: " + duration + " |Score: " + score + " |Number of Questions: " + numQuestions;
		return thisString;
	}
	
	public CompletedExam(int id, int examID, String examName, String startTime, int duration, int score, int numQuestions) {
		this.id = id;
		this.examID = examID;
		this.examName = examName;
		this.startTime = startTime;
		this.duration = duration;
		this.score = score;
		this.numQuestions = numQuestions;
	}

	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}

	public CompletedExam() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getExamID() {
		return examID;
	}
	
	public void setExamID(int examID) {
		this.examID = examID;
	}
	
	public String getExamName() {
		return examName;
	}
	
	public void setExamName(String examName) {
		this.examName = examName;
	}
	
	public String getStarttime() {
		return startTime;
	}
	
	public void setStarttime(String startTime) {
		this.startTime = startTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}

}
