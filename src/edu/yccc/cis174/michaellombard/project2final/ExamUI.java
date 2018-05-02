package edu.yccc.cis174.michaellombard.project2final;

/*****************************
OBJECTIVES - FINAL - MICHAEL LOMBARD

1. Develop Abstract Class version of Exam Builder
2. Provide GUI instead of console interface
3. Make fully database driven
4. Use modal windows
5. Provide tool to view exam history
*****************************/
import java.awt.EventQueue;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JToolBar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ExamUI {
//ui variables
	private JFrame frmExamTaker;
	private JTextField answerField;
	JLabel lblTestName = new JLabel("Test Name");
	JLabel lblQuestion = new JLabel("Question");
	JLabel lblAnswer = new JLabel("Answer");
	JButton button = new JButton("<<<");
	JButton btnScoreExam = new JButton("Score Exam");
	JButton btnLogin = new JButton("Login");
	JButton btnLogout = new JButton("Logout");
	JButton button_1 = new JButton(">>>");
	JLabel lblQofX = new JLabel("Question Q of X");
	JPanel panel_1 = new JPanel();
	
	//test variables	
	DBConnect udb = new DBConnect();
	QBMultipleChoice qbm = new QBMultipleChoice();
	QBTrueFalse qbtf = new QBTrueFalse();
	QBFillBlank qbfb = new QBFillBlank();
	QuestionWithAnswer thisQA = new QuestionWithAnswer();
	int thisQuestion = 0;
	int qbmcount = 0;
	int qbtfcount = 0;
	int qbfbcount = 0;
	int qCount = 0; 
	int numberOfQuestions = 10;//maximum number of questions on the test
	int numberOfAnswers = 4;//maximum number of answers per question
	int passFailCutoff = 70;//Happy result/unhappy result changeover point
	ExamTaker et = new ExamTaker();
	long startTime = 0;
	Map<String,String> savedAnswers = new HashMap<>();

	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExamUI window = new ExamUI();
					window.frmExamTaker.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ExamUI() {
		initialize();
		doLogin();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExamTaker = new JFrame();
		frmExamTaker.setTitle("Exam Taker");
		frmExamTaker.setBounds(100, 100, 741, 549);
		frmExamTaker.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setBackground(new Color(192, 192, 192));
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 245, 245));
		
		

		GroupLayout groupLayout = new GroupLayout(frmExamTaker.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBar, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 451, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
		);
		
		
		panel.setLayout(new MigLayout("", "[100px:n][grow][100px:n]", "[]"));
		panel.add(lblQofX, "cell 1 0,alignx center");
		lblQofX.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		lblQofX.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		
		
		panel_1.setLayout(new MigLayout("fill", "[][grow][]", "[][][][][][][]"));
		
		lblTestName.setFont(new Font("Times New Roman", Font.BOLD, 18));
		panel_1.add(lblTestName, "cell 1 0,alignx center");
		
		lblQuestion.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(lblQuestion, "cell 1 2");
		
		lblAnswer.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(lblAnswer, "cell 1 3");
		
		answerField = new JTextField();
		panel_1.add(answerField, "flowx,cell 1 4,growx");
		answerField.setColumns(10);
		
		btnLogin.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						LoginDialog loginDlg = new LoginDialog(frmExamTaker);
						loginDlg.setVisible(true);
						// if logged on successfully
						if(loginDlg.isSucceeded()){
							et = loginDlg.getExamTaker();
							thisQuestion = 0;
							qCount = 0;
							startExam();
							btnLogin.setVisible(false);
							doShowMain(true);
						}
					}
				});

		btnLogout.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						doShowMain(false);
						btnLogout.setVisible(false);
						btnLogin.setVisible(true);
					}
				});

		
		
		JLabel label = new JLabel("");
		panel_1.add(label, "cell 1 4,grow");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAnswer();
				nextQuestion();
				answerField.setText("");
				showAnswer();
				answerField.requestFocus();
			}
		});
		

		btnScoreExam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAnswer();
				scoreExam();
			}
		});
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveAnswer();
				prevQuestion();
				answerField.setText("");
				showAnswer();
				answerField.requestFocus();
			}
		});
		
		button.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(button, "cell 0 6");
		
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(btnLogin, "cell 1 6,alignx center");
		btnLogin.setVisible(true);

		btnLogout.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(btnLogout, "cell 1 6,alignx center");
		btnLogout.setVisible(false);

		btnScoreExam.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(btnScoreExam, "cell 1 6,alignx center");
		btnScoreExam.setVisible(false);
		
		button_1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		panel_1.add(button_1, "cell 2 6");
		frmExamTaker.getContentPane().setLayout(groupLayout);
	}
	
	public void doLogin() {
		doShowMain(false);

	}


	private void startExam() {

		
		String examName = "Basic Exam";

		button_1.setEnabled(true);

		
		//TESTING VARIABLE INITIALIZATION START\\

		//TESTING VARIABLE INITIALIZATION END\\
		
		//GENERATE EXAM START\\
		qbmcount = 0;
		qbtfcount = 0;
		qbfbcount = 0;
		qCount = 0; 

		lblTestName.setText(examName);
		
		qbm.createQASet(1,numberOfQuestions,numberOfAnswers);	//Generate mc exam
		qbtf.createQASet(2,numberOfQuestions,numberOfAnswers);	//Generate tf exam
		qbfb.createQASet(3,numberOfQuestions,numberOfAnswers);	//Generate tf exam
		qbmcount = qbm.getQuestionCount();
		qbtfcount = qbtf.getQuestionCount();
		qbfbcount = qbfb.getQuestionCount();
		qCount = qbmcount + qbtfcount + qbfbcount; 

		//GENERATE EXAM END\\

		//TIMER AND TIMESTAMP INITIALIZATION START\\
		startTime = System.currentTimeMillis();//Start timer
		//TIMER AND TIMESTAMP INITIALIZATION END\\
		
		//EXAM QUESTIONS START\\
		
		//init default question and answer
		QuestionWithAnswer thisQA = qbm.getQA();
		lblQuestion.setText((thisQuestion+1) + ") " + thisQA.getQuestion());
		lblAnswer.setText(thisQA.getAnswer());
		button.setEnabled(false);
		setQLabel();
	}
	
	private void showAnswer() {		
		if (savedAnswers.containsKey(String.valueOf(thisQuestion))) {
			answerField.setText(savedAnswers.get(String.valueOf(thisQuestion)));
		}
	}
	
	private void nextQuestion() {		
		thisQuestion++;
		if (thisQuestion == 1) {
			button.setEnabled(true);
		}
		if (thisQuestion < qbmcount) {
			qbm.setCurrentQuestion(thisQuestion);
			thisQA = qbm.getQA();

		} else if (thisQuestion < (qbmcount + qbtfcount)) {
			qbtf.setCurrentQuestion(thisQuestion-qbmcount);
		
			thisQA = qbtf.getQA();

		} else if (thisQuestion < (qbmcount + qbtfcount + qbfbcount)) {
			qbfb.setCurrentQuestion(thisQuestion-qbmcount-qbtfcount);
			
			thisQA = qbfb.getQA();
		}
		lblQuestion.setText((thisQuestion+1) + ") " + thisQA.getQuestion());
		lblAnswer.setText(thisQA.getAnswer());

		if (thisQuestion >= (qCount - 1)) {
			button_1.setEnabled(false);
			btnScoreExam.setVisible(true);
			
		}
		setQLabel();
	}
	
	private void prevQuestion() {		
		thisQuestion--;
		if (thisQuestion == 0) {
			button.setEnabled(false);
		}
		if (thisQuestion < qbmcount) {
			thisQA = qbm.getQA(thisQuestion);

		} else if (thisQuestion < (qbmcount + qbtfcount)) {
			thisQA = qbtf.getQA(thisQuestion-qbmcount);

		} else if (thisQuestion < (qbmcount + qbtfcount + qbfbcount)) {
			thisQA = qbfb.getQA(thisQuestion-qbmcount-qbtfcount);
		}
		
		lblQuestion.setText((thisQuestion+1) + ") " + thisQA.getQuestion());
		lblAnswer.setText(thisQA.getAnswer());
		if (thisQuestion < qCount) {
			button_1.setEnabled(true);
			btnScoreExam.setVisible(false);
		}
		setQLabel();
	}
	
	private void saveAnswer() {
		savedAnswers.put(String.valueOf(thisQuestion), answerField.getText());
		
	}
	
	private void setQLabel() {
		lblQofX.setText("" + (thisQuestion + 1) + " of " + qCount);
	}
	
	private void doShowMain(boolean show) {
    	lblAnswer.setVisible(show);
    	lblTestName.setVisible(show);
    	lblQuestion.setVisible(show);
    	lblQofX.setVisible(show);
		button.setVisible(show);
		button_1.setVisible(show);
		btnScoreExam.setVisible(false);
		answerField.setVisible(show);
	}

