package edu.yccc.cis174.michaellombard.bigproject1;

public class ExamTaker {
	int id;
	String userName,email,lastName,firstName, attemptTimeStamp;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public ExamTaker(int id, String userName, String email, String lastName, String firstName, String attemptTimeStamp, int attemptScore, int attemptDuration) {  
		this.id = id;  
		this.userName = userName;  
		this.email = email;  
		this.lastName = lastName;  
		this.firstName = firstName;  
		this.attemptTimeStamp = attemptTimeStamp;  
		this.attemptScore = attemptScore;  
		this.attemptDuration = attemptDuration;  
	}  

	public ExamTaker(int id, String userName, String email, String lastName, String firstName) {  
		this.id = id;  
		this.userName = userName;  
		this.email = email;  
		this.lastName = lastName;  
		this.firstName = firstName;  
	}  

	public ExamTaker() {
		
	}



}
