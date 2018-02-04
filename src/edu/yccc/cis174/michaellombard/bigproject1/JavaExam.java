package edu.yccc.cis174.michaellombard.bigproject1;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
		HashMap< String,Integer> initvalues = ExamFunction.initExam();
		int numberOfQuestions = initvalues.get("numberOfQuestions");//maximum number of questions on the test
		int numberOfAnswers = initvalues.get("numberOfAnswers");//maximum number of answers per question
		int passFailCutoff = initvalues.get("passFailCutoff");//Happy result/unhappy result changeover point
		int answerType = initvalues.get("answerType");//Type of question numbers or letters
		//TESTING VARIABLE INITIALIZATION END\\
		
		//GENERATE EXAM START\\
		List<QuestionWithAnswer> qalist = ExamFunction.generateTest(numberOfQuestions,numberOfAnswers);	//Generate this exam
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
		ExamFunction.writeResult(userValues);
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
	
 }




