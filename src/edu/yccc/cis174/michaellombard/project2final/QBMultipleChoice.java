package edu.yccc.cis174.michaellombard.project2final;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QBMultipleChoice extends QuestionBlock {

	public void createQASet(int categoryID, int minq, int maxq, int mina, int maxa, int randomizeq, int randomizea) {

		int maxamod = maxa;
		List<ExamAnswer> sortedAnswers;
		HashMap< Integer,String> qList = new HashMap<Integer, String>();
		qList.clear();
		qList = db.getQuestions(categoryID);//Call db to get a list of questions
		qaset.clear();

		for (Map.Entry< Integer,String> entry : qList.entrySet()) { //go through each question returned by db
			String answerList = "";
			int thiskey = entry.getKey();
			List<ExamAnswer> aList = db.getAnswers(thiskey);//call db to get all answers for each question
			if (aList.size() >= mina) {//determine if there are at least enough questions to reach the minimum number of answers required
				String correctAns = "0";//default setting for correct answer
				if (aList.size() < maxa) {
					maxamod = aList.size();
				} else {
					maxamod = maxa;
				}
				if (randomizea == 1) {//if answers are to be randomized
					Collections.sort(aList, ExamAnswer.ExamIsCorrect);//ensures correct answer is always in the list
					sortedAnswers = aList.subList(0, maxamod);//Makes sure correct number of possible answers selected
					Collections.shuffle(sortedAnswers);//shuffle again to make sure correct answer is not always first answer
				} else {//if answers are to be in db order
					sortedAnswers = aList.subList(0, maxamod);//Makes sure correct number of possible answers selected
				}
				for(int i = 0; i < sortedAnswers.size(); i++) {//create string representation of answers and get correct answer value
					answerList += (i+1) +") " + sortedAnswers.get(i).answer + "  ";
					if (sortedAnswers.get(i).isCorrect == 1) {//check if this answer is the correct answer
						correctAns = "" + (i+1);
					}
				}
				qaset.add(new QuestionWithAnswer(entry.getValue(),answerList, correctAns));//add question with answer to set
				questionCount++;
			}
			if (randomizeq == 1) {//if question order to be randomized
				Collections.shuffle(qaset);
			}
		}
		
	};

	public void createQASet(int categoryID, int maxq, int maxa) { 
		createQASet(categoryID, 0, maxq, 1, maxa, 1, 1);
	};
	
	
}
