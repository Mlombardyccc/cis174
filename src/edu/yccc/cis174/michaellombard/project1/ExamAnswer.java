package edu.yccc.cis174.michaellombard.project1;

import java.util.Comparator;

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
	
	
	public boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(boolean isCorrect) {
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
	
	/*Comparator for sorting the list by questionId*/
	public static Comparator<ExamAnswer> ExamQuestionId = new Comparator<ExamAnswer>() {
		public int compare(ExamAnswer s1, ExamAnswer s2) {

			int qidno1 = s1.getQuestionId();
			int qidno2 = s2.getQuestionId();

			return qidno1-qidno2;
		}
	};

	/*Comparator for sorting the list by isCorrect*/
	public static Comparator<ExamAnswer> ExamIsCorrect = new Comparator<ExamAnswer>() {
		public int compare(ExamAnswer s1, ExamAnswer s2) {

			return Boolean.compare(s2.getIsCorrect(),s1.getIsCorrect());
		}
	};

	
}
