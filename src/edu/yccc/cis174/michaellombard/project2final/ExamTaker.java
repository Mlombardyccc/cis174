package edu.yccc.cis174.michaellombard.project2final;

public class ExamTaker {
	int id;
	String lastName,firstName, attemptTimeStamp;
	int attemptScore;
	int attemptDuration;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAttemptTimeStamp() {
		return attemptTimeStamp;
	}

	public void setAttemptTimeStamp(String attemptTimeStamp) {
		this.attemptTimeStamp = attemptTimeStamp;
	}

	public int getAttemptScore() {
		return attemptScore;
	}

	public void setAttemptScore(int attemptScore) {
		this.attemptScore = attemptScore;
	}

	public int getAttemptDuration() {
		return attemptDuration;
	}

	public void setAttemptDuration(int attemptDuration) {
		this.attemptDuration = attemptDuration;
	}

	public ExamTaker(int id, String lastName, String firstName, String attemptTimeStamp, int attemptScore, int attemptDuration) {  
		this.id = id;  
		this.lastName = lastName;  
		this.firstName = firstName;  
		this.attemptTimeStamp = attemptTimeStamp;  
		this.attemptScore = attemptScore;  
		this.attemptDuration = attemptDuration;  
	}  
	
	public ExamTaker() {
		
	}



}
