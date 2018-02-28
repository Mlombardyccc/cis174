package edu.yccc.cis174.michaellombard.bigprojectA;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;


public class ExamFunction 
{


	public static String findPath() {
	    //MAKE PATH AS DYNAMIC AS POSSIBLE START\\
	    String pathToClass = DBConnect.class.getName();
	    pathToClass = pathToClass.replace(".", ",");
	    String[] pathItems = pathToClass.split(",");
	    String pathToClassDir = "";
	    for (int i = 0; i < (pathItems.length - 1); i++) {//split string to remove class name and leave path data
	    	pathToClassDir = pathToClassDir + pathItems[i] + "\\\\";
	    }
	    String pathToFile = System.getProperty("user.dir").replace("\\","\\\\") + "\\\\src\\\\" + pathToClassDir;
		//MAKE PATH AS DYNAMIC AS POSSIBLE END\\
	    return pathToFile;
		
	}

	
	public static HashMap< String,String> initExam() { 
		DBConnect db = new DBConnect();
		HashMap< String,String> settingvalues = db.getSettingValues();
		return settingvalues;
	}

	public static void writeResult(ExamTaker takerInfo) {
		DBConnect db = new DBConnect();
		db.addExamHistory(takerInfo);
	}
	
	public static List<QuestionWithAnswer> generateTest(int numberOfQuestions, int numberOfAnswers) {  
		DBConnect db = new DBConnect();
	    List<ExamQuestion> questionlist = new ArrayList<ExamQuestion>();
		List<ExamAnswer> answerlist = new ArrayList<ExamAnswer>();
		List<QuestionWithAnswer> qalist = new ArrayList<QuestionWithAnswer>();

	    //Import Questions
	    questionlist = db.getQuestionList();
		if (numberOfQuestions > questionlist.size()) {
			numberOfQuestions = questionlist.size();
		}

		//Import Answers
	    answerlist = db.getAnswerList();
	    List<ExamQuestion> copy = new LinkedList<ExamQuestion>(questionlist);
	    Collections.shuffle(copy);
	    List<ExamQuestion> randQuestions = copy.subList(0, numberOfQuestions);//randomize questions and set to number selected by numberOfQuestions
		
	    //Combine Questions and Answers		
	    for(ExamQuestion r:randQuestions){  
			List<ExamAnswer> sortedAnswerList = sortedAnswers(numberOfAnswers, r.id, answerlist);
		    qalist.add(new QuestionWithAnswer(r.question,sortedAnswerList));
		}

	    return qalist;
	}  


    public static List<ExamAnswer> sortedAnswers(int listLength, int questionId, List<ExamAnswer> listToSort) {//Sorts and shuffles exam answers
	    List<ExamAnswer> copy = new LinkedList<ExamAnswer>(listToSort);//Make copy of original list to work with
	    List<ExamAnswer> copyb = new ArrayList<ExamAnswer>();
		int numAnswers = 0;
	    Collections.shuffle(copy);//randomizes list
	    for(ExamAnswer a:copy){ //pulls answers for current question into new list
			if (questionId == a.questionId) {
			copyb.add(new ExamAnswer(a.id,a.questionId,a.answer,a.isCorrect));		
			numAnswers++;
			}
			}
	    if (listLength > numAnswers) {listLength = numAnswers;}//ensures no calculation errors if not enough answers present for question
	    Collections.sort(copyb, ExamAnswer.ExamIsCorrect);//ensures correct answer is always in the list
	    List<ExamAnswer> sortedAnswers = copyb.subList(0, listLength);//Makes sure correct number of possible answers selected
	    Collections.shuffle(sortedAnswers);//shuffle again to make sure correct answer is not always first answer
    	return sortedAnswers;
    }

   
 }
