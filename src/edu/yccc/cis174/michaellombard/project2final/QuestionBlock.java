package edu.yccc.cis174.michaellombard.project2final;

import java.util.ArrayList;
import java.util.List;




abstract public class QuestionBlock {
	
	protected int currentQuestion = 0;
	protected boolean reorder;
	protected List<String> wrongset = new ArrayList<String>();
	protected DBConnect db = new DBConnect();	
	protected List<QuestionWithAnswer> qaset = new ArrayList<QuestionWithAnswer>();
	protected int questionCount = 0;

	
	public void createQASet(int categoryID, int minq, int maxq, int mina, int maxa, int randomizeq, int randomizea) {
	
		
	}
	
	public void createQASet(int categoryID, int maxq, int maxa) {
		
		
	}
	
	public boolean inputCorrect(String userinput) {
		if (userinput.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean answerCorrect(String userinput) {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(currentQuestion);
		String correctAnswer = qa.getCorrectAnswer();
		if (userinput.equalsIgnoreCase(correctAnswer)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean answerCorrect(String userinput, int thisQuestion) {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(thisQuestion);
		String correctAnswer = qa.getCorrectAnswer();
		if (userinput.equalsIgnoreCase(correctAnswer)) {
			return true;
		} else {
			return false;
		}
	}

	public String getCorrectAnswer() {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(currentQuestion);
		String correctAnswer = qa.getCorrectAnswer();
		return correctAnswer;
	}

	public String getCorrectAnswer(int thisQuestion) {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(thisQuestion);
		String correctAnswer = qa.getCorrectAnswer();
		return correctAnswer;
	}
	
	public QuestionWithAnswer getNextQA() {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		currentQuestion++;
		qa = qaset.get(currentQuestion);
	 return qa;
	}
	
	public QuestionWithAnswer getQA() {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(currentQuestion);
	 return qa;
	}
	
	public QuestionWithAnswer getPrevQA() {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		currentQuestion--;
		qa = qaset.get(currentQuestion);
	 return qa;
	}
	
	public QuestionWithAnswer getQA(int thisQuestion) {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		currentQuestion = thisQuestion;
		qa = qaset.get(currentQuestion);
	 return qa;
	}

	public int getQuestionCount() {
		return questionCount;
	}
	
	public int getCurrentQuestion() {
		return currentQuestion;
	}
	
	public void setCurrentQuestion(int cqvalue) {
		currentQuestion = cqvalue;
	}
}
