package edu.yccc.cis174.michaellombard.bigproject1;

public class QuestionWithAnswer {
	String question, answer, correctAnswer;
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public QuestionWithAnswer(String question, String answer, String correctAnswer) {
		this.question = question;
		this.answer = answer;
		this.correctAnswer = correctAnswer;
	}

	public QuestionWithAnswer() {
	
	}
}
