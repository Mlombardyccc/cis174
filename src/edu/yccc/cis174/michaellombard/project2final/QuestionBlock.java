package edu.yccc.cis174.michaellombard.project2final;

import java.util.ArrayList;
import java.util.List;

abstract public class QuestionBlock {
	
	boolean reorder;
	List<String> wrongset = new ArrayList<String>();
	DBConnect db = new DBConnect();	


	
	public List<QuestionWithAnswer> createQASet(int categoryID, int minq, int maxq, int mina, int maxa, int randomizeq, int randomizea) {
	
		return null;
	}
	
	public List<QuestionWithAnswer> createQASet(int categoryID, int maxq, int maxa) {
		
		return null;
	}
		
	
	public boolean inputCorrect(String userinput) {
		if (userinput.equalsIgnoreCase("t") || userinput.equalsIgnoreCase("true") || userinput.equalsIgnoreCase("f") || userinput.equalsIgnoreCase("false")) {
			return true;
		} else {
			return false;
		}
	}
		
	
	public boolean answerCorrect(String userinput, String correctAnswer) {
		if (userinput.equalsIgnoreCase(correctAnswer)) {
			return true;
		} else {
			return false;
		}
	}
		
	
}
