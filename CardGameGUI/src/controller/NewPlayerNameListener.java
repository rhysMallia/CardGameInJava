package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NewPlayerNameListener implements KeyListener
{
	private JFrame frame;
	public NewPlayerNameListener(JFrame f)
	{
		frame = f;
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{

	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		char typed = e.getKeyChar();
		if((Character.isDigit(typed))) 
		{
			
			JOptionPane.showMessageDialog(frame, "Please only enter letters! :)");
			e.consume();
		}
	}

}
