package edu.yccc.cis174.michaellombard.project1;

public class ExamAnswer {
	int id;
	int questionId;
	String answer;
	boolean isCorrect;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	
	public ExamAnswer(int id, int questionId, String answer, Boolean isCorrect ) {
		this.id = id;
		this.questionId = questionId;
		this.answer = answer;
		this.isCorrect = isCorrect;
	}
	
	public ExamAnswer() {
		
	}

	
}
