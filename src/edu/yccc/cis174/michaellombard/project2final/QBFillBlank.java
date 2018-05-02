package edu.yccc.cis174.michaellombard.project2final;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QBFillBlank extends QuestionBlock {

	public void createQASet(int categoryID, int minq, int maxq, int mina, int maxa, int randomizeq, int randomizea) {

		List<QuestionWithAnswer> qaset = new ArrayList<QuestionWithAnswer>();	
		List<ExamAnswer> sortedAnswers;
		HashMap< Integer,String> qList = db.getQuestions(categoryID);//Call db to get a list of questions

		for (Map.Entry< Integer,String> entry : qList.entrySet()) { //go through each question returned by db
			String answerList = "";
			int thiskey = entry.getKey();
			List<ExamAnswer> aList = db.getAnswers(thiskey);//call db to get all answers for each question
			if (aList.size() >= mina) {//determine if there are at least enough questions to reach the minimum number of answers required
				String correctAns = "0";//default setting for correct answer
				sortedAnswers = aList;
				for(int i = 0; i < sortedAnswers.size(); i++) {//create string representation of answers and get correct answer value
					answerList += (i+1) +")" + sortedAnswers.get(i).answer + "\n";
					if (sortedAnswers.get(i).isCorrect == 1) {//check if this answer is the correct answer
						if (correctAns.equals("")) {
							correctAns += "" + (i+1);
						} else {
							correctAns += "|*|" + (i+1);
						}
					}
				}
				qaset.add(new QuestionWithAnswer(entry.getValue(),answerList, correctAns));//add question with answer to set
				questionCount++;
			}
		}
		if (randomizeq == 1) {//if question order to be randomized
			Collections.shuffle(qaset);
		}

	
	};

	public void createQASet(int categoryID, int maxq, int maxa) { 
		createQASet(categoryID, 0, maxq, 1, maxa, 1, 1);
	};
	
	public boolean answerCorrect(String userinput) {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(currentQuestion);
		String correctAnswer = qa.getCorrectAnswer();

		boolean correct = false;
		String array1[]= correctAnswer.split("|*|");
		for (String temp: array1){
			if (userinput.equals(temp)) {
				correct = true;
			}
		}
		return correct;
	}

	public boolean answerCorrect(String userinput, int thisQuestion) {
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(thisQuestion);
		String correctAnswer = qa.getCorrectAnswer();

		boolean correct = false;
		String array1[]= correctAnswer.split("|*|");
		for (String temp: array1){
			if (userinput.equals(temp)) {
				correct = true;
			}
		}
		return correct;
	}
	
	public String getCorrectAnswer() {
		String correctAnswerList = "";
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(currentQuestion);
		String correctAnswer = qa.getCorrectAnswer();
		String array1[]= correctAnswer.split("|*|");
		for (String temp: array1){
			if (correctAnswerList.equals("")) {
				correctAnswerList = temp;
			} else {
				correctAnswerList += " OR " + temp;
			}
		}
		return correctAnswerList;
	}

	public String getCorrectAnswer(int thisQuestion) {
		String correctAnswerList = "";
		QuestionWithAnswer qa = new QuestionWithAnswer();
		qa = qaset.get(thisQuestion);
		String correctAnswer = qa.getCorrectAnswer();
		String array1[]= correctAnswer.split("|*|");
		for (String temp: array1){
			if (correctAnswerList.equals("")) {
				correctAnswerList = temp;
			} else {
				correctAnswerList += " OR " + temp;
			}
		}
		return correctAnswerList;
	}

	
	/*******  END MUST BE IMPLEMENTED **********/
	
	
}
