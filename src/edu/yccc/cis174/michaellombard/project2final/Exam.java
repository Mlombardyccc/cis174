package edu.yccc.cis174.michaellombard.project2final;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

 

public class Exam {

	public static void main(String[] args) {  

		DBConnect udb = new DBConnect();
		int qCount = 0;//initialize question total counter
		
		//TESTING VARIABLE INITIALIZATION START\\
		int numberOfQuestions = 10;//maximum number of questions on the test
		int numberOfAnswers = 4;//maximum number of answers per question
		int passFailCutoff = 70;//Happy result/unhappy result changeover point
		ExamTaker et = new ExamTaker();
		//TESTING VARIABLE INITIALIZATION END\\
		
		//GENERATE EXAM START\\
		QBMultipleChoice qbm = new QBMultipleChoice();
		QBTrueFalse qbtf = new QBTrueFalse();
		qbm.createQASet(1,numberOfQuestions,numberOfAnswers);	//Generate mc exam
		qbtf.createQASet(2,numberOfQuestions,numberOfAnswers);	//Generate tf exam
		//GENERATE EXAM END\\

		//GET EXAM TAKER DATA START\\
		Scanner scanner = new Scanner(System.in);//initialize input detection
		System.out.print("Please enter your last name : ");
		et.setLastName(scanner.nextLine());
		System.out.print("Please enter your first name : ");
		et.setFirstName(scanner.nextLine());
		//GET EXAM TAKER DATA END\\
		
		//WAIT FOR READINESS CONFIRMATION START\\
		System.out.print("Press enter when ready to begin test...");
		scanner.nextLine();
		//WAIT FOR READINESS CONFIRMATION END\\
		
		//TIMER AND TIMESTAMP INITIALIZATION START\\
		et.setAttemptTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		int rightAnswers = 0;//initialize counter for answers chosen correctly
		long startTime = System.currentTimeMillis();//Start timer
		//TIMER AND TIMESTAMP INITIALIZATION END\\
		
		//EXAM QUESTIONS START\\
		for(int i = 0; i < qbm.questionCount; i++){
			QuestionWithAnswer thisQA = qbm.getQA();
			if (i != 0) {
				thisQA = qbm.getNextQA();
			}
			System.out.println(thisQA.getQuestion() + "\n\n" + thisQA.getAnswer());//display question with possible answers
			String answer = "";
			while (!qbm.inputCorrect(answer)) {//if answer is blank or more than 1 character, repeat request for answer
				answer = scanner.nextLine();//await answer
			}
			if (qbm.answerCorrect(answer)) {rightAnswers++;}
			qCount++;
		}  
		for(int i = 0; i < qbtf.questionCount; i++){  
			QuestionWithAnswer thisQA = qbtf.getQA();
			if (i != 0) {
				thisQA = qbtf.getNextQA();
			}
			System.out.println(thisQA.getQuestion() + "\n\n" + thisQA.getAnswer());//display question with possible answers
			String answer = "";
			while (!qbtf.inputCorrect(answer)) {//if answer is blank or more than 1 character, repeat request for answer
				answer = scanner.nextLine();//await answer
			}
			if (qbtf.answerCorrect(answer)) {rightAnswers++;}
			qCount++;
		}  
		scanner.close();//close input detector
		//EXAM QUESTIONS END\\
		
		//TIMER FINISH START\\
		long endTime = System.currentTimeMillis();//end timer
		et.setAttemptDuration((int)((endTime - startTime) / 1000));//calculate time until completion in seconds
		//TIMER FINISH END\\
		
		//RECORD AND DISPLAY EXAM RESULT START\\
		et.setAttemptScore((int)Math.round((((double)rightAnswers)/qCount)*100));
    	udb.saveExam(et);
		if (passFailCutoff <= et.getAttemptScore()) {
			System.out.println("Congratulations " + et.getFirstName() + " " + et.getLastName() + "! You scored a " + et.getAttemptScore() + "!");
		} else {
			System.out.println("Sorry " + et.getFirstName() + " " + et.getLastName() + ". You scored a " + et.getAttemptScore() + ". Better luck next time.");
		}
		//RECORD AND DISPLAY EXAM RESULT END\\
	}  

	
}