package edu.yccc.cis174.michaellombard.slack;


import java.awt.EventQueue;



import javax.swing.JFrame;

import javax.swing.JRadioButton;

import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextField;



public class Slack {



	private JFrame frmMessageApp;
	private SlackService slackS = new SlackService();
	private JTextField textField;



	/**

	 * Launch the application.

	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Slack window = new Slack();
					window.frmMessageApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public Slack() {
		initialize();
	}



	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {

		frmMessageApp = new JFrame();
		frmMessageApp.setTitle("Message App");
		frmMessageApp.setBounds(100, 100, 481, 399);
		frmMessageApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMessageApp.getContentPane().setLayout(null);
		
		JLabel lblMessageTarget = new JLabel("Sender Name");
		lblMessageTarget.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageTarget.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblMessageTarget.setBounds(34, 11, 403, 24);
		frmMessageApp.getContentPane().add(lblMessageTarget);

		//Buttons to Select channel\\
		 
		//Message Instructor --- U795A112Q
		JRadioButton rdbtnMessageInstructor = new JRadioButton("Message Instructor");
		rdbtnMessageInstructor.setActionCommand("U795A112Q");
		rdbtnMessageInstructor.setBounds(34, 116, 175, 23);
		frmMessageApp.getContentPane().add(rdbtnMessageInstructor);
		
		//Message Classmate --- U7CD62AS1
		JRadioButton rdbtnMessageVince = new JRadioButton("Message Vince");
		rdbtnMessageVince.setActionCommand("U7CD62AS1");
		rdbtnMessageVince.setBounds(34, 142, 175, 23);
		frmMessageApp.getContentPane().add(rdbtnMessageVince);
		
		//Default Button on Start
		JRadioButton rdbtnMessageGeneral = new JRadioButton("Message #general     ");
		rdbtnMessageGeneral.setActionCommand("#general");
		rdbtnMessageGeneral.setSelected(true);
		rdbtnMessageGeneral.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnMessageGeneral.setBounds(291, 116, 147, 23);
		frmMessageApp.getContentPane().add(rdbtnMessageGeneral);

		JRadioButton rdbtnMessageintegration = new JRadioButton("Message #integration");
		rdbtnMessageintegration.setActionCommand("#integration");
		rdbtnMessageintegration.setHorizontalAlignment(SwingConstants.RIGHT);
		rdbtnMessageintegration.setBounds(291, 142, 146, 23);
		frmMessageApp.getContentPane().add(rdbtnMessageintegration);
		//END Buttons to Select channel\\
		
		//Group Radio Buttons
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnMessageInstructor);
	    group.add(rdbtnMessageVince);
	    group.add(rdbtnMessageGeneral);
	    group.add(rdbtnMessageintegration);


		//END Group Radio Buttons
		
		JSeparator separator = new JSeparator();
		separator.setBounds(34, 176, 403, 4);
		frmMessageApp.getContentPane().add(separator);		

		//text area to enter message to send
		JTextArea textArea = new JTextArea();
		textArea.setBounds(34, 203, 403, 81);
		frmMessageApp.getContentPane().add(textArea);

		JLabel lblMessageToSend = new JLabel("Message To Send");
		lblMessageToSend.setHorizontalAlignment(SwingConstants.CENTER);
		lblMessageToSend.setFont(new Font("Times New Roman", Font.BOLD, 18));
		lblMessageToSend.setBounds(34, 179, 403, 24);
		frmMessageApp.getContentPane().add(lblMessageToSend);

		//Button to Send Message to SlackService
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				slackS.sendMessage(group.getSelection().getActionCommand(),textField.getText(),textArea.getText());
				textArea.setText(null);
			}
		});
		btnSend.setBounds(153, 295, 168, 54);
		frmMessageApp.getContentPane().add(btnSend);
		
		JLabel label = new JLabel("Message Target");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Times New Roman", Font.BOLD, 18));
		label.setBounds(35, 85, 403, 24);
		frmMessageApp.getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(34, 39, 403, 20);
		frmMessageApp.getContentPane().add(textField);
		textField.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(34, 80, 403, 4);
		frmMessageApp.getContentPane().add(separator_1);
		


	}
}