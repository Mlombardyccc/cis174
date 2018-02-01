package edu.yccc.cis174.michaellombard.bigproject1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;



public class JavaExam {

	public static void main(String[] args) throws NumberFormatException, IOException {  
		
		//TESTING VARIABLE INITIALIZATION START\\
		int numberOfQuestions = 10;//maximum number of questions on the test
		int numberOfAnswers = 4;//maximum number of answers per question
		int passFailCutoff = 70;//Happy result/unhappy result changeover point
		//TESTING VARIABLE INITIALIZATION END\\
		
		//GENERATE EXAM START\\
		List<QuestionWithAnswer> qalist = GenerateTest(numberOfQuestions,numberOfAnswers);	//Generate this exam
		//GENERATE EXAM END\\

		//GET EXAM TAKER DATA START\\
		Scanner scanner = new Scanner(System.in);//initialize input detection
		System.out.print("Please enter your last name : ");
		String lastName = scanner.nextLine();
		System.out.print("Please enter your first name : ");
		String firstName = scanner.nextLine();
		//GET EXAM TAKER DATA END\\
		
		//WAIT FOR READINESS CONFIRMATION START\\
		System.out.print("Press enter when ready to begin test...");
		scanner.nextLine();
		//WAIT FOR READINESS CONFIRMATION END\\
		
		//TIMER AND TIMESTAMP INITIALIZATION START\\
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//init timestamp with start of exam not start of registration
		int rightAnswers = 0;//initialize counter for answers chosen correctly
		long startTime = System.currentTimeMillis();//Start timer
		//TIMER AND TIMESTAMP INITIALIZATION END\\
		
		//EXAM QUESTIONS START\\
		for(QuestionWithAnswer qa:qalist){  
			System.out.println(qa.question + "\n\n" + qa.answer);//display question with possible answers
			String answer = "";
			while (answer.length() != 1) {//if answer is blank or more than 1 character, repeat request for answer
				answer = scanner.nextLine();//await answer
				if (answer.length() != 1) {System.out.println("Invalid Answer, try again. \n");}
			}
			if (answer.equalsIgnoreCase(qa.correctAnswer)) {rightAnswers++;}
		}  
		scanner.close();//close input detector
		//EXAM QUESTIONS END\\
		
		//TIMER FINISH START\\
		long endTime = System.currentTimeMillis();//end timer
		long seconds = (endTime - startTime) / 1000;//calculate time until completion in seconds
		//TIMER FINISH END\\
		
		//RECORD AND DISPLAY EXAM RESULT START\\
		int examScore = (int)Math.round((((double)rightAnswers)/numberOfQuestions)*100);
		String takerInfo = lastName + "-:;,-" + firstName +  "-:;,-" + timeStamp +  "-:;,-" + examScore +  "-:;,-" + seconds;
    	writeResult(takerInfo);
		if (passFailCutoff <= examScore) {
			System.out.println("Congratulations " + firstName + " " + lastName + "! You scored a " + examScore + "!");
		} else {
			System.out.println("Sorry " + firstName + " " + lastName + ". You scored a " + examScore + ". Better luck next time.");
		}
		//RECORD AND DISPLAY EXAM RESULT END\\
	}  

	
	public static void writeResult(String takerInfo) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		
	    try {
			File file = new File(findPath() + "takers.sto");
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);
			bw.write(takerInfo);
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

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

	
	public static List<QuestionWithAnswer> GenerateTest(int numberOfQuestions, int numberOfAnswers) throws NumberFormatException, IOException {  
		int fileNumberOfQuestions = 0;//initialize counter for number of questions in the file
		if (numberOfAnswers > 26) { //checks if more answers per question selected than letters in alphabet and sets to max letters if so
			numberOfAnswers = 26;
		}

	    List<ExamQuestion> questionlist = new ArrayList<ExamQuestion>();
		List<ExamAnswer> answerlist = new ArrayList<ExamAnswer>();
		List<QuestionWithAnswer> qalist = new ArrayList<QuestionWithAnswer>();
	    String thisLine = null;


	    //Import Questions
		try {
            BufferedReader inFile = new BufferedReader(new FileReader(findPath() + "questions.sto"));
			while ((thisLine = inFile.readLine()) != null) {
				String[] items = thisLine.split("-:;,-");
				questionlist.add(new ExamQuestion(Integer.valueOf(items[0]),items[1],items[2],Integer.valueOf(items[3])));
				fileNumberOfQuestions++;
			}
			
			if (numberOfQuestions > fileNumberOfQuestions) {
				numberOfQuestions = fileNumberOfQuestions;
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Import Answers
		try {
            BufferedReader inFile = new BufferedReader(new FileReader(findPath() + "answers.sto"));
			while ((thisLine = inFile.readLine()) != null) {
				String[] items = thisLine.split("-:;,-");
				answerlist.add(new ExamAnswer(Integer.valueOf(items[0]),Integer.valueOf(items[1]),items[2],Boolean.valueOf(items[3])));
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	    List<ExamQuestion> copy = new LinkedList<ExamQuestion>(questionlist);
	    Collections.shuffle(copy);
	    List<ExamQuestion> randQuestions = copy.subList(0, numberOfQuestions);//randomize questions and set to number selected by numberOfQuestions

	    int currentQuestion = 1;//initialize question counter

		//Combine Questions and Answers		
	    for(ExamQuestion r:randQuestions){  
			List<ExamAnswer> sa = SortedAnswers(numberOfAnswers, r.id, answerlist);
			String thisQuestion = "";
		    int currentAnswer = 1;
		    int rightAnswer = 0;
		    String wholeAnswer = "";
		    for(ExamAnswer a:sa){  
				wholeAnswer = wholeAnswer + ConvertToLetter(currentAnswer) + ") " + a.answer + "\n";
				if (a.isCorrect) {rightAnswer = currentAnswer;}
				currentAnswer++;			
		    }
			wholeAnswer = wholeAnswer + "\n";
			thisQuestion = currentQuestion + ") " + r.question;
		    qalist.add(new QuestionWithAnswer(thisQuestion,wholeAnswer,ConvertToLetter(rightAnswer)));
		    currentQuestion++;
		}

	    return qalist;
	}  


    public static List<ExamAnswer> SortedAnswers(int listLength, int questionId, List<ExamAnswer> listToSort) {//Sorts and shuffles exam answers
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

    
    public static String ConvertToLetter(int letterId) {
        return letterId > 0 && letterId < 27 ? String.valueOf((char)(letterId + 64)) : null;//find and return ascii value of modified number(limits to capital letters)
    }
}




