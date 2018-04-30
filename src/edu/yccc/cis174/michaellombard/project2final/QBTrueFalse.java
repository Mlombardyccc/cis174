package edu.yccc.cis174.michaellombard.project2final;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class QBTrueFalse implements QuestionBlock {

	boolean reorder;
	List<String> wrongset = new ArrayList<String>();
	DBConnect db = new DBConnect();	
	/*******  START MUST BE IMPLEMENTED **********/
	
	public List<QuestionWithAnswer> createQASet(int categoryID, int minq, int maxq, int mina, int maxa, int randomizeq, int randomizea) {

		List<QuestionWithAnswer> qaset = new ArrayList<QuestionWithAnswer>();	
		List<ExamAnswer> sortedAnswers;
		HashMap< Integer,String> qList = db.getQuestions(categoryID);//Call db to get a list of questions

		for (Map.Entry< Integer,String> entry : qList.entrySet()) { //go through each question returned by db
			String answerList = "";
			int thiskey = entry.getKey();
			List<ExamAnswer> aList = db.getAnswers(thiskey);//call db to get all answers for each question
			if (aList.size() >= mina) {//determine if there are at least enough questions to reach the minimum number of answers required
				String correctAns = "0";//default setting for correct answer
				if (randomizea == 1) {//if answers are to be randomized
					Collections.sort(aList, ExamAnswer.ExamIsCorrect);//ensures correct answer is always in the list
					if ( aList.size() < maxa) {
						maxa = aList.size();
					}
					sortedAnswers = aList.subList(0, maxa);//Makes sure correct number of possible answers selected
					Collections.shuffle(sortedAnswers);//shuffle again to make sure correct answer is not always first answer
				} else {//if answers are to be in db order
					sortedAnswers = aList.subList(0, maxa);//Makes sure correct number of possible answers selected
				}
				answerList = "True or False" + "\n";
				for(int i = 0; i < sortedAnswers.size(); i++) {//create string representation of answers and get correct answer value
					if (sortedAnswers.get(i).isCorrect == 1) {//check if this answer is the correct answer
						correctAns = sortedAnswers.get(i).getAnswer();
					}
				}
				qaset.add(new QuestionWithAnswer(entry.getValue(),answerList, correctAns));//add question with answer to set
			}
			if (randomizeq == 1) {//if question order to be randomized
				Collections.shuffle(qaset);
			}
		}
		return qaset;
	};

	public List<QuestionWithAnswer> createQASet(int categoryID, int maxq, int maxa) { 
		return createQASet(categoryID, 0, maxq, 1, maxa, 1, 1);
	};
	
	public boolean inputCorrect(String userinput) {
		if (userinput.equalsIgnoreCase("t") || userinput.equalsIgnoreCase("true") || userinput.equalsIgnoreCase("f") || userinput.equalsIgnoreCase("false")) {
			return true;
		} else {
			return false;
		}
	};
	
	public boolean answerCorrect(String userinput, String correctAnswer) {
		if (userinput.equalsIgnoreCase("t") || userinput.equalsIgnoreCase("true")) {
			userinput = "t";
		} else if (userinput.equalsIgnoreCase("f") || userinput.equalsIgnoreCase("false"))
		{
			userinput = "f";
		}
		if (userinput.equals(correctAnswer)) {
			return true;
		} else {
			return false;
		}
	};
	
	/*******  END MUST BE IMPLEMENTED **********/
	
	
}
