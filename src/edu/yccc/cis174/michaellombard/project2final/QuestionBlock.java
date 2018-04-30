package edu.yccc.cis174.michaellombard.project2final;

import java.util.List;

public interface QuestionBlock {
	
	public List<QuestionWithAnswer> createQASet(int categoryID, int minq, int maxq, int mina, int maxa, int randomizeq, int randomizea);

	public List<QuestionWithAnswer> createQASet(int categoryID, int maxq, int maxa);
	
	public boolean inputCorrect(String userinput);
	
	public boolean answerCorrect(String userinput, String correctAnswer);
	
}
