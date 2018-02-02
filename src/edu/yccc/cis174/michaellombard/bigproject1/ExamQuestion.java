package edu.yccc.cis174.michaellombard.bigproject1;

public class ExamQuestion {
	int id;
	String question, category;  
	int difficulty;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	public ExamQuestion(int id, String question, String category, int difficulty) {
		this.id = id;
		this.question = question;
		this.category = category;
		this.difficulty = difficulty;
	}

	public ExamQuestion() {
		
	}

	public String toString() {
		String thisString = Integer.toString(id) + " " + question + " " + category + " " + difficulty;
		return thisString;
	}
	
}
