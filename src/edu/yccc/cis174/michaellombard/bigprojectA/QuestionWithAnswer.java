package edu.yccc.cis174.michaellombard.bigprojectA;

import java.util.ArrayList;
import java.util.List;

public class QuestionWithAnswer {
	String question;
	List<ExamAnswer> answerList = new ArrayList<ExamAnswer>();
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<ExamAnswer> getAnswerList() {
		return answerList;
	}

	public void setAnswer(List<ExamAnswer> answerList) {
		this.answerList = answerList;
	}

	public QuestionWithAnswer(String question, List<ExamAnswer> answerList) {
		this.question = question;
		this.answerList = answerList;
	}

	public QuestionWithAnswer() {
	
	}
}
