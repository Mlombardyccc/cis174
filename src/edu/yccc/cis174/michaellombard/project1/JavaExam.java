package edu.yccc.cis174.michaellombard.project1;

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
		Scanner scanner = new Scanner(System.in);
		int numberOfQuestions = 10;//maximum number of questions on the test
		int numberOfAnswers = 4;//maximum number of answers per question
		int passFailCutoff = 70;//Happy result/unhappy result changeover point
		
		int rightAnswers = 0;
		List<QuestionWithAnswer> qalist = GenerateTest(numberOfQuestions,numberOfAnswers);	
		//get tester info
		System.out.print("Please enter your last name : ");
		String lastName = scanner.nextLine();
		System.out.print("Please enter your first name : ");
		String firstName = scanner.nextLine();
		System.out.print("Press enter when ready to begin test...");
		scanner.nextLine();
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//init timestamp with start of exam not start of registration
		long startTime = System.currentTimeMillis();//Start timer
		for(QuestionWithAnswer qa:qalist){  
			System.out.println(qa.question + "\n\n" + qa.answer);//display question with possible answers
			String answer = "";
			while (answer.length() != 1) {//if answer is blank or more than 1 character, repeat request for answer
				answer = scanner.nextLine();//await answer
				if (answer.length() != 1) {System.out.println("Invalid Answer, try again. \n");}
			}
			if (answer.equalsIgnoreCase(qa.correctAnswer)) {rightAnswers++;}
		}  

		long endTime = System.currentTimeMillis();//end timer
		long seconds = (endTime - startTime) / 1000;//calculate time until completion in seconds
		scanner.close();
		//record and display result of exam
		int examScore = (int)Math.round((((double)rightAnswers)/numberOfQuestions)*100);
		String takerInfo = lastName + "-:;,-" + firstName +  "-:;,-" + timeStamp +  "-:;,-" + examScore +  "-:;,-" + seconds;

		BufferedWriter bw = null;
		FileWriter fw = null;
	    String pathToClass = JavaExam.class.getName();
	    pathToClass = pathToClass.replace(".", ",");
	    String[] pathItems = pathToClass.split(",");
	    String pathToClassDir = "";
	    for (int i = 0; i < (pathItems.length - 1); i++) {
	    	pathToClassDir = pathToClassDir + pathItems[i] + "\\\\";
	    }
	    String pathToFile = System.getProperty("user.dir").replace("\\","\\\\") + "\\\\src\\\\" + pathToClassDir;
		try {
			File file = new File(pathToFile + "takers.sto");
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

		if (passFailCutoff <= examScore) {
			System.out.println("Congratulations " + firstName + " " + lastName + "! You scored a " + examScore + "!");
		} else {
			System.out.println("Sorry " + firstName + " " + lastName + ". You scored a " + examScore + ". Better luck next time.");
		}
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
	    String pathToClass = JavaExam.class.getName();
	    pathToClass = pathToClass.replace(".", ",");
	    String[] pathItems = pathToClass.split(",");
	    String pathToClassDir = "";
	    for (int i = 0; i < (pathItems.length - 1); i++) {
	    	pathToClassDir = pathToClassDir + pathItems[i] + "\\\\";
	    }
	    String pathToFile = System.getProperty("user.dir").replace("\\","\\\\") + "\\\\src\\\\" + pathToClassDir;
	    //Import Questions
		try {
            BufferedReader inFile = new BufferedReader(new FileReader(pathToFile + "questions.sto"));
			while ((thisLine = inFile.readLine()) != null) {
//				System.out.println(thisLine);  
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
            BufferedReader inFile = new BufferedReader(new FileReader(pathToFile + "answers.sto"));
			while ((thisLine = inFile.readLine()) != null) {
//	     		System.out.println(thisLine);  
				String[] items = thisLine.split("-:;,-");
				answerlist.add(new ExamAnswer(Integer.valueOf(items[0]),Integer.valueOf(items[1]),items[2],Boolean.valueOf(items[3])));
			}
			inFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

/*		for(ExamQuestion q:questionlist){  
			System.out.println(q.id+" "+q.question+" "+q.category+" "+q.difficulty);  
		}  
		for(ExamAnswer a:answerlist){  
			System.out.println(a.id+" "+a.questionId+" "+a.answer+" "+a.isCorrect);
		}
*/	    List<ExamQuestion> copy = new LinkedList<ExamQuestion>(questionlist);
	    Collections.shuffle(copy);
	    List<ExamQuestion> randQuestions = copy.subList(0, numberOfQuestions);//randomize questions and set to number selected by numberOfQuestions

	    int currentQuestion = 1;//initialize question counter

		//Combine Questions and Answers		
	    for(ExamQuestion r:randQuestions){  
//			System.out.println(r.id+" "+r.question+" "+r.category+" "+r.difficulty);
			List<ExamAnswer> sa = SortedAnswers(numberOfAnswers, r.id, answerlist);
			String thisQuestion = "";
		    int currentAnswer = 1;
		    int rightAnswer = 0;
		    String wholeAnswer = "";
		    for(ExamAnswer a:sa){  
//				System.out.println(a.id+" "+a.questionId+" "+a.answer+" "+a.isCorrect);
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

	
	

    public static List<ExamAnswer> SortedAnswers(int listLength, int questionId, List<ExamAnswer> listToSort) {
	    List<ExamAnswer> copy = new LinkedList<ExamAnswer>(listToSort);
	    List<ExamAnswer> copyb = new ArrayList<ExamAnswer>();
		int numAnswers = 0;
	    Collections.shuffle(copy);
	    for(ExamAnswer a:copy){ 
			if (questionId == a.questionId) {
			copyb.add(new ExamAnswer(a.id,a.questionId,a.answer,a.isCorrect));		
			numAnswers++;
			}
			}
	    if (listLength > numAnswers) {listLength = numAnswers;}
	    Collections.sort(copyb, ExamAnswer.ExamIsCorrect);
	    List<ExamAnswer> sortedAnswers = copyb.subList(0, listLength);
	    Collections.shuffle(sortedAnswers);
    	return sortedAnswers;
    }

    public static String ConvertToLetter(int letterId) {
        return letterId > 0 && letterId < 27 ? String.valueOf((char)(letterId + 64)) : null;
    }
}




