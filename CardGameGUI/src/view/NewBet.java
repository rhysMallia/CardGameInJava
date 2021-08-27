package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AddListener;
import controller.CancelListener;
import controller.NewPlayerPointsListener;
import controller.WindowExitListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;

public class NewBet implements Constants
{
	public NewBet(GameEngine g, ContentHolder content) 
	{
		/** creating the new bet dialog box **/
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel(new GridLayout(2, 0));
		JPanel info = new JPanel(new GridLayout(1,1));
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		info.add(new JLabel("Place Bet Amount:"));
		
		JTextField bet = new JTextField();
		bet.addKeyListener(new NewPlayerPointsListener(frame));
		
		JButton add = new JButton(OKAY);
		JButton cancel = new JButton(CANCEL);
		add.addActionListener(new AddListener(null, bet, frame, content, g, false));
		cancel.addActionListener(new CancelListener(frame, g, content, false));
		
		buttons.add(add);
		buttons.add(Box.createRigidArea(new Dimension(5, 0)));
		buttons.add(cancel);
		info.add(bet);
		
		mainPanel.add(info);
		mainPanel.add(buttons);
		
		frame.addWindowListener(new WindowExitListener(frame, g, content, false));
		
		frame.add(mainPanel);
		frame.getRootPane().setDefaultButton(add);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setTitle(SMALL_T);
		frame.setVisible(true);
	}
}