/*	private void doShowLogin(boolean show) {
		btnLogin.setVisible(show);
	}
*/	
	private void scoreExam() {
		//TIMER FINISH START\\
		long endTime = System.currentTimeMillis();//end timer
		et.setAttemptDuration((int)((endTime - startTime) / 1000));//calculate time until completion in seconds
		//TIMER FINISH END\\
		//compare answers
		int rightAnswers = 0;
		for(int i = 0; i < qCount; i++) {

			if (i < qbmcount) {
				if (savedAnswers.containsKey(String.valueOf(i)) ) {
//					System.out.println(savedAnswers.get(String.valueOf(i)));
					qbm.setCurrentQuestion(i);
					if (qbm.answerCorrect(savedAnswers.get(String.valueOf(i)))) {
						rightAnswers++;
					}
				}

			} else if (i < (qbmcount + qbtfcount)) {
				if (savedAnswers.containsKey(String.valueOf(i)) ) {
					qbtf.setCurrentQuestion(i-qbmcount);
					if (qbtf.answerCorrect(savedAnswers.get(String.valueOf(i)))) {
						rightAnswers++;
					}
				}
			} else if (i < (qbmcount + qbtfcount + qbfbcount)) {
				if (savedAnswers.containsKey(String.valueOf(i)) ) {
					qbfb.setCurrentQuestion(i-qbmcount-qbtfcount);
					if (qbfb.answerCorrect(savedAnswers.get(String.valueOf(i)))) {
						rightAnswers++;
					}
				}
			}
		}
		
		
		//end compare answers
		//RECORD AND DISPLAY EXAM RESULT START\\
		et.setAttemptScore((int)Math.round((((double)rightAnswers)/qCount)*100));
		et.setAttemptTimeStamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		udb.saveExam(et);
    	lblAnswer.setText("");
		button.setVisible(false);
		button_1.setVisible(false);
		btnScoreExam.setVisible(false);
		answerField.setVisible(false);
    	lblQofX.setVisible(false);

		if (passFailCutoff <= et.getAttemptScore()) {
			lblQuestion.setText("Congratulations " + et.getFirstName() + " " + et.getLastName() + "! You scored a " + et.getAttemptScore() + "!");
		} else {
			lblQuestion.setText("Sorry " + et.getFirstName() + " " + et.getLastName() + ". You scored a " + et.getAttemptScore() + ". Better luck next time.");
		}
		btnLogout.setVisible(true);
		//RECORD AND DISPLAY EXAM RESULT END\\
	}  
}

