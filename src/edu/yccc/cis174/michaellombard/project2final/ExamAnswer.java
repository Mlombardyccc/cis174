package edu.yccc.cis174.michaellombard.project2final;

import java.util.Comparator;

public class ExamAnswer {
	int id;
	String answer;
	int isCorrect;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	public int getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(int isCorrect) {
		this.isCorrect = isCorrect;
	}

	public ExamAnswer(int id, String answer, int isCorrect ) {
		this.id = id;
		this.answer = answer;
		this.isCorrect = isCorrect;
	}
	
	public ExamAnswer() {
		
	}
	

	/*Comparator for sorting the list by isCorrect*/
	public static Comparator<ExamAnswer> ExamIsCorrect = new Comparator<ExamAnswer>() {
		boolean ans1;
		boolean ans2;
		public int compare(ExamAnswer s1, ExamAnswer s2) {
			if (s1.getIsCorrect() == 1) {ans1 = true;} else {ans1 = false;}
			if (s2.getIsCorrect() == 1) {ans2 = true;} else {ans2 = false;}
			return Boolean.compare(ans2,ans1);
		}
	};

	
}
