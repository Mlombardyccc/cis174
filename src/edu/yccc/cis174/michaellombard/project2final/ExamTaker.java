package edu.yccc.cis174.michaellombard.project2final;

public class ExamTaker {
	int id;
	String lastName,firstName, attemptTimeStamp, uName, email;
	int attemptScore;
	int attemptDuration;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

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

	public ExamTaker(int id, String lastName, String firstName, String attemptTimeStamp, int attemptScore, int attemptDuration, String uName, String email) {  
		this.id = id;  
		this.lastName = lastName;  
		this.uName = uName;  
		this.email = email;  
		this.firstName = firstName;  
		this.attemptTimeStamp = attemptTimeStamp;  
		this.attemptScore = attemptScore;  
		this.attemptDuration = attemptDuration;  
	}  
	
	public ExamTaker() {
		
	}

	@Override
    public String toString() {
        return String.format("id=" + getId() + "|| uname=" + getuName() + "|| fname=" + getFirstName() + "|| lname=" + getLastName() + "|| email=" + getEmail() + "|| ats=" + getAttemptTimeStamp() + "|| as=" + getAttemptScore() + "|| ad=" + getAttemptDuration() );
    }

}
