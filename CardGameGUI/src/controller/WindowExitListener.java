package controller;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import model.ContentHolder;
import model.interfaces.GameEngine;

public class WindowExitListener implements WindowListener 
{

	GameEngine gameEngine;
	private ContentHolder content;
	private JFrame frame;
	boolean check;
	public WindowExitListener(JFrame f, GameEngine g, ContentHolder c, boolean chk)
	{
		gameEngine = g;
		content = c;
		frame = f;
		check = chk;
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) 
	{
		if(check)
		{		
			content.setPlayer(null);	
		}
		else
		{
			content.setNewBet(null);
		}
		
		frame.setVisible(false);
		content.getTopBar().setAdd(true);
		content.getMenuBar().setAdd(true);
		if(gameEngine.getAllPlayers().size() > 0)
		{
			content.getMenuBar().setDelete(true);
			if(gameEngine.getPlayer(content.getTopBar().returnId()).getBet() > 0)
			{
				content.getTopBar().setDeal(true);
				content.getMenuBar().setDeal(true);
				content.getMenuBar().setDelete(true);
			}
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {

	}

	@Override
	public void windowIconified(WindowEvent arg0) {


	}

	@Override
	public void windowOpened(WindowEvent arg0) {

	}

}
