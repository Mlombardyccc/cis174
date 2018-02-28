package edu.yccc.cis174.michaellombard.bigprojectA;

public class AvailableExam {
	int id;
	String examname;  
	int numberofquestions;
	int maxanswers;
	int allowedattempts;
	int remainingattempts;
	
	public int getRemainingattempts() {
		return remainingattempts;
	}

	public void setRemainingattempts(int remainingattempts) {
		this.remainingattempts = remainingattempts;
	}

	int timelimit;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExamname() {
		return examname;
	}

	public void setExamname(String examname) {
		this.examname = examname;
	}

	public int getNumberofquestions() {
		return numberofquestions;
	}

	public void setNumberofquestions(int numberofquestions) {
		this.numberofquestions = numberofquestions;
	}

	public int getMaxanswers() {
		return maxanswers;
	}

	public void setMaxanswers(int maxanswers) {
		this.maxanswers = maxanswers;
	}

	public int getAllowedattempts() {
		return allowedattempts;
	}

	public void setAllowedattempts(int allowedattempts) {
		this.allowedattempts = allowedattempts;
	}

	public int getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(int timelimit) {
		this.timelimit = timelimit;
	}

	public AvailableExam(int id, String examname,int numberofquestions, int maxanswers, int allowedattempts, int remainingattempts, int timelimit) {
		this.id = id;
		this.examname = examname;
		this.numberofquestions = numberofquestions;
		this.maxanswers = maxanswers;
		this.allowedattempts = allowedattempts;
		this.remainingattempts = remainingattempts;
		this.timelimit = timelimit;
	}

	public AvailableExam() {
		
	}

	public String toString() {
		String thisString = "ID: " + id + " |Exam Name: " + examname + " |Number of Questions: " + numberofquestions + " |Max Answers: " + maxanswers + " |Allowed Attempts: " + allowedattempts + " |Remaining Attempts: " + remainingattempts + " |Time Limit: " + timelimit;
		return thisString;
	}
	
}
