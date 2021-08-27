package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class NewPlayerPointsListener implements KeyListener
{
	private JFrame frame;
	public NewPlayerPointsListener(JFrame f)
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
		if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == KeyEvent.VK_DELETE)
		{
			
		}
		else
		{
			if((!Character.isDigit(typed))) 
			{
				JOptionPane.showMessageDialog(frame, "Please only enter a valid number! :)");
				e.consume();
			}
		}
	}

}
