package edu.yccc.cis174.michaellombard.bigprojectA;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;


public class JExamUI {

	private JFrame frame;
	static DBConnect db = new DBConnect();
	static ExamTaker et = new ExamTaker();
	static ExamFunction ef = new ExamFunction();
	private Map<ButtonModel, JRadioButton> modelToRadioButton = new LinkedHashMap<ButtonModel, JRadioButton>();
	static List<QuestionWithAnswer> qalist;
	static List<String> correctAnswers= new ArrayList<String>();
	static List<ButtonGroup> answerGroup = new ArrayList<ButtonGroup>();
	static List<String> selectedAnswer = new ArrayList<String>();
	static int examScore;
	static int numberCorrect = 0;
	JPanel cards = new JPanel(new CardLayout());
	JPanel sPaneAvailExams = new JPanel(new MigLayout("", "[grow]", "[][]"));
	JPanel sPaneCompExams = new JPanel(new MigLayout("", "[grow]", "[][]"));
	JButton btnStart;
	JPanel examChoicePanel;
	JPanel waitPanel;
	JPanel gradingPanel;

	//TESTING VARIABLE INITIALIZATION START\\
	static HashMap< String,String> initvalues = ExamFunction.initExam();
	int numberOfQuestions;//maximum number of questions on the test
	int numberOfAnswers;//maximum number of answers per question
	int passFailCutoff = Integer.parseInt(initvalues.get("passFailCutoff"));//Happy result/unhappy result changeover point
	int answerType = Integer.parseInt(initvalues.get("answerType"));//Type of question numbers or letters
	int examID; //Integer.parseInt(initvalues.get("name"));//Type of question numbers or letters
	//TESTING VARIABLE INITIALIZATION END\\

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JExamUI window = new JExamUI();
					window.frame.setVisible(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JExamUI() {
		initialize();
		frame.getContentPane().setLayout(new MigLayout("", "[1920px]", "[30px,top][152px,grow,center][30px,bottom]"));
		frame.setMinimumSize(new Dimension(400, 300));
		frame.setMaximumSize(new Dimension(1920, 1080));

		JPanel top_panel = new JPanel();
		//		top_panel.setBackground(Color.PINK);
		top_panel.setBorder(null);
		top_panel.setLayout(new MigLayout("", "[80px][grow][80px]", "[23px]"));
		frame.getContentPane().add(top_panel, "cell 0 0,growx");
		final JButton btnLogin = new JButton("Login");
		final JButton btnScore = new JButton("Score Exam");
		final JButton btnLogout = new JButton("Logout");
		btnStart = new JButton("Start Exam");
		
		JLabel lblWelcomeToThe = new JLabel("Welcome to the Java Exam Maker, Please Login.");
		JLabel lblLoggedIn = new JLabel("");
		btnStart.setVisible(false);
		lblLoggedIn.setVisible(false);

		top_panel.add(lblWelcomeToThe, "cell 1 0 1 1,alignx center,aligny center");
		top_panel.add(lblLoggedIn, "cell 0 0,alignx left,aligny center");
		top_panel.add(btnLogin, "cell 2 0,alignx right,aligny center");
		top_panel.add(btnLogout, "cell 2 0,alignx right,aligny center");
		btnLogout.setVisible(false);
		JPanel mid_panel = new JPanel(new BorderLayout());
		//		mid_panel.setBackground(Color.ORANGE);
		frame.getContentPane().add(mid_panel, "cell 0 1,grow");




		cards.setBounds(0, 0, 660, 367);
		cards.setLocation(0, 0);

		waitPanel = new JPanel();
		waitPanel.setBounds(0, 0, 670, 367);
		waitPanel.setLayout(new MigLayout("", "[][grow][]", "[][grow][]"));
		waitPanel.add(btnStart, "cell 0 0 3 3,alignx center,aligny center, grow");
		mid_panel.add(waitPanel);

		mid_panel.add(cards);
		gradingPanel = new JPanel();
		gradingPanel.setLayout(new BorderLayout(0, 0));
		gradingPanel.setBounds(0, 0, 670, 367);
		JLabel lblGradingText = new JLabel("Grading Exam");
		ImageIcon icon = new ImageIcon(ExamFunction.findPath() + "true.gif");
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		gradingPanel.add(lblGradingText);
		gradingPanel.add(thumb);
		mid_panel.add(gradingPanel);
		gradingPanel.setVisible(false);
		
		JPanel gradePanel = new JPanel();
		gradePanel.setLayout(new BorderLayout(0, 0));
		gradePanel.setBounds(0, 0, 670, 367);
		mid_panel.add(gradePanel);
		
		JLabel lblGradeText = new JLabel("You scored a __ on this Exam.");
		lblGradeText.setHorizontalAlignment(SwingConstants.CENTER);
		gradePanel.add(lblGradeText);
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.setHorizontalAlignment(SwingConstants.CENTER);
		gradePanel.add(btnContinue);
		
		examChoicePanel = new JPanel();
		examChoicePanel.setBounds(0, 0, 670, 367);
		mid_panel.add(examChoicePanel);
		examChoicePanel.setLayout(new MigLayout("", "[grow][grow]", "[20px][grow]"));
		JScrollPane scrollPaneAvailExams = new JScrollPane(sPaneAvailExams);
		JScrollPane scrollPaneCompExams = new JScrollPane(sPaneCompExams);
		
		examChoicePanel.add(scrollPaneAvailExams, "cell 0 1,alignx left,grow");
		
		JLabel lblAvailableExams = new JLabel("Available Exams");
		examChoicePanel.add(lblAvailableExams, "cell 0 0,alignx center");
		
		JLabel lblCompletedExams = new JLabel("Completed Exams");
		examChoicePanel.add(lblCompletedExams, "cell 1 0,alignx center");


		examChoicePanel.add(scrollPaneCompExams, "cell 1 1,alignx left,grow");


		
		cards.setVisible(false);
		gradePanel.setVisible(false);
		examChoicePanel.setVisible(true);

		buildExam();




		JPanel btm_panel = new JPanel();
		//		btm_panel.setBackground(Color.CYAN);
		frame.getContentPane().add(btm_panel, "cell 0 2,grow");
		btm_panel.setLayout(new MigLayout("", "[216][grow][216]", "[23]"));

		JLabel lblStartTime = new JLabel("Start Time");
		btm_panel.add(lblStartTime, "cell 0 0");
		lblStartTime.setVisible(false);

		btm_panel.add(btnScore, "cell 1 0,alignx center");
		btnScore.setVisible(false);

		JLabel lblElapsedTime = new TimerLabel();
		btm_panel.add(lblElapsedTime, "cell 2 0,alignx right");
		lblElapsedTime.setVisible(false);


		btnScore.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						boolean unanswered = false;
						boolean grade = false;
						for(ButtonGroup bg:answerGroup) {
							if (bg.getSelection() == null) {
								unanswered = true;
							}
						}
						if (unanswered) {
							AnswerCheck unquest = new AnswerCheck(frame);
							unquest.setVisible(true);
							grade = unquest.isSucceeded();
						} else {
							grade = true;
						}
						if(grade){
							cards.setVisible(false);
							gradingPanel.setVisible(true);
							gradeExam();
							gradingPanel.setVisible(false);

							if (db.storeExam(qalist, selectedAnswer, et, examID)) {
								showResults();
							} else {
								showResults("Error");
							}
						}
					}
				});

		btnLogin.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						LoginDialog loginDlg = new LoginDialog(frame);
						loginDlg.setVisible(true);
						// if logged on successfully
						if(loginDlg.isSucceeded()){
							et = loginDlg.getExamTaker();
							btnLogin.setVisible(false);
							btnLogout.setVisible(true);
							waitPanel.setVisible(false);
							examChoicePanel.setVisible(true);
							lblLoggedIn.setText(et.getFirstName() + " " + et.getLastName());
							lblLoggedIn.setVisible(true);
							lblWelcomeToThe.setText("Welcome to the Java Exam Maker.");
							buildExamsAvailable();
							buildExamsTaken();


						}
					}
				});

		btnStart.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						lblWelcomeToThe.setText("Exam Title Here");
						waitPanel.setVisible(false);
						btnStart.setVisible(false);
						cards.setVisible(true);
						et.setAttemptTimeStamp( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//init timestamp with start of exam not start of registration
						lblStartTime.setText(et.getAttemptTimeStamp());
						lblStartTime.setVisible(true);
						lblElapsedTime.setText("");
						lblElapsedTime.setVisible(true);
						btnScore.setVisible(true);
					}
				});


		btnLogout.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						et = null;
						lblWelcomeToThe.setText("Welcome to the Java Exam Maker, Please Login.");
						waitPanel.setVisible(true);
						cards.setVisible(false);
						btnLogin.setVisible(true);
						btnLogout.setVisible(false);
						btnStart.setVisible(false);
						lblLoggedIn.setVisible(false);
						lblLoggedIn.setText("");
						lblStartTime.setText("");
						lblStartTime.setVisible(false);
						lblElapsedTime.setText("");
						lblElapsedTime.setVisible(false);
						btnScore.setVisible(false);
						sPaneCompExams.removeAll();
						sPaneAvailExams.removeAll();
					}
				});

	}

	private void gradeExam() {
		
		for(ButtonGroup bg:answerGroup) {
			if (bg.getSelection() != null) {
				ButtonModel buttonModel = bg.getSelection();
				if (modelToRadioButton.containsKey(buttonModel)) {
					JRadioButton b = modelToRadioButton.get(buttonModel);
					selectedAnswer.add(b.getText());
				} else {
					selectedAnswer.add("");
				}
			}  else {
				selectedAnswer.add("");
			}
		}
		int x = 0;
//		System.out.println(" " + correctAnswers.size() + " " + selectedAnswer.size());
		for(String ca:correctAnswers) {
			
//		System.out.println(ca + "\n" + selectedAnswer.get(x) + "\n\n");
			
			if (ca.equalsIgnoreCase(selectedAnswer.get(x))) {
				numberCorrect++; 
			}
			x++;
		}
		examScore = (int)Math.round((((double)numberCorrect)/numberOfQuestions)*100);
		et.attemptScore = examScore;
		et.attemptDuration = 0;
//		System.out.println(Integer.toString(examScore));
		cards.removeAll();
		

	}

	private void showResults() {
		showResults("");
	}
	
	private void showResults(String code) {
		if (code.equalsIgnoreCase("error")) {
			
		} else {
			
		}
	}



	private void buildExam() {
		qalist = ExamFunction.generateTest(numberOfQuestions,numberOfAnswers);	//Generate this exam
		int x = 0;
		CardLayout cardLayout = (CardLayout) cards.getLayout();
		for(QuestionWithAnswer qa:qalist)
		{

			x++;		

			JPanel q1panel = new JPanel();


			q1panel.setLayout(new MigLayout("", "[75px][grow][75px]", "[30px,top][60px,top][15px,top][]"));

			JButton prevQuestion = new JButton("<<<");
			q1panel.add(prevQuestion, "cell 0 0,alignx right,aligny center");

			JButton nextQuestion = new JButton(">>>");
			q1panel.add(nextQuestion, "cell 2 0,alignx left,aligny center");

			JLabel lblQuestion_QuestionNumber = new JLabel("Question " + x);
			q1panel.add(lblQuestion_QuestionNumber, "flowx,cell 1 0,alignx center,aligny center");

			JLabel lblQuestion_Question = new JLabel("<html>" + qa.getQuestion() + " "  + qa.getQuestion() + " " + qa.getQuestion() + "</html");
			q1panel.add(lblQuestion_Question, "cell 0 1 3 1");
			ButtonGroup q1group = new ButtonGroup();

			
			
			
			
			
			
			
			
			JScrollPane scrollPaneq = new JScrollPane();
			scrollPaneq.setBounds(0, 0, 670, 265);
			scrollPaneq.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			q1panel.add(scrollPaneq, "cell 0 3 3 1, grow,push");
			JPanel paneQJ = new JPanel();
			paneQJ.setLayout(new MigLayout("", "[75px][grow][75px]", "[][][][][][][][][]"));








			int y=0;

			for(ExamAnswer answer:qa.answerList)
			{
				String cell = "cell 0 "+ y + " 3 1";
				String anowtext = new String();
				anowtext = answer.answer;
				JRadioButton answernow = new JRadioButton(anowtext);
				paneQJ.add(answernow, cell);
				ButtonModel buttonModel = answernow.getModel();
			    modelToRadioButton.put(buttonModel, answernow);
				q1group.add(answernow);
				y++;
				if (answer.getIsCorrect()) {
					correctAnswers.add(anowtext);
				}
			}

			answerGroup.add(q1group);
			scrollPaneq.add(paneQJ);
			cards.add(q1panel,"Question " + x);
			scrollPaneq.setViewportView(paneQJ);


			prevQuestion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.previous(cards);
				}
			});

			nextQuestion.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					cardLayout.next(cards);
				}
			});






		}
	}
	
	private void setExam(AvailableExam ae) {
		numberOfQuestions = ae.getNumberofquestions();//maximum number of questions on the test
		numberOfAnswers = ae.getMaxanswers();//maximum number of answers per question
		examID =  ae.getId(); //Integer.parseInt(initvalues.get("name"));//Type of question numbers or letters
		buildExam();
		waitPanel.setVisible(true);
		btnStart.setVisible(true);
		examChoicePanel.setVisible(false);
		sPaneCompExams.removeAll();
		sPaneAvailExams.removeAll();
	}

	private void buildExamsAvailable() {
		List<AvailableExam> availableExams = db.getExamsAvailable(et.id);
		int x = 0;
		for (AvailableExam ae:availableExams) {
			System.out.println(ae.toString());
			JButton aebutton = new JButton(ae.examname);
			aebutton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setExam(ae);
				}
			});
			sPaneAvailExams.add(aebutton, "cell 0 " + x + " ,growx");
			x++;
		}
		if (x == 0) {
			sPaneAvailExams.add(new JLabel("No exams available at this time."), "cell 0 0 ,growx");
		}
		sPaneAvailExams.validate();
	}
	
	private void buildExamsTaken() {
		List<CompletedExam> completedExams = db.getExamsTaken(et.id);
		int x = 0;
		for (CompletedExam ce:completedExams) {
			System.out.println(ce.toString());
		sPaneCompExams.add(new JButton(ce.getExamName()), "cell 0 " + x + " ,growx");
		x++;
		}
		if (x == 0) {
			sPaneCompExams.add(new JLabel("No exams available at this time."), "cell 0 0 ,growx");
		}
		sPaneCompExams.validate();
	}
	
	
	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame("Exam Maker");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);
	}
}

