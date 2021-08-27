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
import controller.NewPlayerNameListener;
import controller.NewPlayerPointsListener;
import controller.WindowExitListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;

public class NewPlayer implements Constants
{
	
	public NewPlayer(GameEngine g, ContentHolder content)
	{
		/** creating the new player dialog box **/
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel(new GridLayout(2, 0));
		JPanel info = new JPanel(new GridLayout(2, 2));
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		/** adding our text areas to create new players **/
		info.add(new JLabel("Player Name:"));
		info.add(new JLabel("Initial Points:"));
		
		
		/** creating text field, and adding the listeners to hold basic input constraints **/
		JTextField points = new JTextField();
		JTextField name = new JTextField();
		points.addKeyListener(new NewPlayerPointsListener(frame));
		name.addKeyListener(new NewPlayerNameListener(frame));
		/** adding the button, this will in effect add a new player **/
		JButton add = new JButton(OKAY);
		JButton cancel = new JButton(CANCEL);
		add.addActionListener(new AddListener(name, points, frame, content, g, true));
		cancel.addActionListener(new CancelListener(frame, g, content, true));
		
		/** Adding the buttons to the button panel, then the text boxes to the info panel **/
		buttons.add(add);
		buttons.add(Box.createRigidArea(new Dimension(5, 0)));
		buttons.add(cancel);
		info.add(name);
		info.add(points);
		
		/** Adding both of those into the main panel and then adding that into the frame **/
		mainPanel.add(info);
		mainPanel.add(buttons);
		
		frame.addWindowListener(new WindowExitListener(frame, g, content, true));
		
		/** adding information to the JFrame and setting it to spawn in a location that is suitable for the user experience **/
		frame.add(mainPanel);
		frame.getRootPane().setDefaultButton(add);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setTitle(SMALL_T);
		frame.setVisible(true);
	}
}
