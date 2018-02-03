package edu.yccc.cis174.michaellombard.bigproject1;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import static java.lang.Math.toIntExact;


public class JavaExam 
{
	private static Scanner scanner = new Scanner(System.in);//initialize input detection


	public static void main(String[] args) throws NumberFormatException, IOException 
	{  
		//TESTING VARIABLE INITIALIZATION START\\
		HashMap< String,Integer> initvalues = initExam();
		int numberOfQuestions = initvalues.get("numberOfQuestions");//maximum number of questions on the test
		int numberOfAnswers = initvalues.get("numberOfAnswers");//maximum number of answers per question
		int passFailCutoff = initvalues.get("passFailCutoff");//Happy result/unhappy result changeover point
		int answerType = initvalues.get("answerType");//Type of question numbers or letters
		//TESTING VARIABLE INITIALIZATION END\\
		
		//GENERATE EXAM START\\
		List<QuestionWithAnswer> qalist = generateTest(numberOfQuestions,numberOfAnswers);	//Generate this exam
		//GENERATE EXAM END\\

		//GET EXAM TAKER DATA START\\
		ExamTaker userValues = doLogin();
		//GET EXAM TAKER DATA END\\
		
		//WAIT FOR READINESS CONFIRMATION START\\
		System.out.print("Press enter when ready to begin test...");
		getInput();
 		//WAIT FOR READINESS CONFIRMATION END\\
		
		//TIMER AND TIMESTAMP INITIALIZATION START\\
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//init timestamp with start of exam not start of registration
		int rightAnswers = 0;//initialize counter for answers chosen correctly
		long startTime = System.currentTimeMillis();//Start timer
		//TIMER AND TIMESTAMP INITIALIZATION END\\
		
		//EXAM QUESTIONS START\\
		int currentQuestion = 0;
		for(QuestionWithAnswer qa:qalist)
		{
			currentQuestion++;
			System.out.println(currentQuestion + ") " + qa.question + "\n\n");//display question with possible answers
		    int currentAnswer = 1;
		    int correctAnswer = 0;
			for(ExamAnswer a:qa.answerList){  
		    	System.out.println(FormatKeys.setAnswerFormat(answerType, currentAnswer) + ") " + a.answer);
		    	if (a.isCorrect) {correctAnswer = currentAnswer;}
		    	currentAnswer++;
		    }
			String answer = "";
			while (answer.length() < FormatKeys.setAnswerFormat(answerType,"min") || answer.length() > FormatKeys.setAnswerFormat(answerType,"max")) 
			{//if answer is blank or more than 1 character, repeat request for answer
				answer = getInput();//await answer
				if (answer.length() < FormatKeys.setAnswerFormat(answerType,"min") || answer.length() > FormatKeys.setAnswerFormat(answerType,"max")) 
				{
					System.out.println("Invalid answer. Please retry.\n");
				}
			}
			if (answer.equalsIgnoreCase(FormatKeys.setAnswerFormat(answerType, correctAnswer))) 
			{
				rightAnswers++;
			}
			
		}  
		//EXAM QUESTIONS END\\
		
		//TIMER FINISH START\\
		long endTime = System.currentTimeMillis();//end timer
		long seconds = (endTime - startTime) / 1000;//calculate time until completion in seconds
		//TIMER FINISH END\\
		
		//RECORD AND DISPLAY EXAM RESULT START\\
		int examScore = (int)Math.round((((double)rightAnswers)/numberOfQuestions)*100);
		userValues.setAttemptTimeStamp(timeStamp);
		userValues.setAttemptScore(examScore);
		userValues.setAttemptDuration(toIntExact(seconds));
    	writeResult(userValues);
		if (passFailCutoff <= examScore) {
			System.out.println("Congratulations " + userValues.getFirstName() + " " + userValues.getLastName() + "! You scored a " + examScore + "!");
		} else {
			System.out.println("Sorry " + userValues.getFirstName() + " " + userValues.getLastName() + ". You scored a " + examScore + ". Better luck next time.");
		}
		//RECORD AND DISPLAY EXAM RESULT END\\
	}  
	
	public static String getInput() {
		String input = "";
		input = scanner.nextLine();
		return input;
	}

	public static HashMap< String,Integer> initExam() {
		DBConnect db = new DBConnect();
		HashMap< String,Integer> settingvalues = db.getSettingValues();
		return settingvalues;
	}

	public static ExamTaker doLogin() {
		DBConnect db = new DBConnect();
		ExamTaker returnvalues = new ExamTaker();
		int foundUserName = 0;
		int startuserid = -1;
		while (foundUserName == 0) {
		System.out.print("Please enter your username: ");
		String username = getInput();
		System.out.print("Please enter your password: ");
		String password = getInput();
		startuserid = db.checkLogin(username, password);
		if (startuserid < 1) {
			System.out.println("Login not found. Are you a new user? Enter Y to create account.");
			String newUser = getInput();
			if (newUser.equalsIgnoreCase("y")) {
				startuserid = createUser();
			}
		} 
		if (startuserid > 0) {
			foundUserName = 1;
		} 
		}
		returnvalues = db.getUserValues(startuserid);
		return returnvalues;
	}

	public static int createUser() {
		DBConnect db = new DBConnect();
		int userid= -1;
		
		System.out.println("Please enter your username: ");
		String username = getInput();
		System.out.println("Please enter your password: ");
		String password = getInput();
		System.out.println("Please enter your last name: ");
		String lastname = getInput();
		System.out.println("Please enter your first name: ");
		String firstname = getInput();
		System.out.println("Please enter your email address: ");
		String email = getInput();
		userid = db.addUser(username, password, email, firstname, lastname);
		return userid;
	}

	
	public static void writeResult(ExamTaker takerInfo) {
		DBConnect db = new DBConnect();
		db.addExamHistory(takerInfo);
	}
	
	
	public static String findPath() {
	    //MAKE PATH AS DYNAMIC AS POSSIBLE START\\
	    String pathToClass = JavaExam.class.getName();
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

	
	public static List<QuestionWithAnswer> generateTest(int numberOfQuestions, int numberOfAnswers) throws NumberFormatException, IOException {  
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




