package edu.yccc.cis174.michaellombard.bigproject1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

 
@SuppressWarnings("serial")
public class AnswerCheck extends JDialog {
 
    private JLabel lbUsername;
    private JButton goBack;
    private JButton getGrade;
    private boolean succeeded;

 
    public AnswerCheck(Frame parent) {
         super(parent, "Answer Check", true);
         JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
 
        cs.fill = GridBagConstraints.HORIZONTAL;
 
        lbUsername = new JLabel("Questions remain unanswered!");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(lbUsername, cs);
 
  
        goBack = new JButton("Go Back");
 
        goBack.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
            	succeeded = false;
            	dispose();            }
        });
        getGrade = new JButton("Continue and Grade");
        getGrade.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               succeeded = true;
               dispose();
            }
        });
        JPanel bp = new JPanel();
        bp.add(goBack);
        bp.add(getGrade);
 
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);
 
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }
 
     public boolean isSucceeded() {
        return succeeded;
    }
}