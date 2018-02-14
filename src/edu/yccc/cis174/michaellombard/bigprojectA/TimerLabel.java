package edu.yccc.cis174.michaellombard.bigprojectA;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class TimerLabel extends JLabel implements ActionListener {
	
	public int secondcount = 0;

	public TimerLabel() {
		super("" + new Date());
		Timer t = new Timer(1000, this);
		t.start();
	}

	public void setSecondCount(int sc) {
		secondcount = sc;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		secondcount++;
		setText("" + secondcount);
		
	}
}

